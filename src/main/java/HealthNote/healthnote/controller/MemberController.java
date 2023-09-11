package HealthNote.healthnote.controller;

import HealthNote.healthnote.Member_dto.FormDto;
import HealthNote.healthnote.Member_dto.MemberProcessResult;
import HealthNote.healthnote.domain.Member;
import HealthNote.healthnote.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/members/sign-up")
    public MemberProcessResult signUp(@RequestBody FormDto formDto){

        Boolean result = memberService.signUp(formDto);
        if (result)
        return new MemberProcessResult(true, 200, "회원가입성공");
        else
            return new MemberProcessResult(false, 400, "회원가입실패");
    }

    // 로그인
    @GetMapping("/api/members/sign-in")
    public MemberProcessResult login(@RequestBody FormDto formDto) {
        boolean result = memberService.signIn(formDto);
        if (result)
        return new MemberProcessResult(true, 200, "로그인 성공");
        else
            return new MemberProcessResult(false, 400, "로그인 실패");
    }

    // 아이디 찾기

    @GetMapping("/api/members/find-username")
    public MemberProcessResult findUserId(@RequestBody FormDto formDto) {
        Member result = memberService.findUserId(formDto);
        if (result != null) {
            return new MemberProcessResult(result.getUserId(), 200);
        }
        else {
            return new MemberProcessResult(false, 400);
        }
    }
    // 비밀번호 찾기
    @GetMapping("/api/members/find-userPass")
    public MemberProcessResult findUserPass(@RequestBody FormDto formDto) {
        boolean result = memberService.findUserPass(formDto);
        if (result)
            return new MemberProcessResult(true, 200);
        else
            return new MemberProcessResult(false, 400);
    }

    // 비밀번호 재설정
    @PostMapping("/api/members/update-userPass")
    public MemberProcessResult updateUserPass(@RequestBody FormDto formDto) {
        memberService.updateUserPass(formDto); // 이것이 실패했을때
        // throw new IllegalArgumentException("존재하지 않는 회원입니다."); 실패했을때는 front에서 처리하는 것인가??
        return new MemberProcessResult(true, 200, "변경 성공");
    }

}
