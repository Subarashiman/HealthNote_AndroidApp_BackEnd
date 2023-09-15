package HealthNote.healthnote.admin_page;

import HealthNote.healthnote.admin_page.dto.AdminLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    //main로그인창
    @GetMapping("/")
    public String MainLoginPage(){
        return "index";
    }




    //관리자 로그인 페이지에서 로그인 요청
    //해당 계정이 있는지 확인후 return
    @PostMapping("/admin_page")
    public String loginChecking(@ModelAttribute AdminLoginDto loginForm, Model model){
        boolean check = adminService.loginChecking(loginForm);
        if(check==false){
            String message = "계정이 일치하지 않습니다.";
            model.addAttribute("message",message);
            return "index_fail";
        }else{
            return "adminPage";
        }
    }





}
