package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Library;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class LibraryRepository {

    @PersistenceContext
    private EntityManager em;

    public Library find(int exerciseNum) {
        return em.find(Library.class, exerciseNum);
    }

    public List<Library> findByExerciseNumberBetween(int start, int end) {
        // 시작과 끝 범위에 해당하는 운동의 exerciseNumber와 exerciseName을 조회하는 쿼리를 작성하여 반환
        return em.createQuery("SELECT l.exerciseNumber, l.exerciseName FROM Library l WHERE l.exerciseNumber BETWEEN :start AND :end")
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

}
