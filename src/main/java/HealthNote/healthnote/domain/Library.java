package HealthNote.healthnote.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Library {

    @Id@GeneratedValue
    @Column(name = "library_id")
    private Long id;
    @Setter
    private int exerciseNumber;
    @Setter
    private String exerciseName;
    @Setter
    private byte[] exerciseImage;
    @Setter
    private String exerciseUrl;
    @Setter
    private String exerciseExplanation;

}
