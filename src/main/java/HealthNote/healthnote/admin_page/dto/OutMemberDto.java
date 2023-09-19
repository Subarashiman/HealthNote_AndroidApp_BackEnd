package HealthNote.healthnote.admin_page.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OutMemberDto {

    private Long pk;
    private Long userPk;
    private String withdrawalDate;
    private String userId;
    private String userName;
}
