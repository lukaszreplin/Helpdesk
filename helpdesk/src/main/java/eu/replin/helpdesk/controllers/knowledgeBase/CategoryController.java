package eu.replin.helpdesk.controllers.knowledgeBase;

import eu.replin.helpdesk.domain.Category;
import eu.replin.helpdesk.services.CategoryService;
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
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/categories")
    public String getCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/knowledgebase/categories";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("edit", false);
        model.addAttribute("category", new Category());
        return "/knowledgebase/categoryForm";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String afterAddCategory(RedirectAttributes redirectAttributes, Model model, @Valid Category category, BindingResult bindingResult) {
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
        redirectAttributes.addFlashAttribute("saveCategorySuccess", true);
        return "redirect:/categories";
    }

    @RequestMapping(value = "/editCategory")
    public String editCategory(@RequestParam("id") int id, Model model) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        model.addAttribute("edit", true);
        return "/knowledgebase/categoryForm";
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    public String saveCategoryFromEdit(RedirectAttributes redirectAttributes, Model model, @Valid Category category, BindingResult bindingResult) {
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
        redirectAttributes.addFlashAttribute("saveCategorySuccess", true);
        return "redirect:/categories";
    }

}
