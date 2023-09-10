package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ExerciseLog {

    @Id@GeneratedValue
    @Column(name = "exerciselog_id")
    private Long id;
    @Setter
    private LocalDate exerciseDate;
    @Setter
    private int totalTime;
    @Setter
    private int totalWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Setter
    @OneToMany(mappedBy = "exerciseLog",cascade = CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();



    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getExerciseLogs().add(this);
    }


}
