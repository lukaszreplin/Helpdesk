package eu.replin.helpdesk.domain.repository;


import eu.replin.helpdesk.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("ticket_repository")
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    public ArrayList<Ticket> findAllBySubjectIsNotNull();

    public ArrayList<Ticket> findByUser(String user);

    @Query("SELECT count(t.id) FROM Ticket t")
    int selectCountOfAll();

    @Query("SELECT count(t.id) FROM Ticket t WHERE t.status = '1'")
    int selectCountOfOpen();

    @Query("SELECT count(t.id) FROM Ticket t WHERE t.status = '2'")
    int selectCountOfClose();

    Ticket findById(int id);


}
