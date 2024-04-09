package com.project.blog.controllers;


import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.CategoryDto;
import com.project.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategoryDto = this.categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtos = this.categoryService.getCategories();
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") int categoryId){
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("id") int categoryId){
        CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategoryDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable("id") int categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully",true), HttpStatus.CREATED);
    }





}
