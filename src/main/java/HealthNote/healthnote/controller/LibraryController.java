package HealthNote.healthnote.controller;

import HealthNote.healthnote.domain.Library;
import HealthNote.healthnote.library_dto.LibraryExerciseInfoDto;
import HealthNote.healthnote.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/api/library/exercise-list")
    public List<Library> getExerciseListByExerciseNumber(@RequestParam int exerciseNumber) {


        return libraryService.getExercisesByExerciseNumber(exerciseNumber);
    }



    // 운동 정보
    @GetMapping("/api/library/exercise-info")
    public LibraryExerciseInfoDto findExercise(@RequestParam("exerciseNumber") int exerciseNumber) {
        Library result = libraryService.find(exerciseNumber);

        if (result != null && result.getExerciseName() != null) {
            return convertLibraryToDto(result);
        } else {
            return new LibraryExerciseInfoDto(400, exerciseNumber, null, null, null);
        }

    }

    private LibraryExerciseInfoDto convertLibraryToDto(Library library) {
        LibraryExerciseInfoDto libraryDto = new LibraryExerciseInfoDto();
        libraryDto.setCode(200);
        libraryDto.setExerciseNumber(library.getExerciseNumber());
        libraryDto.setExerciseName(library.getExerciseName());
        libraryDto.setExerciseExplanation(library.getExerciseExplanation());
        libraryDto.setExerciseUrl(library.getExerciseUrl());
        return libraryDto;
    }
}
