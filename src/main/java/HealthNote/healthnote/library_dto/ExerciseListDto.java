package HealthNote.healthnote.library_dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class ExerciseListDto {
    private List<LibraryExerciseListDto> libraryExerciseListDtos = new ArrayList<>();
}
