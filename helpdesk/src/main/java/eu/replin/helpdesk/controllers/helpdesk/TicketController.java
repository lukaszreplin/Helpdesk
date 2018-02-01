package eu.replin.helpdesk.controllers.helpdesk;

import eu.replin.helpdesk.domain.Comment;
import eu.replin.helpdesk.domain.Ticket;
import eu.replin.helpdesk.services.CommentService;
import eu.replin.helpdesk.services.EmailService;
import eu.replin.helpdesk.services.TicketService;
import eu.replin.helpdesk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @RequestMapping("/addTicket")
    public String addTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "/tickets/addTicket";
    }

    @RequestMapping(value = "/addTicket", method = RequestMethod.POST)
    public String afterAdd(RedirectAttributes redirectAttributes, Model model, @Valid Ticket ticket, BindingResult bindingResult) {
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
        redirectAttributes.addFlashAttribute("saveTicketSuccess", true);
        return "redirect:/myTickets";
    }

    @RequestMapping("/myTickets")
    public String getMyTickets(Model model) {
        List<Ticket> tickets = ticketService.getMyTickets();
        model.addAttribute("tickets", tickets);
        return "tickets/myTickets";
    }

    @RequestMapping("/manageTickets")
    public String getAllTickets(@RequestParam(value = "sorter", required = false, defaultValue = "0") String sorter, Model model) {
        List<Ticket> tickets = ticketService.getAllTickets(sorter);
        model.addAttribute("activeSorter", sorter);
        model.addAttribute("tickets", tickets);
        return "tickets/manageTickets";
    }

    @RequestMapping("/ticket")
    public String viewMyTicket(@RequestParam("id") int id, Model model) {
        Ticket ticket = ticketService.getTicket(id);
        ArrayList<Comment> comments = commentService.getCommentsForTicket(ticket);
        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);
        model.addAttribute("commentAdd", new Comment());
        return "/tickets/myTicket";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public String addComment(@RequestParam("ticketId") int id, @RequestParam("content") String content, Model model) {
        Ticket ticket = ticketService.getTicket(id);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(userService.findLoggedUser());
        comment.setTicket(ticket);
        commentService.saveComment(comment);
        ArrayList<Comment> comments = commentService.getCommentsForTicket(ticket);
        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);
        return "redirect:/ticket?id=" + id;
    }

    @RequestMapping("/ticketDetails")
    public String viewTicketDetails(@RequestParam("id") int id, Model model) {
        Ticket ticket = ticketService.getTicket(id);
        ArrayList<Comment> comments = commentService.getCommentsForTicket(ticket);
        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);
        model.addAttribute("commentAdd", new Comment());
        return "/tickets/ticketDetails";
    }

    @RequestMapping(value = "/ticketDetails", method = RequestMethod.POST)
    public String addCommentAdmin(@RequestParam("ticketId") int id, @RequestParam("content") String content, Model model) {
        Ticket ticket = ticketService.getTicket(id);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(userService.findLoggedUser());
        comment.setTicket(ticket);
        commentService.saveComment(comment);
        ArrayList<Comment> comments = commentService.getCommentsForTicket(ticket);
        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);
        return "redirect:/ticketDetails?id=" + id;
    }

    @RequestMapping(value = "/changeTicketStatusByAdmin", method = RequestMethod.POST)
    public String changeTicketStatusByAdmin(@RequestParam("ticketId") int id,
                                            @RequestParam("currentStatus") String status,
                                            @RequestParam("sendMail") int sendMail, Model model) {
        ticketService.changeStatus(id, Integer.parseInt(status), sendMail);
//        emailService.sendSimpleMessage("lukasz@replin.eu", "Test", "" +
//                "<html><body><h1>Status <b>Twojego</b> zgłoszenia został zmieniony!</h1></body></html>");
        return "redirect:/ticketDetails?id=" + id;
    }

    @RequestMapping(value = "/changeTicketStatusByUser", method = RequestMethod.POST)
    public String changeTicketStatusByUser(@RequestParam("ticketId") int id,
                                            @RequestParam("currentStatus") String status, Model model) {
        ticketService.changeStatus(id, Integer.parseInt(status), 1);
//        emailService.sendSimpleMessage("lukasz@replin.eu", "Test", "" +
//                "<html><body><h1>Status <b>Twojego</b> zgłoszenia został zmieniony!</h1></body></html>");
        return "redirect:/ticket?id=" + id;
    }


}
