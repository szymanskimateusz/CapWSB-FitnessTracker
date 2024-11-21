package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class TrainingReportController {
    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    private final TrainingReportService trainingReportService;

    /**
     * Endpoint to manually trigger the monthly training report generation and email dispatch.
     *
     * @return a message indicating the success of the operation
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateAndSendReports() {
        trainingReportService.generateAndSendMonthlyReports();
        return ResponseEntity.ok("Training reports have been generated and sent.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        logger.error("Error occurred", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
