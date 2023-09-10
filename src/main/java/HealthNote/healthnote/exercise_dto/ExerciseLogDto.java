package HealthNote.healthnote.exercise_dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ExerciseLogDto {

    private Long memberId;      //맵핑을 하기위한 해당 회원을 찾기 위해서 회원PK
    private int totalTime;
    private int totalWeight;
    private List<ExerciseDto> exerciseDtos;


}
