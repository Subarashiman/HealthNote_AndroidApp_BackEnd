package HealthNote.healthnote.service;

import HealthNote.healthnote.member_dto.*;
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
        // 성공 = 200, ID중복 = 300, EMAIL 중복 = 400, ID & EMAIL 중복 = 500

        // userId 중복 체크
        if (!validateDuplicateUserId(member.getUserId())) {
            code += 100;
        }
        // email 중복 체크
        if (!validateDuplicateEmail(member.getEmail())) {
            code += 200;
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
        // formDto로 받은 이메일을 DB에서 검색한 후 해당 객체를 반환
        return memberRepository.findByEmail(formDto.getEmail());
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

    // 소개문구 저장
    public IntroductionDto setUserIntroduction(Long id, String introduction) {
        Member member = memberRepository.findOne(id);
        if (member != null) {
            member.setIntroduction(introduction);
            memberRepository.save(member);
            return new IntroductionDto(200);
        } else {
            return new IntroductionDto(400);
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
