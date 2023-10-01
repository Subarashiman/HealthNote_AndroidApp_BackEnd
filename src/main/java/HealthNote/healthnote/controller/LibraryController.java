package HealthNote.healthnote.controller;

import HealthNote.healthnote.domain.Library;
import HealthNote.healthnote.library_dto.LibraryDto;
import HealthNote.healthnote.repository.LibraryRepository;
import HealthNote.healthnote.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private LibraryService libraryService;
    // n00 -> 운동 카테고리(exerciseName) 넘김 , n0m -> exerciseName, exerciseExplaination, exerciseUrl 넘김
    // 운동 정보
//    @GetMapping
//    public LibraryDto findExercises(@RequestParam("exerciseNumber") int exerciseNumber) {
//        Library result = libraryService.find(exerciseNumber);
//        if (result == null) {
//            return new LibraryDto()
//        }
//    }
}
