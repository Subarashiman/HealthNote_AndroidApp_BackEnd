package HealthNote.healthnote.service;

import HealthNote.healthnote.domain.Library;
import HealthNote.healthnote.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryService {

    private final LibraryRepository libraryRepository;
    public Library find(int exerciseNumber) {
        return libraryRepository.find(exerciseNumber);
    }
}
