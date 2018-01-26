package eu.replin.helpdesk.services;

import eu.replin.helpdesk.domain.Article;
import eu.replin.helpdesk.domain.Category;
import eu.replin.helpdesk.domain.repository.ArticleRepository;
import eu.replin.helpdesk.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("articleService")
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ArrayList<Article> getAllArticle() {
        return new ArrayList<Article>(articleRepository.findAllByTitleIsNotNull());
    }

    public Article getArticle(int id) {
        return articleRepository.findArticleById(id);
    }

    public void saveArticle(Article article, int id) {
        System.out.println("save article start");
        Category category = categoryRepository.findById(1);
        System.out.println("pobrano kategorię: " + category.getName());
        article.setCategory(category);
        System.out.println("zapisano...");
        articleRepository.save(article);
    }

    public void updateArticle(Article article) {
        System.out.println("tytul: " + article.getTitle());
        System.out.println("tekst: " + article.getContent());
        System.out.println("id: " + article.getId());
        int a = articleRepository.updateArticle(article.getTitle(), article.getContent(), article.getId());
        System.out.println(a);
    }

    public void deleteArticle(int id) {
        articleRepository.deleteArticleById(id);
    }
}
