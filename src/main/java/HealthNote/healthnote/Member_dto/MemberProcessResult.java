package HealthNote.healthnote.Member_dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberProcessResult {
    String userId;
    Boolean result;
    int code;
    String message;

    public MemberProcessResult(Boolean result, int code, String message) {
        this.result = result;
        this.code = code;
        this.message = message;
    }

    public MemberProcessResult(Boolean result, int code) {
        this.result = result;
        this.code = code;
    }

    public MemberProcessResult( String userId, int code) {
        this.userId = userId;
        this.code = code;
    }
}
