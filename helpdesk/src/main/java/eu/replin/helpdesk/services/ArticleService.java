package eu.replin.helpdesk.services;

import eu.replin.helpdesk.domain.Article;
import eu.replin.helpdesk.domain.Category;
import eu.replin.helpdesk.domain.User;
import eu.replin.helpdesk.domain.repository.ArticleRepository;
import eu.replin.helpdesk.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("articleService")
public class ArticleService {

    @Autowired
    UserService userService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ArrayList<Article> getAllArticle(String sorter) {
        ArrayList<Article> articles = null;
        switch (sorter) {
            case "0":
                articles = articleRepository.findAll();
                break;
            case "1":
                articles = articleRepository.findAllByOrderByIdDesc();
                break;
            case "2":
                articles = articleRepository.findAllByOrderByTitleAsc();
                break;
            case "3":
                articles = articleRepository.findAllByOrderByTitleDesc();
                break;
            default:
                articles = articleRepository.findAll();
                break;
        }
        return articles;
    }


    public Article getArticle(int id) {
        return articleRepository.findArticleById(id);
    }

    public ArrayList<Article> getSearchArticle(String text) {
        return articleRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(text, text);
    }

    public void saveArticle(Article article) {
        User user = userService.findLoggedUser();
        article.setUser(user);
        articleRepository.save(article);
    }

    public void updateArticle(Article article) {
        System.out.println("tytul: " + article.getTitle());
        System.out.println("tekst: " + article.getContent());
        System.out.println("id: " + article.getId());
        int a = articleRepository.updateArticle(article.getTitle(), article.getContent(), article.getCategory(), article.getId());
        System.out.println(a);
    }

    public void deleteArticle(int id) {
        articleRepository.deleteArticleById(id);
    }

    public int getCount() {
        return articleRepository.selectCountOfAll();
    }


}
