package com.example.todo.mapper;

import com.example.todo.dto.CategoryDto;
import com.example.todo.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {
}
