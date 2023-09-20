package HealthNote.healthnote.controller;

import HealthNote.healthnote.myPage_dto.MyPageSaveDto;
import HealthNote.healthnote.myPage_dto.MyPageSaveImageResponseDto;
import HealthNote.healthnote.service.MyPageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PostMapping
    public MyPageSaveImageResponseDto myPageSaveImageResponseDto(@RequestPart("mpJson") String mpJson,
                                                                 @RequestPart("imageFile") MultipartFile userImage) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        MyPageSaveDto mpsd = objectMapper.readValue(mpJson, MyPageSaveDto.class);


        if (!userImage.isEmpty()) {
            mpsd.setUserImage(userImage);
        }
        Boolean mpib = myPageService.saveImage(mpsd);

        return new MyPageSaveImageResponseDto(200);
    }
}
