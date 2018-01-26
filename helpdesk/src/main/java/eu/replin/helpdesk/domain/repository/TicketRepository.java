package eu.replin.helpdesk.domain.repository;


import eu.replin.helpdesk.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ticket_repository")
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    public List<Ticket> findAllBySubjectIsNotNull();



}
