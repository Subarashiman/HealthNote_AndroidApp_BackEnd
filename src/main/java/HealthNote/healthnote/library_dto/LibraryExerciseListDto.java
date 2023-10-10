package HealthNote.healthnote.library_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LibraryExerciseListDto {

    private int exerciseNumber;
    private String exerciseName;

    public LibraryExerciseListDto(int exerciseNumber, String exerciseName) {
        this.exerciseNumber = exerciseNumber;
        this.exerciseName = exerciseName;
    }
}
