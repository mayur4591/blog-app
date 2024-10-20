package com.example.blog_app.services.impl;

import com.example.blog_app.entities.Category;
import com.example.blog_app.exceptions.ResourceNotFoundException;
import com.example.blog_app.payloads.CategoryDto;
import com.example.blog_app.repository.CategoryRepo;
import com.example.blog_app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto,Category.class);
        return modelMapper.map(categoryRepo.save(category),CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryRepo.save(category);

        CategoryDto categoryDto1 = modelMapper.map(category,CategoryDto.class);

        return categoryDto1;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categoryRepo.findAll().forEach((category -> {
            categoryDtos.add(modelMapper.map(category,CategoryDto.class));
        }));
        return categoryDtos;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));

        categoryRepo.delete(category);
    }
}
