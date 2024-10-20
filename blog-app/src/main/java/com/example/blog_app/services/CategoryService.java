package com.example.blog_app.services;

import com.example.blog_app.payloads.CategoryDto;
import com.example.blog_app.repository.CategoryRepo;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    CategoryDto getCategoryById(Integer categoryId);
    List<CategoryDto> getAllCategories();
    void deleteCategory(Integer categoryId);

}
