package HealthNote.healthnote.myPage_dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class MyPageSaveDto {
    Long id;
    MultipartFile userImage;
}
