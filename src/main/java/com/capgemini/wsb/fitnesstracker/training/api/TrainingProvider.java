package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

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
    List<TrainingDto> findAllTrainings();

    List<TrainingDto> findTrainingsByActivityType(ActivityType activityType);

    List<TrainingDto> findFinishedTrainingsAfter(Date afterTime);

    List<TrainingDto> findTrainingsByUserId(Long userId);


}
