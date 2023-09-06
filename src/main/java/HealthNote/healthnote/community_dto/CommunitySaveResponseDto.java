package HealthNote.healthnote.community_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CommunitySaveResponseDto {

    private String result;
    private int code;
    private boolean success;

    public CommunitySaveResponseDto(String result, int code, boolean success) {
        this.result = result;
        this.code = code;
        this.success = success;
    }
}
