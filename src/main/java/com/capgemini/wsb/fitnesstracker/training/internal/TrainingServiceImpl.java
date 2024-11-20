package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing training-related operations.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Retrieves a training by its ID.
     * @param trainingId the ID of the training to retrieve.
     * @return an Optional containing the TrainingDto if found, or empty if not.
     */
    @Override
    public Optional<TrainingDto> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId).map(trainingMapper::toDto);
    }

    /**
     * Retrieves all trainings from the database.
     * @return a list of TrainingDto objects representing all trainings.
     */
    @Override
    public List<TrainingDto> findAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds all trainings of a specific activity type.
     * @param activityType the type of activity to filter by.
     * @return a list of TrainingDto objects matching the specified activity type.
     */
    @Override
    public List<TrainingDto> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAllByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds all trainings that were finished after a specified time.
     * @param afterTime the date and time to filter trainings.
     * @return a list of TrainingDto objects representing trainings finished after the specified time.
     */
    @Override
    public List<TrainingDto> findFinishedTrainingsAfter(Date afterTime) {
        return trainingRepository.findAllByEndTimeAfter(afterTime).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds all trainings associated with a specific user ID.
     * @param userId the ID of the user to filter trainings by.
     * @return a list of TrainingDto objects belonging to the specified user.
     */
    @Override
    public List<TrainingDto> findTrainingsByUserId(Long userId) {
        return trainingRepository.findAllByUserId(userId).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new training and associates it with a user.
     * @param trainingDto the data for the new training.
     * @return the created TrainingDto object.
     * @throws NotFoundException if the user specified in the trainingDto does not exist.
     */
    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        User user = userRepository.findById(trainingDto.user.id()).orElseThrow(() -> new NotFoundException("User not found"));

        Training training = new Training(
                user,
                trainingDto.startTime,
                trainingDto.endTime,
                trainingDto.activityType,
                trainingDto.distance,
                trainingDto.averageSpeed
        );

        trainingRepository.save(training);

        return trainingMapper.toDto(training);
    }

    /**
     * Updates an existing training with new data.
     * @param trainingId the ID of the training to update.
     * @param trainingUpdateDto the new data to update the training with.
     * @return the updated TrainingDto object.
     * @throws TrainingNotFoundException if the specified training does not exist.
     */
    @Override
    public TrainingDto updateTraining(Long trainingId, TrainingUpdateDto trainingUpdateDto) {
        Training existingTraining = trainingRepository.findById(trainingUpdateDto.getId()).orElseThrow(()->new TrainingNotFoundException("Training not found"));
        if (trainingUpdateDto.getDistance() != 0)
            existingTraining.setDistance(trainingUpdateDto.getDistance());

        return trainingMapper.toDto(existingTraining);
    }
}
