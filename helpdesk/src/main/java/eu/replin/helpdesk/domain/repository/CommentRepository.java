package eu.replin.helpdesk.domain.repository;

import eu.replin.helpdesk.domain.Category;
import eu.replin.helpdesk.domain.Comment;
import eu.replin.helpdesk.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    ArrayList<Comment> getAllByTicketOrderByCreatedDateAsc(Ticket ticket);

}
