package HealthNote.healthnote.Member_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
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
