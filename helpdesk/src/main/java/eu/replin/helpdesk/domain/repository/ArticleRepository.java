package eu.replin.helpdesk.domain.repository;

import eu.replin.helpdesk.domain.Article;
import eu.replin.helpdesk.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository("article_repository")
public interface ArticleRepository extends JpaRepository<Article, String> {

    ArrayList<Article> findAll();

    ArrayList<Article> findAllByOrderByTitleAsc();

    ArrayList<Article> findAllByOrderByTitleDesc();

    ArrayList<Article> findAllByOrderByIdDesc();

    ArrayList<Article> findAllByCategoryId(int id);

    ArrayList<Article> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String text1, String text2);

    Article findArticleById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Article a SET a.title = :title, a.content = :content, a.category = :category WHERE a.id = :article_id")
    int updateArticle(@Param("title") String title,
                      @Param("content") String content,
                      @Param("category") Category category,
                      @Param("article_id") int articleId);

    @Transactional
    int deleteArticleById(int id);

    @Query("SELECT count(a.id) FROM Article a")
    int selectCountOfAll();



}
