package eu.replin.helpdesk.controllers.functions;

import eu.replin.helpdesk.domain.User;
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
public class UsersController {

    @Autowired
    UserService us;

    @Autowired
    TicketService ts;

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
    public String logout(RedirectAttributes redirectAttributes, Model model) {
        model.addAttribute("logoutSuccess", true);
        return "redirect:/";
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess(RedirectAttributes redirectAttributes, Model model) {
        redirectAttributes.addFlashAttribute("loginSuccess", true);
        return "redirect:/myAccount";
    }

    @RequestMapping("/myAccount")
    public String getAccount(Model model) {
        User user = us.findLoggedUser();
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("ticketsQty", ts.getMyTickets().size());
        return "myAccount";
    }

}
