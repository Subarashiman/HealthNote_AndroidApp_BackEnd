package HealthNote.healthnote.exercise_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ExerciseWeekDto {

    private int totalWeekTime;
    private int totalWeekWeight;
    private boolean[] weekExerciseCheck = new boolean[7];
    private boolean success;
    private int code;

}
