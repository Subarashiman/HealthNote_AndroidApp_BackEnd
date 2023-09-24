package HealthNote.healthnote.member_dto;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FindPwDto {
    private Long id;
    private int code;

    public FindPwDto(Long id, int code) {
        this.id = id;
        this.code = code;
    }
}
