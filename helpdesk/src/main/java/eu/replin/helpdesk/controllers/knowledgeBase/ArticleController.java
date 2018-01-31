package eu.replin.helpdesk.controllers.knowledgeBase;


import eu.replin.helpdesk.domain.Article;
import eu.replin.helpdesk.domain.Category;
import eu.replin.helpdesk.services.ArticleService;
import eu.replin.helpdesk.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(names={"category", "article"})
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/article")
    public String viewArticle(@RequestParam("id") int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        return "/knowledgebase/article";
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.GET)
    public String addArticle(Model model) {
        model.addAttribute("edit", false);
        model.addAttribute("article", new Article());
        return "/knowledgebase/articleForm";
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public String afterAdd(@Valid Article article,  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> lista = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                        lista.add(error.getDefaultMessage());
                    }
            );
            model.addAttribute("saveError", true);
            model.addAttribute("edit", false);
            model.addAttribute("errorList", lista);
            System.out.println(lista.toString());
            return "knowledgebase/articleForm";
        }
        articleService.saveArticle(article);
        return "redirect:/knowledgeBase";
    }

    @RequestMapping(value = "/editArticle")
    public String editArticle(@RequestParam("id") int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        model.addAttribute("edit", true);
        return "/knowledgebase/articleForm";
    }

    @RequestMapping(value = "/editArticle", method = RequestMethod.POST)
    public String saveArticleFromEdit(RedirectAttributes redirectAttributes, Model model, @Valid @ModelAttribute("vehicle") Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || article.getContent().equals("") || article.getTitle().equals("")) {
            List<String> lista = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error ->{
                        lista.add(error.getDefaultMessage());
                    }
            );
            model.addAttribute("saveError", true);
            model.addAttribute("edit", true);
            model.addAttribute("errorList", lista);
            return "/knowledgebase/articleForm";
        }
        articleService.updateArticle(article);
        redirectAttributes.addFlashAttribute("saveArticleSuccess", true);
        return "redirect:/knowledgeBase";
    }

    @ModelAttribute("articleCategories")
    public List<Category> loadCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories;
    }


    @RequestMapping(value = "/deleteArticle")
    public String deleteArticle(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        articleService.deleteArticle(id);
        return "redirect:/knowledgeBase";
    }
}
