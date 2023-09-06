package HealthNote.healthnote.domain;

import jakarta.persistence.*;
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
    @Setter @Lob
    @Column(length = 5000000)
    private byte[] exerciseImage;
    @Setter
    private String exerciseUrl;
    @Setter
    private String exerciseExplanation;

}
