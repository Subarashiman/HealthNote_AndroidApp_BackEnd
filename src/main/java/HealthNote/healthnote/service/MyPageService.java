package HealthNote.healthnote.service;

import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.myPage_dto.MyPageSaveDto;
import HealthNote.healthnote.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final MemberRepository memberRepository;

    public Boolean saveUserImage(MyPageSaveDto mpd) throws IOException {
        Member findMember = memberRepository.findOne(mpd.getId());

        // id로 맴버를 못찾으면 실패 반환
        if (findMember == null) {
            return false;
        } else {
            MultipartFile picture = mpd.getUserImage();

            BufferedImage originalImage = ImageIO.read(picture.getInputStream());
            BufferedImage resizedImage = Thumbnails.of(originalImage)
                    .size(100, 100)
                    .asBufferedImage();

            String encodingPictureString = convertImageToBase64(resizedImage, "jpg");
            findMember.setUserImage(encodingPictureString);

            return true;
        }
    }

    // 이미지를 Base64 문자열로 변환하는 메서드
    public String convertImageToBase64(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

}
