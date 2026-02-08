package com.tiuon.moneymanager.service;

import com.tiuon.moneymanager.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto saveCategory(CategoryDto categoryDto);
    List<CategoryDto> getCategoriesForCurrentUser();
    List<CategoryDto> getCategoriesByTypeForCurrentUser(String type);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
}
