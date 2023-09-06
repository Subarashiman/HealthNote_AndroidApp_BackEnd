package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;


    //회원 저장
    public void save(Member member){
        em.persist(member);
    }



    // 유저 아이디로 특정 회원 조회
    public List<Member> findMember(String userId){
        return em.createQuery("select m from Member m where userId =:userId")
                    .setParameter("userId",userId)
                    .getResultList();

    }


}
