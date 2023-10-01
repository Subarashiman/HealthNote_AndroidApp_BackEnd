package HealthNote.healthnote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity@Getter
public class CommunityLikeMember {
    @Setter
    @Id@GeneratedValue
    private Long id;
    @Setter
    private Long MemberId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "community_id")
    private Community community;


    //연관관계 메서드
    public void setCommunity(Community community){
        this.community = community;
        community.getCommunityLikeMember().add(this);
    }

}
