package HealthNote.healthnote.repository;

import HealthNote.healthnote.domain.Community;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CommunityRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Community community){
        em.persist(community);
    }

}
