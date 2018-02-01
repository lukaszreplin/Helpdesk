package eu.replin.helpdesk.domain.repository;


import eu.replin.helpdesk.domain.Ticket;
import eu.replin.helpdesk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Repository("ticket_repository")
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    public ArrayList<Ticket> findAllBySubjectIsNotNull();

    public ArrayList<Ticket> findAllByOrderByIdDesc();

    public ArrayList<Ticket> findAllByOrderByIdAsc();

    public ArrayList<Ticket> findByUser(User user);

    @Query("SELECT count(t.id) FROM Ticket t")
    int selectCountOfAll();

    @Query("SELECT count(t.id) FROM Ticket t WHERE t.status = '1'")
    int selectCountOfOpen();

    @Query("SELECT count(t.id) FROM Ticket t WHERE t.status = '2'")
    int selectCountOfClose();

    Ticket findById(int id);



}
