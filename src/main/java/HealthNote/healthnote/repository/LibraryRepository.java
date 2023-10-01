package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Library;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LibraryRepository {

    @PersistenceContext
    private EntityManager em;

    public Library find(int exerciseNum) {
        return em.find(Library.class, exerciseNum);
    }
}
