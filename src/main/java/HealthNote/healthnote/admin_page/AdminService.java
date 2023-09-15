package HealthNote.healthnote.admin_page;

import HealthNote.healthnote.admin_page.dto.AdminLoginDto;
import HealthNote.healthnote.domain.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j @Transactional
public class AdminService {
    private final AdminRepository adminRepository;

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








}
