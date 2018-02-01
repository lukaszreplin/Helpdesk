package eu.replin.helpdesk.services;

import eu.replin.helpdesk.domain.Ticket;
import eu.replin.helpdesk.domain.User;
import eu.replin.helpdesk.domain.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    StatusService statusService;

    public void saveTicket(Ticket ticket) {
        User user = userService.findLoggedUser();
        ticket.setUser(user);
        ticket.setStatus(statusService.getStatus(1));
        ticketRepository.save(ticket);
    }

    public ArrayList<Ticket> getMyTickets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ticketRepository.findByUser(userService.findUserByEmail(authentication.getName()));
    }

    public ArrayList<Ticket> getAllTickets() {
        return ticketRepository.findAllBySubjectIsNotNull();
    }

    public ArrayList<Ticket> getAllTickets(String sorter) {
        ArrayList<Ticket> tickets = null;
        switch (sorter) {
            case "0":
                tickets = ticketRepository.findAllByOrderByIdAsc();
                break;
            case "1":
                tickets = ticketRepository.findAllByOrderByIdDesc();
                break;
            default:
                return null;
        }
        return tickets;
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

    public void changeStatus(int id, int status, int sendMail) {
        Context context = new Context();
        Ticket ticket = getTicket(id);
        User user = ticket.getUser();
        String desc1 = "Szanowny Panie/Pani " + user.getFirstname() + " " + user.getLastname() +", ";
        StringBuilder sb = new StringBuilder("Status Twojego zgłoszenia o id -- " + ticket.getId() + " -- został zmieniony na: ");
        switch (status) {
            case 1:
                ticket.setStatus(statusService.getStatus(2));
                sb.append("Zamknięte. ");
                break;
            case 2:
                ticket.setStatus(statusService.getStatus(1));
                sb.append("Otwarte. ");
                break;
        }
        ticketRepository.save(ticket);
        if (sendMail == 1) {
            context.setVariable("header", "System zgłoszeń HelpDesk");
            context.setVariable("title", "Zmiana statusu zgłoszenia");
            context.setVariable("description1", desc1);
            context.setVariable("description2", sb.toString());
            context.setVariable("description3", "Pozdrawiamy, Zespół HelpDesk.");
            context.setVariable("footer", "Platformy Programowania 2018");
            emailService.sendSimpleMessage(user.getEmail(), "HelpDesk - Powiadomienie", "mailChangeStatus", context);
        }
        }
}
