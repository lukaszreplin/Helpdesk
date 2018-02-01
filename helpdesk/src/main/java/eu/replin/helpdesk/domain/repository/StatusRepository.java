package eu.replin.helpdesk.domain.repository;

import eu.replin.helpdesk.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("status_repository")
public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findById(int id);
}
