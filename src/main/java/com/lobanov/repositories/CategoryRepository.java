package com.lobanov.repositories;

import com.lobanov.models.Category;
import com.lobanov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.font.OpenType;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long > {

    //Set<Category> getAllCategoriesById(Long userId);

    @Query ("select cat from Category cat where cat.user.id = :id")
    List<Category> findAllByUserId(Long id);

    Optional<Category> findCategoryByNameAndUser(String name, User user);
}
