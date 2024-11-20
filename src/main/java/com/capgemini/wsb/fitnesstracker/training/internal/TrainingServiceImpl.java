package com.capgemini.wsb.fitnesstracker.training.internal;

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
        User user = userRepository.findById(trainingDto.user.getId()).orElseThrow(() -> new RuntimeException("User not found"));

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
    public TrainingDto updateTraining(TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(trainingDto.id).orElseThrow(()->new TrainingNotFoundException("Training not found"));
        existingTraining.setDistance(trainingDto.distance);
        trainingRepository.save(existingTraining);
        return trainingMapper.toDto(existingTraining);
    }




}
