package com.capgemini.wsb.fitnesstracker.notification.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;

@Service
@EnableScheduling
@Data
public class ReportService {
    private static final String reportTitle = "Last week's report";

    private final EmailSender emailSender;

    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;

    private SimpleMailMessage createEmail(final String user, final List<Training> trainingList){
        final List<Training> trainings = trainingList.stream().filter(training -> training.getStartTime().after(returnBeginningOfLastWeek()) && training.getStartTime().before(returnYesterday())).toList();
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user);
        simpleMailMessage.setSubject(ReportService.reportTitle);
        final StringBuilder builder = new StringBuilder("""
                %s trainings completed last week,
                accumulating %s units of distance
                You've finished %s trainings so far.
                Last week's trainings can be found below:
                ----
                """.formatted(trainings.size(),
                trainings.isEmpty() ? 0 : trainings.stream().mapToDouble(Training::getDistance).sum(),
                trainings.size()
        ));
        trainings.forEach(training -> builder.append("""
            Training start: %s
            Training end: %s
            Activity type: %s
            Distance: %s
            Average speed: %s
            ----
            """.formatted(training.getStartTime(),
                training.getEndTime() == null ? "-" : training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        )));
        simpleMailMessage.setText(builder.toString());
        System.out.println(builder); //task requirement
        return simpleMailMessage;
    }

    private Date returnBeginningOfLastWeek() {
        final Calendar now = Calendar.getInstance();
        now.add(DAY_OF_MONTH, -7);
        return now.getTime();
    }

    private Date returnYesterday() {
        final Calendar now = Calendar.getInstance();
        now.add(DAY_OF_MONTH, -1);
        return now.getTime();
    }

    @Scheduled(cron = "0 0 12 ? * 1")
    public void generateReport(){
        final List<User> userList = userProvider.findAllUsers();
        for (User user : userList){
            final SimpleMailMessage simpleMailMessage = createEmail(user.getEmail(), trainingProvider.getUserTrainings(user.getId()));
            emailSender.send(simpleMailMessage);
        }
    }
}
