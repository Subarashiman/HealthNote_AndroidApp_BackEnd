package HealthNote.healthnote.service;

import HealthNote.healthnote.domain.Library;
import HealthNote.healthnote.library_dto.LibraryExerciseListDto;
import HealthNote.healthnote.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryService {

    private final LibraryRepository libraryRepository;
    public Library find(int exerciseNumber) {
        return libraryRepository.find(exerciseNumber);
    }

    public List<LibraryExerciseListDto> getExercisesByExerciseNumber(int exerciseNumber) {
        // exerciseNumber의 백의 자리를 구합니다.
        int exerciseCategory = exerciseNumber / 100;

        // 백의 자리에 해당하는 운동 정보 범위를 계산합니다.
        int start = exerciseCategory * 100 + 1;
        int end = (exerciseCategory + 1) * 100 - 1;

        // 해당 범위의 운동 정보를 반환합니다.
        return libraryRepository.findByExerciseNumberBetween(start, end);
    }
}
