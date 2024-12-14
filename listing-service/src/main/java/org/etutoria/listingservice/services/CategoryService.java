package org.etutoria.listingservice.services;

import org.etutoria.listingservice.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category categoryDTO);
    Category getCategoryById(Integer id);
    Category updateCategory(Integer id, Category categoryDTO);
    void deleteCategory(Integer id);
    List<Category> getAllCategories();
}
