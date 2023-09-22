package HealthNote.healthnote.member_dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdDto {
    private String userId;
    private int code;

    public FindIdDto(String userId, int code) {
        this.userId = userId;
        this.code = code;
    }
}
