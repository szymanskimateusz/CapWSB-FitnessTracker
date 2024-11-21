package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class TrainingReportService {
    private final StatisticsProvider statisticsProvider;
    private final UserProvider userProvider;
    private final JavaMailSender mailSender;
    private final TrainingRepository trainingRepository;

    /**
     * Generates and sends monthly reports for all users.
     */
    public void generateAndSendMonthlyReports() {
        List<User> users = userProvider.findAllUsers();
        for (User user : users) {
            Date startOfMonth = convertToDate(LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay());
            Date endOfMonth = convertToDate(LocalDate.now().withDayOfMonth(1).atStartOfDay());

            List<Training> lastMonthTrainings = trainingRepository.findAllByUserIdAndStartTimeBetween(user.getId(), startOfMonth, endOfMonth);

            int totalTrainings = lastMonthTrainings.size();

            String subject = "Monthly Training Report";
            String content = generateReportContent(user, totalTrainings);

            EmailDto email = new EmailDto(user.getEmail(), subject, content);

            sendEmail(email);
        }
    }


    /**
     * Generates the content of the training report email.
     *
     * @param user the user receiving the report
     * @param totalTrainings the total number of trainings in the last month
     * @return the email content as a string
     */
    private String generateReportContent(User user, int totalTrainings) {
        return String.format("""
            Hello %s,

            Here is your training summary for the last month:
            - Total trainings: %d

            Keep up the great work!
            """,
                user.getFirstName(),
                totalTrainings
        );
    }

    /**
     * Sends the email using the provided EmailDto.
     *
     * @param email the email to send
     */
    private void sendEmail(EmailDto email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(email.toAddress());
            helper.setSubject(email.subject());
            helper.setText(email.content());

            mailSender.send(message);
            System.out.println("Email sent to: " + email.toAddress());
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    /**
     * Converts LocalDateTime to java.util.Date.
     *
     * @param localDateTime the LocalDateTime to convert
     * @return the Date equivalent of the LocalDateTime
     */
    private Date convertToDate(LocalDateTime localDateTime) {

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
