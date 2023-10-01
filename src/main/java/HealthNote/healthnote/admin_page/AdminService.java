package HealthNote.healthnote.admin_page;

import HealthNote.healthnote.admin_page.dto.*;
import HealthNote.healthnote.domain.Admin;
import HealthNote.healthnote.domain.Library;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.domain.WithdrawalMember;
import HealthNote.healthnote.repository.CommunityRepository;
import HealthNote.healthnote.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j @Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;

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

        communityRepository.deleteCommunity(id);
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


    //가입자 전부 가져오기(이용회원 + 가입날짜)
    public List<usingUserDto> findAllJoinUser(){
        List<Member> members = adminRepository.findAllMember();
        List<Member> sortedMembers = members.stream()
                .sorted(Comparator.comparing(Member::getJoinDate).reversed())
                .collect(Collectors.toList());
        List<usingUserDto> users = new ArrayList<>();

        for (Member member : sortedMembers) {
            usingUserDto usingUserDto = new usingUserDto();
            usingUserDto.setPk(member.getId());
            usingUserDto.setUserId(member.getUserId());
            usingUserDto.setUserName(member.getUserName());
            usingUserDto.setUserEmail(member.getEmail());
            usingUserDto.setJoinDate(member.getJoinDate());
            users.add(usingUserDto);
        }
        return users;
    }


    //탈퇴자 전부 가져오기___(관리자페이지 탈퇴자 리스트)
    public List<OutMemberDto> findWithdrawalMember(){
        List<WithdrawalMember> outMembers = adminRepository.findAllWithdrawMember();
        List<WithdrawalMember> sortedOutMembers = outMembers.stream()
                .sorted(Comparator.comparing(WithdrawalMember::getWithdrawalDate).reversed())
                .collect(Collectors.toList());
        List<OutMemberDto>outMemberDtos = new ArrayList<>();

        for (WithdrawalMember outMember : sortedOutMembers) {
            OutMemberDto outMemberDto = new OutMemberDto();
            outMemberDto.setPk(outMember.getId());
            outMemberDto.setUserPk(outMember.getUserPk());
            outMemberDto.setUserId(outMember.getUserId());
            outMemberDto.setUserName(outMember.getUserName());
            outMemberDto.setWithdrawalDate(outMember.getWithdrawalDate());
            outMemberDtos.add(outMemberDto);
        }
        return outMemberDtos;
    }



    //모든 커뮤니티글 최근순으로 정렬해서 보내기____관리자페이지 게시글 확인
    public List<AdminCommunityListDto> findAllCommunityList(){
        List<AdminCommunityListDto> communities = adminRepository.findAllCommunityList();
        List<AdminCommunityListDto> sortedCommunityList = communities.stream()
                .sorted(Comparator.comparing(AdminCommunityListDto::getWriteDate).reversed())
                .collect(Collectors.toList());
        return sortedCommunityList;
    }


    //메인 화면에 유저수, 게시글 수, 운동 개수
    public MainPageDto findAllDataCount(){
        int usingUserCount = adminRepository.findUsingUserCount().intValue();
        int outUserCount = adminRepository.findOutUserCount().intValue();
        int communityCount = adminRepository.findCommunityCount().intValue();
        int exerciseCount = adminRepository.findExerciseCount().intValue();

        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setTotalUserCount(usingUserCount+outUserCount);
        mainPageDto.setUsingUserCount(usingUserCount);
        mainPageDto.setOutUserCount(outUserCount);
        mainPageDto.setCommunityCount(communityCount);
        mainPageDto.setExerciseCount(exerciseCount);

        return mainPageDto;
    }


    //운동 목록 다 가져와서 넘겨주기______대시보드(운동 확인)
    public List<AdminLibraryDto>findAllLibrary(){
        List<Library> allLibrary = adminRepository.findAllLibrary();
        List<AdminLibraryDto>adminLibrarys = new ArrayList<>();

        for (Library library : allLibrary) {
            AdminLibraryDto adminLibraryDto = new AdminLibraryDto();
            adminLibraryDto.setExerciseNumber(library.getExerciseNumber());
            adminLibraryDto.setExerciseExplanation(library.getExerciseExplanation());
            adminLibraryDto.setExerciseName(library.getExerciseName());
            adminLibraryDto.setExerciseUrl(library.getExerciseUrl());
            adminLibraryDto.setExerciseCategory(findCategory(library.getExerciseNumber()));
            adminLibrarys.add(adminLibraryDto);
        }
        return adminLibrarys;
    }


    //라이브러리 운동 수정 _____ (adminPage4 운동수정)
    public void correctionsLibrary(RequestLibraryDto requestLibraryDto){
        Library findLibrary = adminRepository.findOneLibrary(requestLibraryDto.getExerciseNumber());
        //운동 정보 변경
        findLibrary.setExerciseName(requestLibraryDto.getExerciseName());
        findLibrary.setExerciseUrl(requestLibraryDto.getExerciseUrl());
        findLibrary.setExerciseExplanation(requestLibraryDto.getExerciseExplanation());

    }

    //라이브러리 운동 삭제_____-(adminPage4 운동삭제)
    public void deleteLibrary(int id){
        adminRepository.deleteLibrary(id);
    }
    //라이브러리 운동 추가_____(adminPage4 운동추가)
    public void addLibrary(RequestLibraryDto requestLibraryDto){
        Library library = new Library();
        library.setExerciseNumber(requestLibraryDto.getExerciseNumber());
        library.setExerciseName(requestLibraryDto.getExerciseName());
        library.setExerciseExplanation(requestLibraryDto.getExerciseExplanation());
        library.setExerciseUrl(requestLibraryDto.getExerciseUrl());

        adminRepository.addLibrary(library);

    }










    //운동 넘버에 따른 카테고리 분류 메소드
    public String findCategory(int exerciseNumber){
        if (100 <= exerciseNumber && exerciseNumber <= 199) {
            return "하체";
        } else if (200 <= exerciseNumber && exerciseNumber <= 299) {
            return "가슴";
        } else if (300 <= exerciseNumber && exerciseNumber <= 399) {
            return "등";
        } else if (400 <= exerciseNumber && exerciseNumber <= 499) {
            return "어깨";
        } else if (500 <= exerciseNumber && exerciseNumber <= 599) {
            return "팔";
        } else {
            return "분류할 수 없음";
        }
    }

}
