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
interface TrainingRepository extends JpaRepository<Training, Long> {
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
}
