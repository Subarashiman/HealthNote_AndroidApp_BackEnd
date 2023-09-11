package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Exercise;
import HealthNote.healthnote.domain.ExerciseLog;
import HealthNote.healthnote.domain.ExerciseSet;
import HealthNote.healthnote.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ExerciseRepository {

    @PersistenceContext
    private EntityManager em;
    private final MemberRepository memberRepository;

    //그날 한 운동기록 저장
    public boolean saveExerciseLog(ExerciseLog exerciseLog){
        em.persist(exerciseLog);
        return true;
    }


    //회원PK를 받아서 해당 유저의 운동기록 초기화
    //외래기 제약 조건 때문에 최하단의 관계 테이블 부터 다 삭제 해줘야 한다.
    //즉, exerciseSet테이블 지우고 exercise테이블 지우고, 마지막으로 exerciseLog테이블 삭제해준다.
    public boolean deleteExerciseLog(Long id){
        Member findMember = (Member) em.createQuery("select m from Member m where m.id=:id")
                .setParameter("id", id)
                .getSingleResult();

        List<ExerciseLog> exerciseLogs = findMember.getExerciseLogs();

        for (ExerciseLog exerciseLog : exerciseLogs) {
            List<Exercise> exercises = exerciseLog.getExercises();

            for (Exercise exercise : exercises) {
                em.createQuery("delete from ExerciseSet es where es.exercise.id=:exerciseId")
                        .setParameter("exerciseId",exercise.getId())
                        .executeUpdate();
            }

            em.createQuery("delete from Exercise e where e.exerciseLog.id=:exerciseLogId")
                    .setParameter("exerciseLogId",exerciseLog.getId())
                    .executeUpdate();
        }

        em.createQuery("delete from ExerciseLog el where el.member.id=:memberId")
                .setParameter("memberId",findMember.getId())
                .executeUpdate();
        return true;
    }










}
