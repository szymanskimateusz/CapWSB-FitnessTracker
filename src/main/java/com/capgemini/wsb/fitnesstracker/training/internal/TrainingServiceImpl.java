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

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    @Override
    public Optional<TrainingDto> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId).map(trainingMapper::toDto);
    }

    @Override
    public List<TrainingDto> findAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAllByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findFinishedTrainingsAfter(Date afterTime) {
        return trainingRepository.findAllByEndTimeAfter(afterTime).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findTrainingsByUserId(Long userId) {
        return trainingRepository.findAllByUserId(userId).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

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

    @Override
    public TrainingDto updateTraining(Long trainingId, TrainingUpdateDto trainingUpdateDto) {
        Training existingTraining = trainingRepository.findById(trainingUpdateDto.getId()).orElseThrow(()->new TrainingNotFoundException("Training not found"));
        if (trainingUpdateDto.getDistance() != 0)
            existingTraining.setDistance(trainingUpdateDto.getDistance());

        return trainingMapper.toDto(existingTraining);
    }




}
