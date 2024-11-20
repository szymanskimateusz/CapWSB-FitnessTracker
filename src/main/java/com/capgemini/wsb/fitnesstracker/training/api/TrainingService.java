package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.List;

public interface TrainingService {
    /**
     * Creates a new training based on the provided training data.
     *
     * @param trainingDto the data of the training to be created
     * @return The created {@link TrainingDto} containing details of the new training
     */
    TrainingDto createTraining(TrainingDto trainingDto);

    /**
     * Updates an existing training with the given ID based on the provided update data.
     *
     * @param trainingId the ID of the training to be updated
     * @param trainingUpdateDto the data to update the training
     * @return The updated {@link TrainingDto} containing the new details of the training
     */
    TrainingDto updateTraining(Long trainingId, TrainingUpdateDto trainingUpdateDto);

}
