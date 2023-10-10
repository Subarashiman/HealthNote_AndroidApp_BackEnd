package HealthNote.healthnote.member_dto;


import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class UpdateUserPassDto {
    private int code;

    public UpdateUserPassDto(int code) {
        this.code = code;
    }
}
