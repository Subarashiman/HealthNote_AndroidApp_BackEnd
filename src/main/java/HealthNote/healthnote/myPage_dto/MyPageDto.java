package HealthNote.healthnote.myPage_dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyPageDto {
    Long id;
    String userName;
    String email;
    String introduction;
    byte[] userImage;

    public MyPageDto(Long id, String userName, String email, String introduction, byte[] userImage) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.introduction = introduction;
        this.userImage = userImage;
    }
}
