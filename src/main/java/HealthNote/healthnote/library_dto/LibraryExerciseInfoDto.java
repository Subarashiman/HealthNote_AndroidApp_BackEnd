package HealthNote.healthnote.library_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryExerciseInfoDto {

    private int code;
    private int exerciseNumber;
    private String exerciseName;
    private String exerciseExplanation;
    private String exerciseUrl;

    public LibraryExerciseInfoDto() {

    }
    public LibraryExerciseInfoDto(int code, int exerciseNumber, String exerciseName, String exerciseExplanation, String exerciseUrl) {
        this.code = code;
        this.exerciseNumber = exerciseNumber;
        this.exerciseName = exerciseName;
        this.exerciseExplanation = exerciseExplanation;
        this.exerciseUrl = exerciseUrl;
    }
}
