package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class ExerciseSet {

    @Id
    @GeneratedValue
    @Column(name = "exerciseset_id")
    private Long id;
    @Setter
    private int count;
    @Setter
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;


    //연관관계 메서드
    public void setExercise(Exercise exercise){
        this.exercise = exercise;
        exercise.getExerciseSets().add(this);
    }


}
