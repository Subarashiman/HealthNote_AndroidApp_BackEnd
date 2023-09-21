package HealthNote.healthnote.controller;

import HealthNote.healthnote.exercise_dto.*;
import HealthNote.healthnote.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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



    //설정창(데이터초기화)   ---->  유저 고유 id(PK) 프론트가 넘겨주면  해당 유저의 운동기록 초기화(삭제)
    @DeleteMapping("/exercise")
    public ExerciseSuccessDto deleteExerciseLog(@RequestParam("memberId")Long id){
        boolean success = exerciseService.deleteAllExerciseLog(id);
        return new ExerciseSuccessDto(success,200,"삭제되었습니다.");
    }



    //홈화면(이번주 운동)
    //일주일치(주간) -----> ex) 오늘이 화요일이면 월,화만 넘겨주고 나머지 null. (컬렉션 사용? 인덱스7개해서)
    //운동완료 여부, 운동 시간
    @GetMapping("/exercise/week")
    public ExerciseWeekDto WeekExerciseData(@RequestParam("memberId")Long memberId){

        ExerciseWeekDto exerciseWeekDto = exerciseService.WeekExerciseData(memberId);

        if(exerciseWeekDto == null){
            ExerciseWeekDto failExerciseWeekDto = new ExerciseWeekDto();
            int[]failWeek = new int[]{0,0,0,0,0,0,0};
            failExerciseWeekDto.setWeekExerciseCheck(failWeek);
            failExerciseWeekDto.setCode(400);
            return failExerciseWeekDto;
        }

        exerciseWeekDto.setCode(200);
        return exerciseWeekDto;
    }





    //1) 30일치 운동 Date만 넘겨주기(프론트에서 memberPK 넘겨 받음)
    @GetMapping("/exercise/month")
    public ExerciseMonthDto MonthExerciseData(@RequestParam("memberId")Long id){
        LocalDate[] localDates = exerciseService.MonthExercsieDate(id);

        return new ExerciseMonthDto(localDates,true,200);
    }




    //2) 클릭한 그 날의 운동 데이터만 넘겨주기(프론트에서 memberPK, Date받기)
    @GetMapping("/exercise/day")
    public ExerciseLogDto DayExerciseLog(@RequestParam("memberId")Long id,
                                         @RequestParam("date")String date){
        try {
            LocalDate localDate = LocalDate.parse(date);

            //해당회원이 없거나, 잘못된 정보가 들어온 경우.
            ExerciseLogDto exerciseLogDto = exerciseService.DayExerciseLogData(id, localDate);
            if (exerciseLogDto == null) {
                ExerciseLogDto exerciseLogDto1 = new ExerciseLogDto();
                exerciseLogDto1.setExerciseDtos(null);
                exerciseLogDto1.setTotalWeight(0);
                exerciseLogDto1.setMemberId(id);
                exerciseLogDto1.setTotalTime(0);
                return exerciseLogDto1;
            }
            return exerciseLogDto;
        }catch (DateTimeParseException e){
            ExerciseLogErrorDto exerciseLogDtoError = new ExerciseLogErrorDto();
            exerciseLogDtoError.setError("Invalid date format. Please use yyyy-MM-dd.");
            return exerciseLogDtoError;
        }
    }









}
