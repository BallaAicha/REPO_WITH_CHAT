package org.etutoria.listingservice.services.impl;

import org.etutoria.listingservice.entities.Category;
import org.etutoria.listingservice.entities.ParentCategory;
import org.etutoria.listingservice.repositories.CategoryRepository;
import org.etutoria.listingservice.repositories.ParentCategoryRepository;
import org.etutoria.listingservice.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ParentCategoryRepository parentCategoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ParentCategoryRepository parentCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.parentCategoryRepository = parentCategoryRepository;
    }

    @Override
    public Category createCategory(Category categoryDTO) {
        if (categoryDTO.getId() == null || categoryDTO.getId().isEmpty()) {
            categoryDTO.setId(java.util.UUID.randomUUID().toString());
        }
        if (categoryDTO.getParentCategoryId() != null && !categoryDTO.getParentCategoryId().isEmpty()) {
            categoryRepository.findById(categoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("ParentCategory not found"));
        }
        return categoryRepository.save(categoryDTO);
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public Category updateCategory(String id, Category categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        if (categoryDTO.getParentCategoryId() != null && !categoryDTO.getParentCategoryId().isEmpty()) {
            ParentCategory parentCategory = parentCategoryRepository.findById(categoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("ParentCategory not found"));
            category.setParentCategoryId(parentCategory.getId());
        }
        return categoryRepository.save(category);
    }
    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}