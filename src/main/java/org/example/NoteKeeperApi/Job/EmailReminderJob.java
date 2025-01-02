package org.example.NoteKeeperApi.Job;

import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Entity.Note;
import org.example.NoteKeeperApi.Repository.NoteRepo;
import org.example.NoteKeeperApi.Service.EmailService.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailReminderJob implements Job {
    private final NoteRepo noteRepo;
    private final EmailService emailService;
    private final String emailTemplate = "email-template.html";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailReminderJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        Long noteId = context.getJobDetail().getJobDataMap().getLong("noteId");
        Note note = noteRepo.findById(noteId).orElse(null);

        if (note != null && note.getReminderTime() != null) {
            String email = note.getUser().getEmail();
            String subject = "Reminder from NoteKeeperApp: " + note.getTitle();
            String noteContent = note.getContent();
            String username = note.getUser().getUsername();
            Map<String, Object> variables = new HashMap<>();
            variables.put("messageContent", noteContent);
            variables.put("username", username); // Добавляем имя пользователя
            emailService.sendEmail(email, subject, emailTemplate, variables);
            LOGGER.info("Sent email reminder for note {} to {}", note.getId(), email);
        } else {
            LOGGER.warn("Note with ID {} not found or reminderTime is null", noteId);
        }
    }
}
