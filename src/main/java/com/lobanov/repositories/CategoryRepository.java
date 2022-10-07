package com.lobanov.repositories;

import com.lobanov.models.Category;
import com.lobanov.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.font.OpenType;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long > {

    @Query ("select cat from Category cat where cat.user.id = :id")
    List<Category> findAllByUserId(Long id);

   // @EntityGraph(attributePaths = {"expensesList", "user"})
    Optional<Category> findCategoryByUserId(Long id);
    Optional<Category> findCategoryById(Long id);

    Optional<Category> findCategoryByNameAndUser(String name, User user);

    @Query("select c from Category c inner join User u ON c.user.id =u.id where c.id =:categoryId and u.id = :userId ")
    Optional<Category> findCategoryByIdAndUserId(Long categoryId, Long userId);
}
