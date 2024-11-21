package com.capgemini.wsb.fitnesstracker.statistics.api;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthlyStatisticsScheduler {
    private final StatisticsService statisticsService;

    /**
     * Scheduled task to generate statistics on the first day of each month at 12:00 AM.
     */
    @Scheduled(cron = "0 0 0 1 * *")
    public void scheduleMonthlyStatisticsGeneration() {
        statisticsService.generateMonthlyStatistics();
    }
}
