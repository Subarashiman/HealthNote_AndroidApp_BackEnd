package HealthNote.healthnote.admin_page.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class AdminCommunityListDto {

    private Long pk;
    private LocalDate writeDate;
    private String title;
    private int goodCount;
    private String userId;

    public AdminCommunityListDto(Long pk, LocalDate writeDate, String title, int goodCount, String userId) {
        this.pk = pk;
        this.writeDate = writeDate;
        this.title = title;
        this.goodCount = goodCount;
        this.userId = userId;
    }
}
