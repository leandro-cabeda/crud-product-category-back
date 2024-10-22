package com.crud_service.services;

import com.crud_service.entities.Category;
import com.crud_service.exception.RegisterExistsException;
import com.crud_service.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategoriesFiltered(String name) {

        if(name == null || name.isEmpty())
            return categoryRepository.findAll();

        return categoryRepository.findByNameContaining(name);
    }

    public Category createCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {
            throw new RegisterExistsException("Categoria com o nome " + category.getName()
                    + " ja existe na base de dados!");
        }

        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        Category oldCategory = categoryRepository.findByName(category.getName());

        if (oldCategory != null && !oldCategory.getId().equals(id)) {
            throw new RegisterExistsException("Categoria com o nome " + category.getName()
                    + " ja existe na base de dados!");
        }

        category.setId(id);
        category.setUpdatedAt(new Date());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

}

