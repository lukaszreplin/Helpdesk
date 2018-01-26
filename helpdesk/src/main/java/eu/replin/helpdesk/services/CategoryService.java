package eu.replin.helpdesk.services;

import eu.replin.helpdesk.domain.Article;
import eu.replin.helpdesk.domain.Category;
import eu.replin.helpdesk.domain.repository.ArticleRepository;
import eu.replin.helpdesk.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("categoryService")
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public ArrayList<Category> getAllCategories() {
        return new ArrayList<Category>(categoryRepository.findAllByNameIsNotNull());
    }

    public Category getCategory(int id) {
        return categoryRepository.findById(id);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        int a = categoryRepository.updateCategory(category.getName(), category.getId());
        System.out.println(a);
    }

}
