package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Library;
import HealthNote.healthnote.library_dto.LibraryExerciseListDto;
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

    public List<LibraryExerciseListDto> findByExerciseNumberBetween(int start, int end) {
        // 시작과 끝 범위에 해당하는 운동의 exerciseNumber와 exerciseName을 조회하는 쿼리를 작성하여 반환
        List resultList = em.createQuery("SELECT new HealthNote.healthnote.library_dto.LibraryExerciseListDto(l.exerciseNumber,l.exerciseName)"
                        + " FROM Library l WHERE l.exerciseNumber BETWEEN :start AND :end")
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
        return resultList;
    }

}
