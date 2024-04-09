package com.project.blog.services;

import com.project.blog.model.Category;
import com.project.blog.payloads.CategoryDto;
import com.project.blog.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, int id);
    List<CategoryDto> getCategories();
    CategoryDto getCategoryById(int id);
    void deleteCategory(int id);
}
