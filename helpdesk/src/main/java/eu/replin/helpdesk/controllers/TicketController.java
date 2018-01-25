package eu.replin.helpdesk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketController {

    @RequestMapping("/myTickets")
    public String myTickets() {
        return "/tickets/mytickets";
    }

    @RequestMapping("/addTicket")
    public String addTicket() {
        return "/tickets/addticket";
    }

}
