package HealthNote.healthnote.member_dto;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class WithdrawalDto {
    private boolean success;
    private int code;
    private String message;

    public WithdrawalDto(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
