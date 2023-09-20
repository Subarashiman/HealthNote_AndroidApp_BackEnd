package HealthNote.healthnote.myPage_dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyPageSaveImageResponseDto {
    int code;

    public MyPageSaveImageResponseDto(int code) {
        this.code = code;
    }
}
