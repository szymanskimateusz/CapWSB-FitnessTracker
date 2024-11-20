package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.List;

public interface TrainingService {
    TrainingDto createTraining(TrainingDto trainingDto);

    TrainingDto updateTraining(Long trainingId, TrainingUpdateDto trainingUpdateDto);

}
