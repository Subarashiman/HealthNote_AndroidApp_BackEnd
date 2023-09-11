package HealthNote.healthnote.service;

import HealthNote.healthnote.Member_dto.FormDto;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    // 회원가입
    public Boolean signUp(FormDto formDto) {
        // 회원 객체 생성 및 저장
        Member member = new Member();
        member.setUserId(formDto.getUserId());
        member.setUserPass(formDto.getUserPass());
        member.setUserName(formDto.getUserName());
        member.setEmail(formDto.getEmail());

        // userId 중복 체크
        validateDuplicateMember(member.getUserId());


        memberRepository.save(member);

        return true;
    }
    // 중복회원검사
    private void validateDuplicateMember(String userId) {
        Member findMembers = memberRepository.findByUserId(userId);
        if (findMembers != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 로그인
    public boolean signIn(FormDto formDto) {
        Member member = memberRepository.findByUserId(formDto.getUserId());

        // 회원이 존재하고 입력된 비밀번호와 회원의 비밀번호가 일치하는지 확인
        if (member != null && formDto.getUserPass().equals(member.getUserPass())) {
            return true;
        } else {
            return false;
        }
    }

    // 아이디 찾기
    // 파라미터로 받은 email을 DB에서 조회해서 존재하면 해당 id를 클라이언트에게 전달 없으면 메세지 전달
    public Member findUserId(FormDto formDto) {
        Member member = memberRepository.findByEmail(formDto.getEmail());
        if (member != null) {
            // 조회한 회원 정보에서 이메일을 추출하여 비교
            if (member.getEmail().equals(formDto.getEmail())) {
                // 이메일이 일치하는 경우 아이디를 반환
                return member;
            }
        }

        // 이메일이 일치하지 않거나 회원을 찾을 수 없는 경우 실패
        return member;
    }


    // 비번 찾기
    public boolean findUserPass(FormDto formDto) {
        // 파라미터로 받은 userId와 email을 DB에서 조회해서 존재하면 true를 보냄
        boolean result = memberRepository.findByUserIdAndEmail(formDto.getUserId(), formDto.getEmail());
        return result;
    }

    public void updateUserPass(FormDto formDto) {
        Member member = memberRepository.findByUserId(formDto.getUserId());

        if (member != null) {
            member.setUserPass(formDto.getUserPass());
            // 변경된 비밀번호를 데이터베이스에 저장
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }
}
