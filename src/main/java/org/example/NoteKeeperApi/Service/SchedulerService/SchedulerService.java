package org.example.NoteKeeperApi.Service.SchedulerService;

import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Entity.Note;
import org.example.NoteKeeperApi.Job.EmailReminderJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private final Scheduler scheduler;

    public void scheduleEmailReminder(Note note) throws SchedulerException {
        if (note.getReminderTime() == null) return;
        Date reminderTime = java.util.Date
                .from(note.getReminderTime().atZone(ZoneId.systemDefault()).toInstant());
        JobDetail jobDetail = buildJobDetail(note);
        Trigger trigger = buildJobTrigger(jobDetail, reminderTime);
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void cancelEmailReminder(Long noteId) throws SchedulerException {
        String jobKey = "emailReminderJob_" + noteId;
        TriggerKey triggerKey = new TriggerKey(jobKey, "email-reminders");
        boolean isJobDeleted = scheduler.unscheduleJob(triggerKey);
        if (isJobDeleted) {
            LOGGER.info("Reminder job for note with ID {} has been successfully canceled.", noteId);
        } else {
            LOGGER.warn("No reminder job found for note with ID {}. Could not cancel the reminder.", noteId);
        }
    }

    private JobDetail buildJobDetail(Note note) {
        return JobBuilder.newJob(EmailReminderJob.class)
                .withIdentity("emailReminderJob_" + note.getId())
                .usingJobData("noteId", note.getId())
                .storeDurably(false)
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