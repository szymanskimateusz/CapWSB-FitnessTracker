package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Mapper class responsible for converting between Training entities and DTOs (Data Transfer Objects).
 */
@RequiredArgsConstructor
@Component
public class TrainingMapper {

    private final UserMapper userMapper;

    /**
     * Converts a Training entity to a TrainingDto.
     *
     * @param training the Training entity to be converted.
     * @return a TrainingDto containing the training's details.
     */
    public TrainingDto toDto(Training training) {
       var dto = new TrainingDto();
       dto.id = training.getId();
       dto.user = userMapper.toDto(training.getUser());
       dto.startTime = training.getStartTime();
       dto.endTime = training.getEndTime();
       dto.activityType = training.getActivityType();
       dto.distance = training.getDistance();
       dto.averageSpeed = training.getAverageSpeed();
       return dto;
    }

    /**
     * Converts a TrainingDto to a Training entity.
     *
     * @param trainingDto the TrainingDto containing the training's details.
     * @return a Training entity based on the provided TrainingDto.
     */
    public Training toEntity(UserDto userDto, TrainingDto trainingDto) {
        return new Training(
                userMapper.toEntity(userDto),
                trainingDto.startTime,
                trainingDto.endTime,
                trainingDto.activityType,
                trainingDto.distance,
                trainingDto.averageSpeed);
    }
}
