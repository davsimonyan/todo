package com.example.todo.service;

import com.example.todo.dto.CategoryDto;
import com.example.todo.entity.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    List<CategoryDto> findAll();

    void remove(Long id);

}

