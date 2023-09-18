package HealthNote.healthnote.admin_page.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MainPageDto {
    private int totalUserCount;
    private int usingUserCount;
    private int outUserCount;
    private int communityCount;
    private int exerciseCount;
}
