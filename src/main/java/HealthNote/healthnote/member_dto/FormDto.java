package HealthNote.healthnote.member_dto;

import lombok.Data;

@Data
public class FormDto {
    private Long id;
    private String userId;
    private String userPass;
    private String userName;
    private String email;
}
