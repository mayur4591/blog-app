package com.example.blog_app.controllers;

import com.example.blog_app.payloads.CategoryDto;
import com.example.blog_app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private  CategoryService categoryService;

    @PostMapping("/")
    private ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
       CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);

       return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    private ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto,categoryId);

        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    private void deleteCategory(@PathVariable Integer categoryId){
        categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/{categoryId}")
    private ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/")
    private ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();

        return ResponseEntity.ok(categoryDtoList);
    }

}
