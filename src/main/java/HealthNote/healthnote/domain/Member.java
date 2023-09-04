package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String sex;
    @Setter
    private int age;
    @Setter
    private int height;
    @Setter
    private int weight;
    @Lob @Setter
    private byte[] userImage;
    @Setter
    private String introduction;
    @Setter
    private String email;
    @Setter
    private String notification;
    @Setter
    private int following;
    @Setter
    private int follower;


    @Setter
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Community> communities = new ArrayList<>();
    @Setter
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<ExerciseLog> exerciseLogs = new ArrayList<>();



}
