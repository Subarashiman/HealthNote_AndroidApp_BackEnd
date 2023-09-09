package HealthNote.healthnote.community_dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EncodingImageDto {

    private String base64EncodingImage;
    private int goodCount;

    public EncodingImageDto(String base64EncodingImage, int goodCount) {
        this.base64EncodingImage = base64EncodingImage;
        this.goodCount = goodCount;
    }
}
