package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingIdTO;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserMapper userMapper;
    private final UserProvider userProvider;
    TrainingTO toTraining(Training training) {
        return new TrainingTO(training.getId(),
                userMapper.toDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }

    Training toEntity(TrainingIdTO trainingTO) {
        return new Training(
                trainingTO.getStartTime(),
                trainingTO.getEndTime(),
                trainingTO.getActivityType(),
                trainingTO.getDistance(),
                trainingTO.getAverageSpeed());
    }

    Training toEntityUpdate(TrainingIdTO trainingTO) {
        return new Training (trainingTO.getId(),
                userProvider.getUser(trainingTO.getUserId()).get(),
                trainingTO.getStartTime(),
                trainingTO.getEndTime(),
                trainingTO.getActivityType(),
                trainingTO.getDistance(),
                trainingTO.getAverageSpeed());
    }
}