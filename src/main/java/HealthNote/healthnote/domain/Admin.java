package HealthNote.healthnote.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Admin {

    @Id@GeneratedValue
    @Column(name = "admin_Pk")
    private Long id;
    @Setter
    private String adminId;
    @Setter
    private String adminPass;
    @Setter
    private String phoneNumber;
    @Setter
    private String adminName;
    @Setter
    private String adminEmail;
}
