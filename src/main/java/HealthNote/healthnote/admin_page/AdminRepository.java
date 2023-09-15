package HealthNote.healthnote.admin_page;

import HealthNote.healthnote.domain.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class AdminRepository {
    @PersistenceContext
    private EntityManager em;

    //관리자 로그인 페이지에서 로그인 요청
    //해당 계정이 있는지 확인후 return
    public List<Admin> loginCheckingRepository(String id){
        return em.createQuery("select a from Admin a where a.adminId =:id")
                .setParameter("id",id)
                .getResultList();
    }


}
