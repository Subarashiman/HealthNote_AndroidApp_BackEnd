package HealthNote.healthnote.member_dto;


import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SignUpDto {
    private int code;

    public SignUpDto(int code) {
        this.code = code;
    }
}
