package HealthNote.healthnote.library_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDto {

    int code;
    int exerciseNumber;
    String exerciseName;
    String exerciseExplaination;
    String exerciseUrl;

    public LibraryDto(int code, int exerciseNumber, String exerciseName, String exerciseExplaination, String exerciseUrl) {
        this.code = code;
        this.exerciseNumber = exerciseNumber;
        this.exerciseName = exerciseName;
        this.exerciseExplaination = exerciseExplaination;
        this.exerciseUrl = exerciseUrl;
    }
}
