package HealthNote.healthnote.admin_page;

import HealthNote.healthnote.admin_page.dto.AdminCommunityDto;
import HealthNote.healthnote.admin_page.dto.AdminCommunityListDto;
import HealthNote.healthnote.domain.*;
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

    //모든 유저 찾아서 리스트로 반환
    public List<Member> findAllMember(){
        return em.createQuery("select m from Member m")
                .getResultList();
    }


    //모든 게시글 찾아서 리스트로 반환
    public List<AdminCommunityDto> findAllCommunity(){
        return em.createQuery("select new HealthNote.healthnote.admin_page.dto.AdminCommunityDto(c.id,c.date,c.communityPicture,c.title,c.goodCount,m.userId) " +
                        "from Community c join Member m on c.member.id=m.id")
                .getResultList();
    }

    //해당 게시글 삭제
    public void deleteCommunity(Long id){
        em.createQuery("delete from Community c where c.id=:id")
                .setParameter("id",id)
                .executeUpdate();
    }

    //탈퇴한 모든 회원 가져오기
    public List<WithdrawalMember> findAllWithdrawMember(){
        return em.createQuery("select w from WithdrawalMember w")
                .getResultList();
    }

    //모든 게시글 리스트 최신순 + 작성회원 아이디
    public List<AdminCommunityListDto> findAllCommunityList(){
        return em.createQuery("select new HealthNote.healthnote.admin_page.dto.AdminCommunityListDto(c.id,c.date,c.title,c.goodCount,m.userId)" +
                "from Community c join Member m on c.member.id=m.id")
                .getResultList();
    }

    //사용자 수 조회
    public Long findUsingUserCount(){
        return (Long)em.createQuery("select count(m) from Member m")
                .getSingleResult();
    }
    //탈퇴자 수 조회
    public Long findOutUserCount(){
        return (Long)em.createQuery("select count(w) from WithdrawalMember w")
                .getSingleResult();
    }
    //게시글 수 조회
    public Long findCommunityCount(){
        return (Long)em.createQuery("select count(c) from Community c")
                .getSingleResult();
    }
    //운동 개수 조회
    public Long findExerciseCount(){
        return (Long)em.createQuery("select count(l) from Library l")
                .getSingleResult();
    }

    //라이브러리 운동 목록 다 가져오기. ____ 운동번호가 작은 순으로 정렬
    public List<Library> findAllLibrary(){
        return em.createQuery("select l from Library l order by l.exerciseNumber asc ")
                .getResultList();
    }
    //라이브러리 특정 운동 하나 가져오기
    public Library findOneLibrary(int id){
        return em.find(Library.class,id);
    }
    //라이브러리 특정 운동 삭제
    public void deleteLibrary(int id){
        em.createQuery("delete from Library l where l.exerciseNumber=:id")
                .setParameter("id",id)
                .executeUpdate();
    }
    //라이브러리 운동 추가
    public void addLibrary(Library library){
        em.persist(library);
    }



}
