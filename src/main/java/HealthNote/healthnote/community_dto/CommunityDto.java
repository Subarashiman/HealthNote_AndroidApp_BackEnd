package HealthNote.healthnote.community_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CommunityDto {
    // 유저 사진, 유저이름, 게시판 사진, 타이틀, 좋아요 수
    private Long communityId;
    private String communityPicture;
    private String title;
    private int goodCount;
    private String userImage;
    private String userName;

    public CommunityDto(String userImage, String userName, String communityPicture, String title, int goodCount,Long communityId) {
        this.userImage = userImage;
        this.userName = userName;
        this.communityPicture = communityPicture;
        this.title = title;
        this.goodCount = goodCount;
        this.communityId = communityId;
    }
}
