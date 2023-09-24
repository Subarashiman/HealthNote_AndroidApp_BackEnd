package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Library {

    @Id
    @Column(name = "library_id")
    @Setter
    private int exerciseNumber;
    @Setter
    private String exerciseName;
    @Setter
    private String exerciseUrl;
    @Setter
    @Column(length = 1000)
    private String exerciseExplanation;

}
