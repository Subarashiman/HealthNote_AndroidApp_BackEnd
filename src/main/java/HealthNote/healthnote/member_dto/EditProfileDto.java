package HealthNote.healthnote.member_dto;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EditProfileDto {
    private int code;

    public EditProfileDto(int code) {
        this.code = code;
    }
}
