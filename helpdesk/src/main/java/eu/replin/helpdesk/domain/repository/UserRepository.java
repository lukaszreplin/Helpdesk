package eu.replin.helpdesk.domain.repository;

import eu.replin.helpdesk.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void createUser(User user) {
        em.persist(user);

    }
}
