package HealthNote.healthnote.member_dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserPassDto {
    private int code;

    public UpdateUserPassDto(int code) {
        this.code = code;
    }
}
