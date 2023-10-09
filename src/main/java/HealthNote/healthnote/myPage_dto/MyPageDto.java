package HealthNote.healthnote.myPage_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageDto {

    private int code;
    private String userName;
    private String introduction;
    private String email;
    private String userImage;

    public MyPageDto(int code, String userName, String introduction, String email, String userImage) {
        this.code = code;
        this.userName = userName;
        this.introduction = introduction;
        this.email = email;
        this.userImage = userImage;
    }

    public MyPageDto(int code) {
        this.code = code;
    }
}
