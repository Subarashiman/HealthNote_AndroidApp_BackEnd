package HealthNote.healthnote.exercise_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ExerciseWeekDto {

    private int[] weekExerciseCheck = new int[7];
    private int code;

}
