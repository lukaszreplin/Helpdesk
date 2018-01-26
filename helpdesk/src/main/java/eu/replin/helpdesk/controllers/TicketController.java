package eu.replin.helpdesk.controllers;

import eu.replin.helpdesk.domain.Ticket;
import eu.replin.helpdesk.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;

    @RequestMapping("/myTickets")
    public String myTickets() {
        return "/tickets/mytickets";
    }

    @RequestMapping("/addTicket")
    public String addTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "/tickets/addticket";
    }


}
