package com.example.todo.service;

import com.example.todo.dto.TodoDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.security.CurrentUser;

import java.util.List;

public interface TodoService {

    Todo save(Todo todo);

    List<TodoDto> findByUserAndStatus(CurrentUser currentUser, Status status);

    List<TodoDto> findByUserAndCategory(CurrentUser currentUser, Category category);

    List<TodoDto> findByUserId(CurrentUser currentUser);

    TodoDto update(Long id,Status status);

    void remove(CurrentUser currentUser,Long id);
}

