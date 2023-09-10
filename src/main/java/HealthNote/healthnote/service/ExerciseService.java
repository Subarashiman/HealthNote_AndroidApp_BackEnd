package HealthNote.healthnote.service;


import HealthNote.healthnote.domain.Exercise;
import HealthNote.healthnote.domain.ExerciseLog;
import HealthNote.healthnote.domain.ExerciseSet;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.exercise_dto.ExerciseDto;
import HealthNote.healthnote.exercise_dto.ExerciseLogDto;
import HealthNote.healthnote.exercise_dto.ExerciseSetDto;
import HealthNote.healthnote.repository.ExerciseRepository;
import HealthNote.healthnote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MemberRepository memberRepository;

    //그날 한 운동 기록 저장기능(Dto에서 엔티티로 변환)
    public boolean saveExerciseLog(ExerciseLogDto exerciseLogDto){
        ExerciseLog exerciseLog = new ExerciseLog(); //로그 엔티티생성
        Member findMember = memberRepository.findOne(exerciseLogDto.getMemberId()); //해당 맴버찾기
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();

        //오늘 운동을 이미 해서 게시글을 올렸는지 확인 후 존재한다면 return false 한다.(운동 게시글은 하루에 하나씩)
        List<ExerciseLog> exerciseLogs = findMember.getExerciseLogs();
        for (ExerciseLog log : exerciseLogs) {
            if(log.getExerciseDate().equals(date)){
                return false;
            }
        }
        exerciseLog.setMember(findMember);      //회원과 연관관계 맵핑
        exerciseLog.setExerciseDate(date);
        exerciseLog.setTotalTime(exerciseLogDto.getTotalTime());
        exerciseLog.setTotalWeight(exerciseLogDto.getTotalWeight());


        //운동기록 엔티티에 그날 한 운동들 넣어주기
        List<ExerciseDto> exerciseDtos = exerciseLogDto.getExerciseDtos();
        List<Exercise> exercises = new ArrayList<>();

        //운동들을 다 꺼내서 엔티티에 값 넣어주기
        for (ExerciseDto exerciseDto : exerciseDtos) {
            Exercise exercise = new Exercise();     //운동 엔티티 생성
            exercise.setExerciseName(exerciseDto.getExerciseName());
            exercise.setSetCount(exerciseDto.getExerciseSetDtos().size());
            exercise.setExerciseLog(exerciseLog);   //운동엔티티를 log엔티티에 맵핑

            //운동별 세트들을 다 꺼내서 엔티티에 값 넣어주기
            List<ExerciseSet> exerciseSets = new ArrayList<>();
            List<ExerciseSetDto> exerciseSetDtos = exerciseDto.getExerciseSetDtos();
            for (ExerciseSetDto exerciseSetDto : exerciseSetDtos) {
                ExerciseSet exerciseSet = new ExerciseSet();
                exerciseSet.setCount(exerciseSetDto.getCount());
                exerciseSet.setWeight(exerciseSetDto.getWeight());
                exerciseSet.setExercise(exercise);  //세트엔티티를 운동엔티티에 맵핑
                exerciseSets.add(exerciseSet);
            }
            exercise.setExerciseSets(exerciseSets);     //운동에 세트들을 넣어주고

            exercises.add(exercise);    //운동을 운동리스트에 넣기
        }

        //for문이 돌고나면 운동에 세트들이 리스트로 담기고, 운동들이 리스트에 담김---> 운동 로그 엔티티에 운동 리스트 넣기
        exerciseLog.setExercises(exercises);


        boolean success = exerciseRepository.saveExerciseLog(exerciseLog);


        return success;
    }

}
