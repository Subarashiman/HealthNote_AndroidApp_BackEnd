package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.ExerciseLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Slf4j
public class ExerciseRepository {

    @PersistenceContext
    private EntityManager em;

    //그날 한 운동기록 저장
    public boolean saveExerciseLog(ExerciseLog exerciseLog){
        em.persist(exerciseLog);
        return true;
    }

}
