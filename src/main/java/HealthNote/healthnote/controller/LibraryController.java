package HealthNote.healthnote.controller;

import HealthNote.healthnote.domain.Library;
import HealthNote.healthnote.library_dto.LibraryDto;
import HealthNote.healthnote.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    // n00 -> 운동 카테고리(exerciseName) 넘김 , n0m -> exerciseName, exerciseExplaination, exerciseUrl 넘김
    // 운동 정보
    @GetMapping("/api/library/exercise")
    public LibraryDto findExercise(@RequestParam("exerciseNumber") int exerciseNumber) {
        Library result = libraryService.find(exerciseNumber);

        if (result != null && result.getExerciseName() != null) {
            return convertLibraryToDto(result);
        } else {
            return new LibraryDto(400, exerciseNumber, null, null, null);
        }

    }

    private LibraryDto convertLibraryToDto(Library library) {
        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setCode(200);
        libraryDto.setExerciseNumber(library.getExerciseNumber());
        libraryDto.setExerciseName(library.getExerciseName());
        libraryDto.setExerciseExplanation(library.getExerciseExplanation());
        libraryDto.setExerciseUrl(library.getExerciseUrl());
        return libraryDto;
    }
}
