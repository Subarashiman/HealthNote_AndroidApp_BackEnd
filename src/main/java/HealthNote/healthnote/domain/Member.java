package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id@GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Setter
    private String userId;
    @Setter
    private String userPass;
    @Setter
    private String userName;
    @Setter
    private String email;
    @Lob @Setter
    @Column(length = 5000000)
    private String userImage;
    @Setter
    private String introduction;
<<<<<<< HEAD
=======
    @Setter
    private String email;
    @Setter
    private String joinDate;
>>>>>>> 5f556d4d9bb9d1089546b01df710ae2850c74e2d


    @Setter
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Community> communities = new ArrayList<>();
    @Setter
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<ExerciseLog> exerciseLogs = new ArrayList<>();



}
