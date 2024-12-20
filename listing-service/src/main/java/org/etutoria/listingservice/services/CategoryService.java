package org.etutoria.listingservice.services;

import org.etutoria.listingservice.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category categoryDTO);
    Category getCategoryById(String id);
    Category updateCategory(String id, Category categoryDTO);
    void deleteCategory(String id);
    List<Category> getAllCategories();
}
