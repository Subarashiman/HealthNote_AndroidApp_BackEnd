package HealthNote.healthnote.service;

import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.myPage_dto.MyPageSaveDto;
import HealthNote.healthnote.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final MemberRepository memberRepository;

//    public Boolean saveImage(MyPageSaveDto mpsd) {
//        Member findMember = memberRepository.findOne(mpsd.getId());
//
//        // id로 맴버를 못찾으면 실패 반환
//        if (findMember == null) {
//            return false;
//        } else {
//
//        }
//    }
}
