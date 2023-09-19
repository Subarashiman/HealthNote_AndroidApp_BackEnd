package HealthNote.healthnote.admin_page.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class usingUserDto {

    private Long Pk;
    private String joinDate;
    private String userId;
    private String userName;
    private String userEmail;

}
