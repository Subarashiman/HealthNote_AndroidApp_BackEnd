package HealthNote.healthnote.Member_dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignInDto {
    @Setter
    private Long memberId;
    private int code;



    public SignInDto(Long memberId, int code) {
        this.memberId = memberId;
        this.code = code;
    }
}
