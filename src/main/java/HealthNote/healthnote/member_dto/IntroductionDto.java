package HealthNote.healthnote.member_dto;

import lombok.Getter;

@Getter
public class IntroductionDto {
    int code;

    public IntroductionDto(int code) {
        this.code = code;
    }
}
