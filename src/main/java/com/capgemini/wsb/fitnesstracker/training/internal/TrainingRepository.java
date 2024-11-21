package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
/**
 * Repository interface for Training entity.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Finds all trainings by a specific activity type.
     *
     * @param activityType the activity type to filter by.
     * @return list of trainings matching the specified activity type.
     */
    List<Training> findAllByActivityType(ActivityType activityType);

    /**
     * Finds all finished trainings after a specific date.
     *
     * @param afterTime the date to filter finished trainings.
     * @return list of trainings finished after the specified date.
     */
    List<Training> findAllByEndTimeAfter(Date afterTime);

    /**
     * Finds all trainings for a specific user ID.
     *
     * @param userId the user ID to filter trainings.
     * @return list of trainings for the specified user.
     */
    List<Training> findAllByUserId(Long userId);


    /**
     * Finds trainings for a specific user within a date range.
     *
     * @param user the user whose trainings are to be fetched
     * @param startTime the start time of the date range
     * @param endTime the end time of the date range
     * @return A list of trainings
     */
    List<Training> findByUserAndStartTimeBetween(User user, Date startTime, Date endTime);

    /**
     * Finds all trainings for a given user in the last month.
     *
     * @param userId the user's id
     * @param startOfMonth the start date of the last month
     * @param endOfMonth the end date of the last month
     * @return a list of trainings in the last month
     */
    List<Training> findAllByUserIdAndStartTimeBetween(Long userId, Date startOfMonth, Date endOfMonth);
}
