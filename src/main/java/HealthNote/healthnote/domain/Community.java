package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Community {


    @Id@GeneratedValue
    @Column(name = "community_id")
    private Long id;
    @Setter
    private LocalDateTime date;
    @Setter
    private String content;
    @Lob @Setter
    private byte[] communityPicture;
    @Setter
    private int goodCount;


    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getCommunities().add(this);
    }

}
