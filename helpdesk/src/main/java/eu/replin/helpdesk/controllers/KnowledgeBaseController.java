package eu.replin.helpdesk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KnowledgeBaseController {

    @RequestMapping("/knowledgeBase")
    public String index() {
        return "kbase";
    }

}
