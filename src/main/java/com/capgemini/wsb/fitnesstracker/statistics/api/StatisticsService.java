package com.capgemini.wsb.fitnesstracker.statistics.api;

import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsRepository;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TrainingRepository trainingRepository;
    private final StatisticsRepository statisticsRepository;
    private final UserProvider userProvider;

    /**
     * Generates and saves statistics for all users for the last month.
     */
    @Transactional
    public void generateMonthlyStatistics() {
        List<User> users = userProvider.findAllUsers();

        for (User user : users) {
            Date startOfMonth = convertToDate(LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay());
            Date endOfMonth = convertToDate(LocalDate.now().withDayOfMonth(1).atStartOfDay());

            List<Training> lastMonthTrainings = trainingRepository.findByUserAndStartTimeBetween(
                    user, startOfMonth, endOfMonth);

            int totalTrainings = lastMonthTrainings.size();
            double totalDistance = lastMonthTrainings.stream()
                    .mapToDouble(Training::getDistance)
                    .sum();
            double totalAverageSpeed = lastMonthTrainings.stream()
                    .mapToDouble(Training::getAverageSpeed)
                    .average()
                    .orElse(0.0);


            Statistics statistics = statisticsRepository.findByUser(user)
                    .orElse(new Statistics());
            statistics.setUser(user);
            statistics.setTotalTrainings(totalTrainings);
            statistics.setTotalDistance(totalDistance);
            statistics.setTotalAverageSpeed(totalAverageSpeed);

            statisticsRepository.save(statistics);
        }
    }

    /**
     * Converts LocalDateTime to java.util.Date.
     *
     * @param localDateTime the LocalDateTime to convert
     * @return the Date equivalent of the LocalDateTime
     */
    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
