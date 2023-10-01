package HealthNote.healthnote.repository;

import HealthNote.healthnote.community_dto.CommunityDto;
import HealthNote.healthnote.domain.Community;
import HealthNote.healthnote.domain.CommunityLikeMember;
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


    //10개씩 끊어서 넘겨주기 최근 부터 이후 날짜 데이터로  ---> 전체게시판 테이블에서 게시판PK로 10개씩 끊어서 넘겨주기
    //				-----> 넘겨야 할 데이터(유저 사진, 유저이름, 게시판 사진, 타이틀, 좋아요 수, 게시글 id(PK))
    public List<CommunityDto> findCommunityAllByTen(int front){
        Long totalCommunityCount = (Long) em.createQuery("select count(c) from Community c").getSingleResult();
        if(totalCommunityCount == 0){
            return null;
        }
        int maxResults = Math.min(10, totalCommunityCount.intValue());

        return em.createQuery("select new HealthNote.healthnote.community_dto.CommunityDto(" +
                "m.userImage,m.userName,c.communityPicture,c.title,c.goodCount,c.id)"
                +" from Community c join c.member m order by c.id desc ")
                .setFirstResult(front)
                .setMaxResults(maxResults)
                .getResultList();
    }

    //좋아요 테이블에 해당 게시글에 좋아요한 회원 추가
    public void saveLikeTable(CommunityLikeMember communityLikeMember){
        em.persist(communityLikeMember);
    }





    //해당 회원의 게시글 전체 삭제, 해당 회원의 게시글 다 가져와서 게시글별 좋아요 테이블 다 삭제
    public void deleteCommunity(Long id){
        em.createQuery("delete from CommunityLikeMember cl where cl.MemberId=:id")
                .setParameter("id", id)
                .executeUpdate();

        em.createQuery("delete from Community c where c.member.id=:id")
                .setParameter("id",id)
                .executeUpdate();
    }


}
