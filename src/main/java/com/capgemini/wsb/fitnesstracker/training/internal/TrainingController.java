package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingUpdateDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    private final TrainingService trainingService;
    private final TrainingProvider trainingProvider;
    @Autowired
    private final UserRepository userRepository;

    /**
     * Get all trainings.
     */
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> trainings = trainingProvider.findAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    /**
     * Get all trainings for a specific user.
     * @param userId ID of the user whose trainings are to be retrieved.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUser(@PathVariable Long userId) {
        List<TrainingDto> trainings = trainingProvider.findTrainingsByUserId(userId);
        return ResponseEntity.ok(trainings);
    }

    /**
     * Get all finished trainings after a specified date.
     * @param afterTime Date after which finished trainings are to be retrieved.
     */
    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getFinishedTrainingsAfter(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date afterTime) {
        List<TrainingDto> trainings = trainingProvider.findFinishedTrainingsAfter(afterTime);
        return ResponseEntity.ok(trainings);
    }

    /**
     * Get all trainings by activity type.
     * @param activityType The type of activity for filtering trainings.
     */
    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        List<TrainingDto> trainings = trainingProvider.findTrainingsByActivityType(activityType);
        return ResponseEntity.ok(trainings);
    }

    /**
     * Create a new training.
     * @param trainingDto Training data for the new training.
     */
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingDto trainingDto) {
        TrainingDto createdTraining = trainingService.createTraining(trainingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTraining);
    }

    /**
     * Handles HTTP PUT requests to update an existing training by its ID.
     * @param trainingId the ID of the training to update.
     * @param trainingUpdateDto the data for updating the training.
     * @return a ResponseEntity containing the updated TrainingDto object.
     */
    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingUpdateDto trainingUpdateDto) {
        trainingUpdateDto.setId(trainingId);
        TrainingDto updatedTraining = trainingService.updateTraining(trainingId, trainingUpdateDto);
        return ResponseEntity.ok(updatedTraining);
    }

    /**
     * Handles all exceptions that are not specifically caught by other exception handlers.
     * Logs the error and returns a standardized error response.
     * @param ex the exception that was thrown.
     * @return a ResponseEntity containing an error message and HTTP BAD_REQUEST status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        logger.error("Error occurred", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
