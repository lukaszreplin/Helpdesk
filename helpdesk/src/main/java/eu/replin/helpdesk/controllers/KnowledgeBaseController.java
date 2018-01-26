package eu.replin.helpdesk.controllers;

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
        model.addAttribute("articles", articles);
        return "/knowledgebase/kbase";
    }

    @RequestMapping("/categories")
    public String getCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/knowledgebase/categories";
    }

    @RequestMapping("/article")
    public String viewArticle(@RequestParam("id") int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        return "/knowledgebase/article";
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.GET)
    public String addArticle(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("edit", false);
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categories);
        return "/knowledgebase/articleForm";
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public String afterAdd(Model model,@Valid Article article, BindingResult bindingResult) {
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
        articleService.saveArticle(article, 1);
        model.addAttribute("saveArticleSuccess", true);
        return "index";
    }

    @RequestMapping(value = "/editArticle")
    public String editArticle(@RequestParam("id") int id, Model model) {
        Article article = articleService.getArticle(id);
        model.addAttribute("article", article);
        model.addAttribute("edit", true);
        return "/knowledgebase/articleForm";
    }

    @RequestMapping(value = "/editArticle", method = RequestMethod.POST)
    public String saveArticleFromEdit(Model model, @Valid Article article, BindingResult bindingResult) {
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
        System.out.println("id w kontrolerze: " + article.getId());
        articleService.updateArticle(article);
        model.addAttribute("saveArticleSuccess", true);
        return "index";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("edit", false);
        model.addAttribute("category", new Category());
        return "/knowledgebase/categoryForm";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String afterAddCategory(Model model, @Valid Category category, BindingResult bindingResult) {
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
            return "knowledgebase/categoryForm";
        }
        categoryService.saveCategory(category);
        model.addAttribute("saveCategorySuccess", true);
        return "index";
    }

    @RequestMapping(value = "/editCategory")
    public String editCategory(@RequestParam("id") int id, Model model) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        model.addAttribute("edit", true);
        return "/knowledgebase/categoryForm";
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    public String saveCategoryFromEdit(Model model, @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || category.getName().equals("")) {
            List<String> lista = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error ->{
                        lista.add(error.getDefaultMessage());
                    }
            );
            model.addAttribute("saveError", true);
            model.addAttribute("edit", true);
            model.addAttribute("errorList", lista);
            return "/knowledgebase/categoryForm";
        }
        System.out.println("id w kontrolerze: " + category.getId());
        categoryService.updateCategory(category);
        model.addAttribute("saveCategorySuccess", true);
        return "index";
    }

    @RequestMapping(value = "/deleteArticle")
    public String deleteArticle(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        articleService.deleteArticle(id);
        return "redirect:/knowledgeBase";
    }

}
