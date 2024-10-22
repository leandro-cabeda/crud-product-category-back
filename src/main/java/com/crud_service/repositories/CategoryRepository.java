package com.crud_service.repositories;

import com.crud_service.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByNameContaining(String name);

    boolean existsByName(String name);

    Category findByName(String name);
}
