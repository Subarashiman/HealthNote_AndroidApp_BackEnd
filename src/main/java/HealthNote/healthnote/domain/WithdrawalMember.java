package HealthNote.healthnote.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class WithdrawalMember {

    @Id@GeneratedValue
    @Column(name = "withdrawalmember_id")
    private Long id;

    @Setter
    private String withdrawalDate;
    @Setter
    private Long userPk;
    @Setter
    private String userId;
    @Setter
    private String userName;

}
