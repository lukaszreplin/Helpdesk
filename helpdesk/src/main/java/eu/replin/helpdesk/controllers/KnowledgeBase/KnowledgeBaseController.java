package eu.replin.helpdesk.controllers.KnowledgeBase;

import eu.replin.helpdesk.domain.Article;
import eu.replin.helpdesk.domain.Category;
import eu.replin.helpdesk.domain.User;
import eu.replin.helpdesk.domain.repository.ArticleRepository;
import eu.replin.helpdesk.services.ArticleService;
import eu.replin.helpdesk.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class KnowledgeBaseController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/knowledgeBase")
    public String getArticles(Model model) {
        List<Article> articles = articleService.getAllArticle();
        System.out.println(articles.toString());
        model.addAttribute("articles", articles);
        return "/knowledgebase/kbase";
    }




}
