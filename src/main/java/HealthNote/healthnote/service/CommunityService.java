package HealthNote.healthnote.service;

import HealthNote.healthnote.community_dto.CommunityDto;
import HealthNote.healthnote.community_dto.CommunitySaveDto;
import HealthNote.healthnote.community_dto.Community_ID_Boolean;
import HealthNote.healthnote.community_dto.EncodingImageDto;
import HealthNote.healthnote.domain.Community;
import HealthNote.healthnote.domain.CommunityLikeMember;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.repository.CommunityRepository;
import HealthNote.healthnote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    //게시글 저장(dto 데이터를 엔티티로 변환 후 저장)
    public Community_ID_Boolean contentSave(CommunitySaveDto csd) throws IOException {
        Community_ID_Boolean cib = new Community_ID_Boolean();
        Community community = new Community();
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();

        community.setGoodCount(0);
        community.setTitle(csd.getTitle());
        community.setDate(date);

        //커뮤니티 엔티티에 해당 member 엔티티 연관관계 맵핑 해주기
        Member findMember = memberRepository.findOne(csd.getId());
        if (findMember != null) {
            community.setMember(findMember);
        }else{
            cib.setSuccess(false);
            return cib;
        }
        //커뮤니티에 사진 저장하기
        if(csd.getCommunityPicture()!=null){
            MultipartFile picture = csd.getCommunityPicture();

            BufferedImage originalImage = ImageIO.read(picture.getInputStream());
            BufferedImage resizedImage = Thumbnails.of(originalImage)
                    .size(1080, 1080)
                    .asBufferedImage();

            String encodingPictureString = convertImageToBase64(resizedImage, "jpg");
            community.setCommunityPicture(encodingPictureString);
        }

        //커뮤니티 저장
        communityRepository.save(community);
        cib.setSuccess(true); cib.setId(community.getId());
        return cib;
    }




    //좋아요 더하기 기능   , 좋아요 누른 게시물에 좋아요테이블에 해당 유저 추가
    public int GoodCountAdd(Long id, Long memberId){
        Community findCommunity = communityRepository.findCommunity(id);
        //만약 이미 좋아요를 눌렀던 적이 있는 게시물인 경우 좋아요 추가 안됨. 그대로 반환
        List<CommunityLikeMember> communityLikeMember1 = findCommunity.getCommunityLikeMember();
        for (CommunityLikeMember communityLikeMember : communityLikeMember1) {
            if(communityLikeMember.getMemberId() == memberId){
                return findCommunity.getGoodCount();
            }
        }

        int goodCount = findCommunity.getGoodCount();
        findCommunity.setGoodCount(goodCount+1);

        //좋아요 누를 게시글에 좋아요 테이블 생성 추가
        CommunityLikeMember communityLikeMember = new CommunityLikeMember();
        communityLikeMember.setMemberId(memberId);
        communityLikeMember.setCommunity(findCommunity);

        communityRepository.saveLikeTable(communityLikeMember);
        return findCommunity.getGoodCount();
    }



    //해당 user의 게시판 이미지들 넘기기 기능
    public List<EncodingImageDto> UserCommunityImages(Long id){
        List<Community> communities = communityRepository.findMemberCommunity(id);
        //해당 유저가 게시글이 하나도 없을 경우.(게시글 이미지가 없을 경우)
        if(communities.isEmpty()){
            return null;
        }
        List<EncodingImageDto> communityImages = new ArrayList<>();

        for (Community community : communities) {
            String encodingImage = community.getCommunityPicture();
            if(encodingImage.isEmpty()){
                return null;
            }
            int goodCount = community.getGoodCount();
            EncodingImageDto image = new EncodingImageDto(encodingImage,goodCount);
            communityImages.add(image);
        }
        return communityImages;
    }


    //전체 게시판 게시글 10개씩 끊어서 데이터 넘겨주기 기능
    //넘겨야 할 데이터(유저 사진, 유저이름, 게시판 사진, 타이틀, 좋아요 수, 게시글 id(PK))
    public List<CommunityDto> CommunityAllByTen(int front, Long memberId){
        List<CommunityDto> communityDtos = communityRepository.findCommunityAllByTen(front);
        if(communityDtos == null){
            return null;
        }

        //유저 사진이 없을 경우 null값 넣어주기, 유저가 해당 게시물 좋아요 눌렀는지 안눌렀는지 확인.
        for (CommunityDto communityDto : communityDtos) {
            if(communityDto.getUserImage() == null){
                communityDto.setUserImage(null);
            }
            //유저가 해당 게시물 좋아요 눌렀는지 확인 후 값 넣어주기.
            Community community = communityRepository.findCommunity(communityDto.getCommunityId());
            List<CommunityLikeMember> communityLikeMembers = community.getCommunityLikeMember();

            for (CommunityLikeMember communityLikeMember : communityLikeMembers) {
                if(communityLikeMember.getMemberId() == memberId){
                    communityDto.setLikeState(true);
                    break;
                }
            }
        }
        return communityDtos;
    }




    // 이미지를 Base64 문자열로 변환하는 메서드
    public static String convertImageToBase64(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

}
