package HealthNote.healthnote.community_dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter@Setter
public class CommunitySaveDto {

    private String content;
    private MultipartFile communityPicture;
    private String userId;

}
