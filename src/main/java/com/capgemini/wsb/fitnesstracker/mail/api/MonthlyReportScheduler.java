package com.capgemini.wsb.fitnesstracker.mail.api;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthlyReportScheduler {
    private final TrainingReportService trainingReportService;

    /**
     * Scheduled task to generate and send reports on the first day of each month at 8:00 AM.
     */
    @Scheduled(cron = "0 0 8 1 * *")
    public void scheduleMonthlyReports() {
        trainingReportService.generateAndSendMonthlyReports();
    }
}
