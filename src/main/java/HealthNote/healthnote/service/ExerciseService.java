package HealthNote.healthnote.service;


import HealthNote.healthnote.domain.Exercise;
import HealthNote.healthnote.domain.ExerciseLog;
import HealthNote.healthnote.domain.ExerciseSet;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.exercise_dto.*;
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

        return exerciseRepository.saveExerciseLog(exerciseLog);
    }




    // 유저 고유 id(PK) 프론트가 넘겨주면  해당 유저의 운동기록 초기화(삭제)
    public boolean deleteAllExerciseLog(Long id){
        return exerciseRepository.deleteExerciseLog(id);
    }




    //홈화면(이번주 운동)
    //일주일치(주간) -----> ex) 오늘이 화요일이면 월,화만 넘겨주고 나머지 false. (컬렉션 사용? 인덱스7개해서)
    //운동완료 여부, 운동 시간
    public ExerciseWeekDto WeekExerciseData(Long id){
        if(memberRepository.findOne(id)==null){
            return null;
        }

        ExerciseWeekDto exerciseWeekDto = new ExerciseWeekDto();
        LocalDate currentDate = LocalDate.now();
        String currentDayOfWeek = currentDate.getDayOfWeek().toString(); //오늘 요일을 가져옴

        //만약 운동기록이 하나도 존재하지 않는 경우 바로 리턴
        List<ExerciseLog> firstCheckExerciseLogs = exerciseRepository.pickWeekExerciseLog(id, 1);
        if(firstCheckExerciseLogs == null) {
            return NoExerciseLogData();
        }

        //운동기록이 하나라도 존재할 경우.
        int[] dayCheck = new int[7];
        int i = 0;
        switch (currentDayOfWeek){
            case "MONDAY":
                List<ExerciseLog> exerciseLogs = exerciseRepository.pickWeekExerciseLog(id, 1);
                for (ExerciseLog exerciseLog : exerciseLogs) {
                    if(exerciseLog.getExerciseDate().equals(currentDate)){
                        dayCheck[0] = exerciseLog.getTotalTime();
                        exerciseWeekDto.setWeekExerciseCheck(dayCheck);
                        return exerciseWeekDto;
                    }else{
                        return NoExerciseLogData();
                    }
                }
                break;
            //화요일
            case"TUESDAY":
                List<ExerciseLog> exerciseLogs1 = exerciseRepository.pickWeekExerciseLog(id, 2);

                for (ExerciseLog exerciseLog : exerciseLogs1){
                    for(i=0;i<2;i++){
                        if(exerciseLog.getExerciseDate().equals(currentDate.minusDays(i))){
                            dayCheck[1-i] = exerciseLog.getTotalTime();
                            break;
                        }
                    }
                }
                exerciseWeekDto.setWeekExerciseCheck(dayCheck);
                break;
            case"WEDNESDAY":
                List<ExerciseLog> exerciseLogs2 = exerciseRepository.pickWeekExerciseLog(id, 3);

                for (ExerciseLog exerciseLog : exerciseLogs2){
                    for(i=0;i<3;i++){
                        if(exerciseLog.getExerciseDate().equals(currentDate.minusDays(i))){
                            dayCheck[2-i] = exerciseLog.getTotalTime();
                            break;
                        }
                    }
                }
                exerciseWeekDto.setWeekExerciseCheck(dayCheck);
                break;
            case"THURSDAY":
                List<ExerciseLog> exerciseLogs3 = exerciseRepository.pickWeekExerciseLog(id, 4);

                for (ExerciseLog exerciseLog : exerciseLogs3){
                    for(i=0;i<4;i++){
                        if(exerciseLog.getExerciseDate().equals(currentDate.minusDays(i))){
                            dayCheck[3-i] = exerciseLog.getTotalTime();
                            break;
                        }
                    }
                }
                exerciseWeekDto.setWeekExerciseCheck(dayCheck);
                break;
            case"FRIDAY":
                List<ExerciseLog> exerciseLogs4 = exerciseRepository.pickWeekExerciseLog(id, 5);

                for (ExerciseLog exerciseLog : exerciseLogs4){
                    for(i=0;i<5;i++){
                        if(exerciseLog.getExerciseDate().equals(currentDate.minusDays(i))){
                            dayCheck[4-i] = exerciseLog.getTotalTime();
                            break;
                        }
                    }
                }
                exerciseWeekDto.setWeekExerciseCheck(dayCheck);
                break;
            case"SATURDAY":
                List<ExerciseLog> exerciseLogs5 = exerciseRepository.pickWeekExerciseLog(id, 6);

                for (ExerciseLog exerciseLog : exerciseLogs5){
                    for(i=0;i<6;i++){
                        if(exerciseLog.getExerciseDate().equals(currentDate.minusDays(i))){
                            dayCheck[5-i] = exerciseLog.getTotalTime();
                            break;
                        }
                    }
                }
                exerciseWeekDto.setWeekExerciseCheck(dayCheck);

                break;
            case"SUNDAY":
                List<ExerciseLog> exerciseLogs6 = exerciseRepository.pickWeekExerciseLog(id, 7);

                for (ExerciseLog exerciseLog : exerciseLogs6){
                    for(i=0;i<7;i++){
                        if(exerciseLog.getExerciseDate().equals(currentDate.minusDays(i))){
                            dayCheck[6-i] = exerciseLog.getTotalTime();
                            break;
                        }
                    }
                }
                exerciseWeekDto.setWeekExerciseCheck(dayCheck);
                break;
        }

        return exerciseWeekDto;
    }



    //1) 30일치 운동 Date만 넘겨주기(프론트에서 memberPK 넘겨 받음)
    public LocalDate[] MonthExercsieDate(Long id){
        List<ExerciseLog> exerciseLogs = exerciseRepository.MonthExerciseLogData(id);
        if(exerciseLogs == null)return null;

        LocalDate[] logDates = new LocalDate[exerciseLogs.size()];
        int i=0;
        for (ExerciseLog exerciseLog : exerciseLogs) {
            logDates[i] = exerciseLog.getExerciseDate();
            i++;
        }
        return logDates;
    }


    //2) 클릭한 그 날의 운동 데이터만 넘겨주기(프론트에서 memberPK, Date받기)
    public ExerciseLogDto DayExerciseLogData(Long id, LocalDate date){
        ExerciseLog exerciseLog = exerciseRepository.DayExerciseData(id, date);
        if(exerciseLog == null){
            return null;
        }
        ExerciseLogDto exerciseLogDto = new ExerciseLogDto();
        exerciseLogDto.setTotalTime(exerciseLog.getTotalTime());
        exerciseLogDto.setTotalWeight(exerciseLog.getTotalWeight());
        exerciseLogDto.setMemberId(id);

        //엔티티에 운동이랑 세트 옮기기.
        List<Exercise> exercises = exerciseLog.getExercises();
        List<ExerciseDto> exerciseDtos = new ArrayList<>();
        for (Exercise exercise : exercises) {
            ExerciseDto exerciseDto = new ExerciseDto();
            exerciseDto.setExerciseName(exercise.getExerciseName());

            List<ExerciseSet> exerciseSets = exercise.getExerciseSets();
            List<ExerciseSetDto> exerciseSetDtos = new ArrayList<>();
            for (ExerciseSet exerciseSet : exerciseSets) {
                ExerciseSetDto exerciseSetDto = new ExerciseSetDto();
                exerciseSetDto.setCount(exerciseSet.getCount());
                exerciseSetDto.setWeight(exerciseSet.getWeight());
                exerciseSetDtos.add(exerciseSetDto);
            }
            exerciseDto.setExerciseSetDtos(exerciseSetDtos);
            exerciseDtos.add(exerciseDto);
        }
        exerciseLogDto.setExerciseDtos(exerciseDtos);

        return exerciseLogDto;

    }













    //주간 운동기록이 없을 경우 반환하는 메서드.
    private ExerciseWeekDto NoExerciseLogData(){
        ExerciseWeekDto exerciseWeekDto = new ExerciseWeekDto();
        int[]Integers = new int[7];
        for(int i=0;i<7;i++)Integers[i]=0;
        exerciseWeekDto.setWeekExerciseCheck(Integers);
        return exerciseWeekDto;
    }
















}
