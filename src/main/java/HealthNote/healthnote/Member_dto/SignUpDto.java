package HealthNote.healthnote.Member_dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpDto {
    private int code;

    public SignUpDto(int code) {
        this.code = code;
    }
}
