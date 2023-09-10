package HealthNote.healthnote.exercise_dto;

import HealthNote.healthnote.domain.ExerciseSet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ExerciseDto {

    private String exerciseName;
    private List<ExerciseSetDto> exerciseSetDtos;


}
