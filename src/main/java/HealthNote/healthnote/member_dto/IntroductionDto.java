package HealthNote.healthnote.member_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class IntroductionDto {
    int code;

    public IntroductionDto(int code) {
        this.code = code;
    }
}
