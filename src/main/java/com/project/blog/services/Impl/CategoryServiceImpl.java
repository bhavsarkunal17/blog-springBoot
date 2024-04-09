package com.project.blog.services.Impl;

import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.model.Category;
import com.project.blog.payloads.CategoryDto;
import com.project.blog.repositories.CategoryRepo;
import com.project.blog.services.CategoryService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto,Category.class);
        Category createdCategory = this.categoryRepo.save(category);
        return modelMapper.map(createdCategory,CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {

        Category originalCategory = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        originalCategory.setCategoryTitle(categoryDto.getCategoryTitle());
        originalCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(originalCategory);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream()
                .map((category) -> this.modelMapper.map(category,CategoryDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDto getCategoryById(int id) {

        Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        return this.modelMapper.map(category,CategoryDto.class);

    }

    @Override
    public void deleteCategory(int id) {

        this.categoryRepo.deleteById(id);
    }
}
