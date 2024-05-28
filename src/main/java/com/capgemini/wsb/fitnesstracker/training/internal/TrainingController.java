package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingIdTO;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingProvider trainingProvider;
    private final TrainingMapper trainingMapper;
    private final TrainingRepository trainingRepository;
    private final TrainingServiceImpl trainingService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingTO createTraining(@RequestBody TrainingIdTO trainingIdTO){
        return trainingMapper.toTraining(trainingService.createTrainingEntity(trainingIdTO));
    }
    @GetMapping
    public List<TrainingTO> getTrainings() {
        return trainingProvider.getTrainings().stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public List<TrainingTO> getTrainingsForUser(@PathVariable("userId") Long userId){
        return trainingService.getUserTrainings(userId).stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @GetMapping("/finished/{endDate}")
    public List<TrainingTO> getTrainingsByEndTimeAfter(@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date endDate){
        return trainingRepository.findByEndTimeAfter(endDate).stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @GetMapping("/activityType")
    public List<TrainingTO> getTrainingsByActivityType(@RequestParam("activityType") ActivityType activityType){
        return trainingRepository.findByActivityType(activityType).stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @PutMapping("/{trainingId}")
    public TrainingTO updateTraining(@PathVariable Long trainingId, @RequestBody TrainingIdTO trainingIdTO){
        Training training = trainingMapper.toEntityUpdate(trainingIdTO);
        final Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toTraining(savedTraining);
    }
}
