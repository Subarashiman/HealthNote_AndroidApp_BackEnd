package HealthNote.healthnote.admin_page;

import HealthNote.healthnote.admin_page.dto.AdminCommunityDto;
import HealthNote.healthnote.admin_page.dto.AdminLoginDto;
import HealthNote.healthnote.admin_page.dto.AdminMemberDto;
import HealthNote.healthnote.domain.Admin;
import HealthNote.healthnote.domain.Community;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.domain.WithdrawalMember;
import HealthNote.healthnote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j @Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    //관리자 로그인 페이지에서 로그인 요청
    //해당 계정이 있는지 확인후 return
    public boolean loginChecking(AdminLoginDto loginForm){
        String id = loginForm.getAdminId();
        String pass = loginForm.getAdminPass();
        List<Admin> admins = adminRepository.loginCheckingRepository(id);
        if(admins.isEmpty())return false;
        Admin findAdmin = admins.get(0);
        if(!findAdmin.getAdminPass().equals(pass))return false;
        return true;
    }

    //모든 맴버 리스트로 반환(관리자 페이지 전체 회원 조회)
    public List<AdminMemberDto> AllMemberList(){
        List<Member> allMember = adminRepository.findAllMember();
        List<AdminMemberDto> memberDtos = new ArrayList<>();
        for (Member member : allMember) {
            AdminMemberDto adminMemberDto = new AdminMemberDto();
            adminMemberDto.setPk(member.getId());
            adminMemberDto.setUserName(member.getUserName());
            adminMemberDto.setUserId(member.getUserId());
            adminMemberDto.setUserEmail(member.getEmail());
            adminMemberDto.setUserImage(member.getUserImage());
            memberDtos.add(adminMemberDto);
        }
        return memberDtos;
    }


    //해당 멤버 삭제
    public void deleteUser(Long id){
        Member findMember = memberRepository.findOne(id);
        WithdrawalMember withdrawalMember = new WithdrawalMember();
        withdrawalMember.setUserPk(findMember.getId());
        withdrawalMember.setUserName(findMember.getUserName());
        withdrawalMember.setUserId(findMember.getUserId());

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        withdrawalMember.setWithdrawalDate(formattedDateTime);

        memberRepository.saveWithdrawalMember(withdrawalMember);
        memberRepository.WithdrawalMember(id);
    }


    // 모든 게시글 리스트로 넘겨주기(관리자 페이지 게시글 전체 조회)
    public List<AdminCommunityDto> findAllCommunity(){
        List<AdminCommunityDto> communities = adminRepository.findAllCommunity();
        return communities;
    }

    //해당 게시글 삭제(관지자)
    public void deleteCommunity(Long id){
        adminRepository.deleteCommunity(id);
    }








}
