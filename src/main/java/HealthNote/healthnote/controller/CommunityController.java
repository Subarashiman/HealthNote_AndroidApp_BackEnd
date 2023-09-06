package HealthNote.healthnote.controller;

import HealthNote.healthnote.community_dto.CommunitySaveDto;
import HealthNote.healthnote.community_dto.CommunitySaveResponseDto;
import HealthNote.healthnote.community_dto.CommunityService;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    //게시글 저장(content-type: multipart/form-data)
    @PostMapping(value = "/community", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommunitySaveResponseDto communitySave(@RequestPart("CMjson")String cmJson,
                                                  @RequestPart("imageFile")MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        CommunitySaveDto csd = objectMapper.readValue(cmJson, CommunitySaveDto.class);

        if(!image.isEmpty()){
            csd.setCommunityPicture(image);
        }

        boolean saveChecked = communityService.contentSave(csd);
        if(saveChecked == true){
            return new CommunitySaveResponseDto("저장 완료",200,true);
        }else{
            return new CommunitySaveResponseDto("저장 실패",400,false);
        }
    }


    
    
    //게시글 수정
    
    
    //게시글 삭제
    
    
    //게시글 좋아요
    
    
    //게시글 좋아요 취소




    private final MemberRepository memberRepository;
    //멤버 저장 테스트
    @GetMapping("/")
    public String TestMemberSave(){
        Member member = new Member();
        member.setUserName("홍길동");
        member.setAge(19);
        member.setUserPass("123456");
        member.setEmail("test@naver.com");
        member.setSex("남자");
        member.setUserId("testid");
        memberRepository.save(member);
        return "완료";

    }



}
