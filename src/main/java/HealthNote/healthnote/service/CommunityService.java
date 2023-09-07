package HealthNote.healthnote.service;

import HealthNote.healthnote.community_dto.CommunitySaveDto;
import HealthNote.healthnote.community_dto.Community_ID_Boolean;
import HealthNote.healthnote.domain.Community;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.repository.CommunityRepository;
import HealthNote.healthnote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
            community.setCommunityPicture(picture.getBytes());
        }

        //커뮤니티 저장
        communityRepository.save(community);
        cib.setSuccess(true); cib.setId(community.getId());
        return cib;
    }




    //좋아요 더하기 기능
    public int GoodCountAdd(Long id){
        Community findCommunity = communityRepository.findCommunity(id);
        int goodCount = findCommunity.getGoodCount();
        findCommunity.setGoodCount(goodCount+1);
        return findCommunity.getGoodCount();
    }


}
