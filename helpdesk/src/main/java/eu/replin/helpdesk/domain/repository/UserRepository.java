package eu.replin.helpdesk.domain.repository;

import eu.replin.helpdesk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository("user_repository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);


}
