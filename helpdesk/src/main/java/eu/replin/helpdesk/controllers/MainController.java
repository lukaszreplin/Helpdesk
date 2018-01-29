package eu.replin.helpdesk.controllers;

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
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    UserService us;

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

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String afterRegister(Model model, @Valid User user, BindingResult bindingResult) {
        User userExists = us.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Użytkownik z takim adresem email jest już w bazie");
        }
        if (bindingResult.hasErrors()) {
            List<String> lista = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                        lista.add(error.getDefaultMessage());
                    }
            );
            model.addAttribute("registerError", true);
            model.addAttribute("errorList", lista);
            System.out.println(lista.toString());
            return "register";
        }
        us.saveUser(user);
        model.addAttribute("registerSuccess", true);
        return "index";
    }

    @RequestMapping("/logoutSuccess")
    public String logout(Model model) {
        model.addAttribute("logoutSuccess", true);
        return "index";
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess(Model model) {
        model.addAttribute("loginSuccess", true);
        return "index";
    }

//    @RequestMapping("/error")
//    public String render404(Model model) {
//        return "kbase";
//    }


}
