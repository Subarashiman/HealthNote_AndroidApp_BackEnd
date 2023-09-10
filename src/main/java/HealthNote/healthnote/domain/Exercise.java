package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Exercise {

    @Id
    @GeneratedValue
    @Column(name = "exercise_id")
    private Long id;
    @Setter
    private String exerciseName;
    @Setter
    private int setCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exerciselog_id")
    private ExerciseLog exerciseLog;
    @Setter
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<ExerciseSet> exerciseSets = new ArrayList<>();


    //연관관계 메서드
    public void setExerciseLog(ExerciseLog exerciseLog){
        this.exerciseLog = exerciseLog;
        exerciseLog.getExercises().add(this);
    }



}
