package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {
    private final UserMapper userMapper;
    TrainingTO toTraining(Training training){
        return new TrainingTO(training.getId(), training.getUser(), training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed())
    }
}
