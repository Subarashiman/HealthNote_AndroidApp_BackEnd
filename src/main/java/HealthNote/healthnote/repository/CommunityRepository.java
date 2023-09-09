package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Community;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CommunityRepository {

    @PersistenceContext
    private EntityManager em;
    //게시판 저장
    public void save(Community community){
        em.persist(community);
    }

    //게시글 PK로 해당 게시글 찾기
    public Community findCommunity(Long id){
        Community community = em.find(Community.class, id);
        return community;
    }

    //member PK로 해당 유저가 작성한 게시글 다 찾기(날짜 순으로 오름차순 정렬)
    public List<Community> findMemberCommunity(Long id){
        return em.createQuery("select c from Community c where c.member.id=:id order by c.date asc")
                .setParameter("id",id)
                .getResultList();
    }

}
