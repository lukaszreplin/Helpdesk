package eu.replin.helpdesk.controllers;

import eu.replin.helpdesk.domain.Ticket;
import eu.replin.helpdesk.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;


    @RequestMapping("/addTicket")
    public String addTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "/tickets/addTicket";
    }

    @RequestMapping(value = "/addTicket", method = RequestMethod.POST)
    public String afterAdd(Model model, @Valid Ticket ticket, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> lista = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                        lista.add(error.getDefaultMessage());
                    }
            );
            model.addAttribute("saveError", true);
            model.addAttribute("errorList", lista);
            System.out.println(lista.toString());
            return "tickets/addTicket";
        }
        ticketService.saveTicket(ticket);
        model.addAttribute("saveTicketSuccess", true);
        return "index";
    }

    @RequestMapping("/myTickets")
    public String getMyTickets(Model model) {
        List<Ticket> tickets = ticketService.getMyTickets();
        model.addAttribute("tickets", tickets);
        return "tickets/myTickets";
    }

    @RequestMapping("/manageTickets")
    public String getAllTickets(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "tickets/manageTickets";
    }

    @RequestMapping("/ticket")
    public String viewArticle(@RequestParam("id") int id, Model model) {
        Ticket ticket = ticketService.getTicket(id);
        model.addAttribute("ticket", ticket);
        return "/tickets/myTicket";
    }


}
