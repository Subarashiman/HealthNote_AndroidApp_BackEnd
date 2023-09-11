package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    // 유저PK로 특정 회원 조회
    public Member findOne(Long id){
        return em.find(Member.class,id);

    }

    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findByUserId(String userId) {
        try {
            TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.userId = :userId", Member.class)
                    .setParameter("userId", userId);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null; // 결과가 없는 경우 null 반환
        }
    }



    public boolean findByUserIdAndEmail(String userId, String email) {
        TypedQuery<String> query = em.createQuery("SELECT m.userPass FROM Member m WHERE m.userId = :userId AND m.email = :email", String.class);
        query.setParameter("userId", userId);
        query.setParameter("email", email);

        try {
            String userPass = query.getSingleResult();
            return userPass != null; // userPass가 존재하면 true, 그렇지 않으면 false 반환
        } catch (NoResultException ex) {
            return false; // 조회 결과가 없으면 false 반환
        }
    }


    public Member findByEmail(String email) {
        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class);
        query.setParameter("email", email);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null; // 이메일에 해당하는 회원이 없는 경우
        }
    }


}
