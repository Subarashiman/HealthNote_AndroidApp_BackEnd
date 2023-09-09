package HealthNote.healthnote.controller;

import HealthNote.healthnote.community_dto.CommunitySaveDto;
import HealthNote.healthnote.community_dto.CommunitySaveResponseDto;
import HealthNote.healthnote.community_dto.Community_ID_Boolean;
import HealthNote.healthnote.community_dto.EncodingImageDto;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.repository.MemberRepository;
import HealthNote.healthnote.service.CommunityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;

    //게시글 저장(content-type: multipart/form-data)
    @PostMapping("/community")
    public CommunitySaveResponseDto communitySave(@RequestPart("cmJson")String cmJson,
                                                  @RequestPart("imageFile")MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        CommunitySaveDto csd = objectMapper.readValue(cmJson, CommunitySaveDto.class);

        if(!image.isEmpty()){
            csd.setCommunityPicture(image);
        }
        Community_ID_Boolean cib = communityService.contentSave(csd);
        if(cib.isSuccess() == true){
            CommunitySaveResponseDto csr = new CommunitySaveResponseDto("저장 완료", 200, true);
            csr.setCommunityId(cib.getId());
            return csr;
        }else{
            return new CommunitySaveResponseDto("저장 실패",400,false);
        }
    }

    //게시글 좋아요
    @GetMapping("/community")
    public GoodCountDTO GoodCountAdd(@RequestParam("communityId")Long communityId){
        int goodCount = communityService.GoodCountAdd(communityId);
        return new GoodCountDTO(goodCount,200);
    }



    //마이페이지 해당 유저에 게시판 이미지들을 리스트로 넘기기(순서대로)
    // memberPK를 받으면 해당 유저에 게시판 이미지들을 리스트로 넘기기.
    @GetMapping("/community/user/image")
    public UserCommunityImages MemberCommunityImage(@RequestParam("memberId")Long id){
        List<EncodingImageDto> encodingImages = communityService.UserCommunityImages(id);
        if(encodingImages == null){
            return new UserCommunityImages(null,0,200);
        }
        int imageSize = encodingImages.size();
        return new UserCommunityImages(encodingImages,imageSize,200);
    }



    //전체 게시판 게시글 10개씩 끊어서 데이터 넘겨주기(sns메인)





    @Getter@Setter
    public static class UserCommunityImages{
        private List<EncodingImageDto> encodingImages;
        private int encodingImageCount;
        private int code;

        public UserCommunityImages(List<EncodingImageDto> encodingImages, int encodingImageCount,int code) {
            this.encodingImages = encodingImages;
            this.encodingImageCount = encodingImageCount;
            this.code = code;
        }
    }


    @Getter@Setter
    public static class GoodCountDTO{
        private int resultCount;
        private int code;
        public GoodCountDTO(int resultCount,int code) {
            this.resultCount = resultCount;
            this.code = code;
        }
    }

    private final MemberRepository memberRepository;
    //멤버 저장 테스트
    @GetMapping("/")
    public String TestMemberSave(){
        Member member = new Member();
        member.setUserName("홍길동");
        member.setUserPass("123456");
        member.setEmail("test@naver.com");
        member.setUserId("testid");
        memberRepository.save(member);
        return "완료";

    }



}
