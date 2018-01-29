package eu.replin.helpdesk.domain.repository;

import eu.replin.helpdesk.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    ArrayList<Category> findAll();

    Category findByName(String name);

    Category findById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Category c SET c.name = :name WHERE c.id = :category_id")
    int updateCategory(@Param("name") String name,
                      @Param("category_id") int categoryId);
}
