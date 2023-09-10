package HealthNote.healthnote.controller;

import HealthNote.healthnote.domain.ExerciseLog;
import HealthNote.healthnote.exercise_dto.ExerciseLogDto;
import HealthNote.healthnote.exercise_dto.ExerciseSuccessDto;
import HealthNote.healthnote.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExerciseController {

    private final ExerciseService exerciseService;


    //오늘의 운동기록 저장
    // 일대 다 관계 테이블(트리구조)데이터 어떻게 받을 건지....리스트 객체 받을 수 있는지 확인
    @PostMapping("/exercise")
    public ExerciseSuccessDto saveExerciseLog(@RequestBody ExerciseLogDto exerciseLogDto){
        boolean checkSuccess = exerciseService.saveExerciseLog(exerciseLogDto);

        if(checkSuccess != true){
            return new ExerciseSuccessDto(checkSuccess,400,"운동저장은 하루에 한번만 가능합니다.");
        }

        return new ExerciseSuccessDto(checkSuccess,200,"성공적으로 저장되었습니다.");

    }





}
