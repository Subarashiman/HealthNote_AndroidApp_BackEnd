package HealthNote.healthnote.member_dto;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FormDto {
    private Long id;
    private String userId;
    private String userPass;
    private String userName;
    private String email;
}
