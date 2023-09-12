package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Exercise;
import HealthNote.healthnote.domain.ExerciseLog;
import HealthNote.healthnote.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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



    //해당 회원의 일주일 운동기록을 가져오기 위한 메서드
    public List<ExerciseLog> pickWeekExerciseLog(Long id,int dataCount){
        //해당 유저의 전체 운동기록 갯수를 가져옴
        Long totalExerciseLogCount = (Long) em.createQuery("select count(el) from ExerciseLog el where el.member.id=:id")
                .setParameter("id",id)
                .getSingleResult();
        //만약 운동기록이 하나도 없다면 null을 반환
        int totalLogCount = totalExerciseLogCount.intValue();
        if(totalLogCount == 0){
            return null;
        }
        //존재하는 운동기록 개수가 dataCount보다 적으면 존재하는 운동기록 만큼 가져와
        int maxResults = Math.min(dataCount, totalLogCount);
        //해당 유저의 모든 운동기록을 가장 최신순부터 가져와
        return em.createQuery("select el from ExerciseLog el where el.member.id=:id order by el.id desc")
                .setParameter("id",id)
                .setFirstResult(0)
                .setMaxResults(maxResults)
                .getResultList();
    }




    //해당회원의 30일치 운동한 날짜 가져오기
    public List<ExerciseLog> MonthExerciseLogData(Long id){
        Long totalExerciseLogCount = (Long) em.createQuery("select count(el) from ExerciseLog el where el.member.id=:id")
                .setParameter("id",id)
                .getSingleResult();

        //만약 운동기록이 하나도 없다면 null을 반환
        int totalLogCount = totalExerciseLogCount.intValue();
        if(totalLogCount == 0){
            return null;
        }
        //존재하는 운동기록이 30일치보다 적으면 존재하는 운동기록 만큼 가져와
        int maxResults = Math.min(30, totalLogCount);

        return em.createQuery("select el from ExerciseLog el where el.member.id=:id order by el.id desc ")
                .setParameter("id",id)
                .setFirstResult(0)
                .setMaxResults(maxResults)
                .getResultList();
    }




    //memberId랑 해당 운동date받아서 특정 운동기록 넘겨주기
    public ExerciseLog DayExerciseData(Long memberId, LocalDate date){
        try {
            return (ExerciseLog) em.createQuery("select el from ExerciseLog el " +
                            "where el.member.id =:memberId and el.exerciseDate =:date")
                    .setParameter("memberId", memberId)
                    .setParameter("date", date)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }





}
