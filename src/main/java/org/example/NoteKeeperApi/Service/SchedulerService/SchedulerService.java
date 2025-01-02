package org.example.NoteKeeperApi.Service.SchedulerService;

import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Entity.Note;
import org.example.NoteKeeperApi.Job.EmailReminderJob;
import org.example.NoteKeeperApi.Repository.NoteRepo;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final Scheduler scheduler;
    private final NoteRepo noteRepo;

    public void scheduleEmailReminder(Note note) throws SchedulerException {
        if (note.getReminderTime() == null) return;

        Date reminderTime = java.util.Date
                .from(note.getReminderTime().atZone(ZoneId.systemDefault()).toInstant());

        JobDetail jobDetail = buildJobDetail(note);
        Trigger trigger = buildJobTrigger(jobDetail, reminderTime);

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private JobDetail buildJobDetail(Note note) {
        return JobBuilder.newJob(EmailReminderJob.class)
                .withIdentity("emailReminderJob_" + note.getId())
                .usingJobData("noteId", note.getId())
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, Date startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-reminders")
                .startAt(startAt)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}

