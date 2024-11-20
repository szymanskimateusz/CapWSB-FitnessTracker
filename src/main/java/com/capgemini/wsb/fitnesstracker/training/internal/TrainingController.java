package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseEntity<List<TrainingDto>> getFinishedTrainingsAfter(@PathVariable Date afterTime) {
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
     * Update an existing training by ID.
     * @param trainingId ID of the training to update.
     * @param trainingDto Updated training data.
     */
    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        TrainingDto updatedTraining = trainingService.updateTraining(trainingDto);
        return ResponseEntity.ok(updatedTraining);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        logger.error("Error occurred", ex); // Logs the stack trace
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
