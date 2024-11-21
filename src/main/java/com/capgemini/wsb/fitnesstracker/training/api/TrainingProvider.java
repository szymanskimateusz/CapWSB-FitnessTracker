package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<TrainingDto> getTraining(Long trainingId);

    /**
     * Retrieves all available trainings.
     *
     * @return A list of {@link TrainingDto} containing all trainings
     */
    List<TrainingDto> findAllTrainings();

    /**
     * Retrieves trainings filtered by a specific activity type.
     *
     * @param activityType the type of activity to filter trainings
     * @return A list of {@link TrainingDto} containing trainings of the specified activity type
     */
    List<TrainingDto> findTrainingsByActivityType(ActivityType activityType);

    /**
     * Retrieves trainings that were finished after the specified time.
     *
     * @param afterTime the timestamp after which finished trainings will be retrieved
     * @return A list of {@link TrainingDto} containing finished trainings after the given time
     */
    List<TrainingDto> findFinishedTrainingsAfter(Date afterTime);

    /**
     * Retrieves trainings associated with a specific user.
     *
     * @param userId the ID of the user whose trainings are to be retrieved
     * @return A list of {@link TrainingDto} containing trainings associated with the given user
     */
    List<TrainingDto> findTrainingsByUserId(Long userId);

}
