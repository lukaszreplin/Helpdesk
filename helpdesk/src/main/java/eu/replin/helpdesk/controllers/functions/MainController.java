package eu.replin.helpdesk.controllers.functions;

import eu.replin.helpdesk.domain.User;
import eu.replin.helpdesk.services.ArticleService;
import eu.replin.helpdesk.services.TicketService;
import eu.replin.helpdesk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ArticleService as;

    @Autowired
    TicketService ts;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("articleCounter", as.getCount());
        model.addAttribute("ticketsAll", ts.selectCountOfAll());
        model.addAttribute("ticketsClosed", ts.selectCountOfClose());
        model.addAttribute("ticketsOpen", ts.selectCountOfOpen());
        return "index";
    }



//    @RequestMapping("/error")
//    public String render404(Model model) {
//        return "kbase";
//    }


}
