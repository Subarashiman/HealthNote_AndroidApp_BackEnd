package HealthNote.healthnote.admin_page.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class AdminCommunityDto {

    private Long id;
    private LocalDate date;
    private String communityPicture;
    private String title;
    private int goodCount;
    private String userId;

    public AdminCommunityDto(Long id, LocalDate date, String communityPicture, String title, int goodCount, String userId) {
        this.id = id;
        this.date = date;
        this.communityPicture = communityPicture;
        this.title = title;
        this.goodCount = goodCount;
        this.userId = userId;
    }
}
