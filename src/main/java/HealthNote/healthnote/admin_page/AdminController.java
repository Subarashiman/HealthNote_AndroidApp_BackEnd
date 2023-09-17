package HealthNote.healthnote.admin_page;

import HealthNote.healthnote.admin_page.dto.AdminCommunityDto;
import HealthNote.healthnote.admin_page.dto.AdminLoginDto;
import HealthNote.healthnote.admin_page.dto.AdminMemberDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;

    //main로그인창
    //로그인 창으로 이동하는데 만약 로그인 되어 있다면(세션이 있다면) 로그인창이 아닌 관리자페이지로 넘어간다.
    @GetMapping("/")
    public String MainLoginPage(HttpSession httpSession){
        Object session = httpSession.getAttribute("adminId");
        if(session == null){
            return "index";
        }else{
            return "redirect:/adminPage";
        }
    }

    //관리자 로그인 페이지에서 로그인 요청
    //해당 계정이 있는지 확인후 return
    @PostMapping("/adminPage")
    public String loginChecking(@ModelAttribute AdminLoginDto loginForm, Model model, HttpSession httpSession){
        boolean check = adminService.loginChecking(loginForm);
        if(check==false){
            String message = "계정이 일치하지 않습니다.";
            model.addAttribute("message",message);
            return "index";
        }else{
            httpSession.setAttribute("adminId",loginForm.getAdminId());
            return "redirect:/adminPage";
        }
    }
    //세션을 확인 후 메인 화면으로 보냄. 세션이 없으면 로그인 화면으로 보냄.
    @GetMapping("/adminPage")
    public String MainAdminPage(HttpSession httpSession){
        Object session = httpSession.getAttribute("adminId");
        if(session==null){
            return "redirect:/";
        }else{
            return "adminPage";
        }
    }

    //adminPage2로 보냄.(회원관리 페이지) ____모든 맴버를 다 뽑아서 넘겨줌.
    @GetMapping("/adminPage2")
    public String AdminPage2(HttpSession httpSession,Model model){
        if(loginChecking(httpSession)) {
            List<AdminMemberDto> members = adminService.AllMemberList();
            model.addAttribute("members",members);
            return "adminPage2";
        }
        return "redirect:/";
    }
    //유저 삭제 메서드
    @GetMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("Pk")Long id){
        adminService.deleteUser(id);
        return "redirect:/adminPage2";
    }

    //adminPage3로 보냄.(게시판 관리 페이지) _______모든 게시글 뽑아서 넘겨줌.
    @GetMapping("/adminPage3")
    public String AdminPage3(HttpSession httpSession,Model model){
        if(loginChecking(httpSession)){
            List<AdminCommunityDto> communities = adminService.findAllCommunity();
            model.addAttribute("communities",communities);
            return "adminPage3";
        }
        return "redirect:/";
    }
    //해당 게시글 삭제하기.
    @GetMapping("/admin/community/delete")
    public String deleteCommunity(@RequestParam("Pk")Long id){
        adminService.deleteCommunity(id);
        return "redirect:/adminPage3";
    }

    //adminPage4로 보냄.(운동 라이브러리 페이지)
    @GetMapping("/adminPage4")
    public String AdminPage4(HttpSession httpSession){
        if(loginChecking(httpSession)){
            return "adminPage4";
        }
        return "redirect:/";
    }
    //adminPage4_create 로 보냄. (운동 추가 페이지)
    @GetMapping("/adminPage4_create")
    public String AdminPage4_Create(HttpSession httpSession){
        if(loginChecking(httpSession)){
            return "adminPage4_create";
        }
        return "redirect:/";
    }
    //logout 기능. (세션 취소 시키고 로그인페이지로)
    @GetMapping("/logout")
    public String Logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }












    //login 되어있는지 아닌지 체크 메서드
    public boolean loginChecking(HttpSession httpSession){
        Object session = httpSession.getAttribute("adminId");
        if(session==null){
            return false;
        }else{
            return true;
        }
    }


}
