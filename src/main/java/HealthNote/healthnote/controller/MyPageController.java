package HealthNote.healthnote.controller;

import HealthNote.healthnote.myPage_dto.MyPageSaveDto;
import HealthNote.healthnote.myPage_dto.MyPageSaveImageResponseDto;
import HealthNote.healthnote.service.MyPageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    //사진 파일을 받은 후 사진 크기를 압축해서 줄이고, base 64 문자열 형식으로 encoding한 후 encoding 된 문자열 파일을 db 저장.
    @PostMapping("/api/myPage/userImage")
    public MyPageSaveImageResponseDto saveUserImage(@RequestPart("mpJson") String mpJson,
                                                    @RequestPart("userImage") MultipartFile userImage) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        MyPageSaveDto mpd = objectMapper.readValue(mpJson, MyPageSaveDto.class);

        if (!userImage.isEmpty()) {
            mpd.setUserImage(userImage);
        }
        Boolean result = myPageService.saveUserImage(mpd);

        if (result) {
            return new MyPageSaveImageResponseDto(200);
        } else {
            return new MyPageSaveImageResponseDto(400);
        }
    }
}
