package HealthNote.healthnote.exercise_dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class ExerciseMonthDto {

    private LocalDate[] ExerciseMonthDates;
    private boolean success;
    private int code;

    public ExerciseMonthDto(LocalDate[] exerciseMonth, boolean success, int code) {
        ExerciseMonthDates = exerciseMonth;
        this.success = success;
        this.code = code;
    }
}
