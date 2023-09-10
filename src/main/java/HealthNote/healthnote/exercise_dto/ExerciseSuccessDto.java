package HealthNote.healthnote.exercise_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ExerciseSuccessDto {

    private boolean success;
    private int code;
    private String message;

    public ExerciseSuccessDto(boolean success, int code,String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
