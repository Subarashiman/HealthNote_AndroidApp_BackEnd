package HealthNote.healthnote.service;

import HealthNote.healthnote.Member_dto.*;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.domain.WithdrawalMember;
import HealthNote.healthnote.repository.CommunityRepository;
import HealthNote.healthnote.repository.ExerciseRepository;
import HealthNote.healthnote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final ExerciseRepository exerciseRepository;
    private final CommunityRepository communityRepository;


    // 회원가입
    public SignUpDto signUp(FormDto formDto) {
        // 회원 객체 생성 및 저장
        Member member = new Member();
        member.setUserId(formDto.getUserId());
        member.setUserPass(formDto.getUserPass());
        member.setUserName(formDto.getUserName());
        member.setEmail(formDto.getEmail());
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        member.setJoinDate(formattedDateTime);

        int code = 200;
        // 성공 = 200, ID중복 = 400, EMAIL 중복 = 300, ID & EMAIL 중복 = 500

        // userId 중복 체크
        if (!validateDuplicateUserId(member.getUserId())) {
            code += 200;
        }
        // email 중복 체크
        if (!validateDuplicateEmail(member.getEmail())) {
            code += 100;
        }

        if (code == 200) {
            memberRepository.save(member);
        }

        return new SignUpDto(code);
    }

    // 중복Email검사
    private boolean validateDuplicateEmail(String email) {
        Member find = memberRepository.findByEmail(email);
        return find == null;
    }

    // 중복ID검사
    private boolean validateDuplicateUserId(String userId) {
        Member findMembers = memberRepository.findByUserId(userId);
        return findMembers == null;
    }

    // 로그인
    public SignInDto signIn(FormDto formDto) {
        Member member = memberRepository.findByUserId(formDto.getUserId());

        // 회원이 존재하고 입력된 비밀번호와 회원의 비밀번호가 일치하는지 확인
        if (member != null && formDto.getUserPass().equals(member.getUserPass())) {
            // memberId도 같이 반환해 주기
            return new SignInDto(member.getId(), 200);
        } else {
            return null;
        }
    }

    // 아이디 찾기
    // 파라미터로 받은 email을 DB에서 조회해서 존재하면 해당 id를 클라이언트에게 전달 없으면 메세지 전달
    public Member findUserId(FormDto formDto) {
        Member member = memberRepository.findByEmail(formDto.getEmail());
        if (member != null && member.getUserId().equals(formDto.getUserId())) {
            // 조회한 회원 정보에서 아이디를 추출하여 비교
            return member;
        }

        // 이메일이 일치하지 않거나 회원을 찾을 수 없는 경우 실패
        return null;
    }


    // 비번 찾기
    public FindPwDto findUserPass(FormDto formDto) {
        Member result = memberRepository.findByUserIdAndEmail(formDto.getUserId(), formDto.getEmail());
        if (result != null) {
            return new FindPwDto(result.getId(), 200);
        } else {
            return new FindPwDto(null, 400);
        }
    }


    public UpdateUserPassDto updateUserPass(FormDto formDto) {
        Member member = memberRepository.findOne(formDto.getId());

        if (member != null) {
            member.setUserPass(formDto.getUserPass());
            // 변경된 비밀번호를 데이터베이스에 저장
            memberRepository.save(member);
            return new UpdateUserPassDto(200);
        } else {
            return new UpdateUserPassDto(400);
        }
    }



    //회원탈퇴 기능 서비스
    public boolean WithdrawalMemberService(Long id){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        Member findMember = memberRepository.findOne(id);
        WithdrawalMember withdrawalMember = new WithdrawalMember();
        withdrawalMember.setWithdrawalDate(formattedDateTime);
        withdrawalMember.setUserId(findMember.getUserId());
        withdrawalMember.setUserPk(id);
        withdrawalMember.setUserName(findMember.getUserName());
        memberRepository.saveWithdrawalMember(withdrawalMember);
        exerciseRepository.deleteExerciseLog(id);
        communityRepository.deleteCommunity(id);
        memberRepository.WithdrawalMember(id);
        return true;
    }
}
