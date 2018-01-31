package eu.replin.helpdesk.services;

import eu.replin.helpdesk.domain.Ticket;
import eu.replin.helpdesk.domain.User;
import eu.replin.helpdesk.domain.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TicketService {

    @Autowired
    UserService userService;

    @Autowired
    TicketRepository ticketRepository;

    public void saveTicket(Ticket ticket) {
        User user = userService.findLoggedUser();
        ticket.setUser(user);
        ticket.setStatus(1);
        ticketRepository.save(ticket);
    }

    public ArrayList<Ticket> getMyTickets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ticketRepository.findByUser(userService.findUserByEmail(authentication.getName()));
    }

    public ArrayList<Ticket> getAllTickets() {
        return ticketRepository.findAllBySubjectIsNotNull();
    }

    public int selectCountOfAll() {
        return ticketRepository.selectCountOfAll();
    }

    public int selectCountOfOpen() {
        return ticketRepository.selectCountOfOpen();
    }

    public int selectCountOfClose() {
        return ticketRepository.selectCountOfClose();
    }

    public Ticket getTicket(int id) {
        return ticketRepository.findById(id);
    }

    public void changeStatus(int id, int status) {
        Ticket ticket = getTicket(id);
        switch (status) {
            case 1:
                ticket.setStatus(2);
                break;
            case 2:
                ticket.setStatus(1);
                break;
        }
        ticketRepository.save(ticket);
    }
}
