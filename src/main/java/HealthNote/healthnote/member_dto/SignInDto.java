package HealthNote.healthnote.member_dto;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SignInDto {

    private Long id;
    private int code;



    public SignInDto(Long memberId, int code) {
        this.id = memberId;
        this.code = code;
    }
}
