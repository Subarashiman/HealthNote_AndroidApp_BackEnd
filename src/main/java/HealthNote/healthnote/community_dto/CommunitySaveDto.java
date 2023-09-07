package HealthNote.healthnote.community_dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter@Setter
public class CommunitySaveDto {

    private Long id;
    private String title;
    private MultipartFile communityPicture;

}
