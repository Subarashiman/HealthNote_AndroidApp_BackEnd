package HealthNote.healthnote.library_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDto {

    int code;
    int exerciseNumber;
    String exerciseName;
    String exerciseExplanation;
    String exerciseUrl;

    public LibraryDto() {

    }
    public LibraryDto(int code, int exerciseNumber, String exerciseName, String exerciseExplanation, String exerciseUrl) {
        this.code = code;
        this.exerciseNumber = exerciseNumber;
        this.exerciseName = exerciseName;
        this.exerciseExplanation = exerciseExplanation;
        this.exerciseUrl = exerciseUrl;
    }
}
