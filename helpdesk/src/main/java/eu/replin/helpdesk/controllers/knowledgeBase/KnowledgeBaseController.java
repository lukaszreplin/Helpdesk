package eu.replin.helpdesk.controllers.knowledgeBase;

import eu.replin.helpdesk.domain.Article;
import eu.replin.helpdesk.services.ArticleService;
import eu.replin.helpdesk.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
        return "/knowledgebase/knowledgeBase";
    }

    @RequestMapping(value = "/knowledgeBase", method = RequestMethod.POST)
    public String getArticlesFromSearch(@RequestParam("search") String text, Model model) {
        ArrayList<Article> articles = articleService.getSearchArticle(text);
        model.addAttribute("articles", articles);
        return "/knowledgebase/searchResults";
    }




}
