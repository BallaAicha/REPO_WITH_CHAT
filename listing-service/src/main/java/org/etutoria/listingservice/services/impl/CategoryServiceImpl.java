package org.etutoria.listingservice.services.impl;

import org.etutoria.listingservice.entities.Category;
import org.etutoria.listingservice.repositories.CategoryRepository;
import org.etutoria.listingservice.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category categoryDTO) {
        return categoryRepository.save(categoryDTO);
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public Category updateCategory(Integer id, Category categoryDTO) {
        return categoryRepository.save(categoryDTO);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
