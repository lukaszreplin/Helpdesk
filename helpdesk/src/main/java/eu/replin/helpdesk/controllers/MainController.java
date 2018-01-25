package eu.replin.helpdesk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
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

    @RequestMapping("/register")
    public String register() {
        return "register";
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
