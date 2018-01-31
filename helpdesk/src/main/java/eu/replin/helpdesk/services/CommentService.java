package eu.replin.helpdesk.services;

import eu.replin.helpdesk.domain.Comment;
import eu.replin.helpdesk.domain.Ticket;
import eu.replin.helpdesk.domain.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public ArrayList<Comment> getCommentsForTicket(Ticket ticket) {
        return commentRepository.getAllByTicketOrderByCreatedDateAsc(ticket);
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

}
