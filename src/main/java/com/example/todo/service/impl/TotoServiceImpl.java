package com.example.todo.service.impl;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.repo.TodoRepository;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TotoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoMapper todoMapper;

    @Override
    public Todo save(Todo todo) {
        todo.setStatus(Status.NOT_STARTED);
        return todoRepository.save(todo);
    }

    @Override
    public List<TodoDto> findByUserAndStatus(CurrentUser currentUser, Status status) {
        return todoMapper.toDto(todoRepository.findAllByUserAndStatus(currentUser.getUser(), status));
    }

    @Override
    public List<TodoDto> findByUserAndCategory(CurrentUser currentUser, Category category) {
        return todoMapper.toDto(todoRepository.findAllByUserAndCategory(currentUser.getUser(), category));
    }

    @Override
    public TodoDto update(Long id, Status status) {
        TodoDto byId = findById(id);
        byId.setStatus(status);
        Todo todo = todoMapper.toEntity(byId);
        Todo save = todoRepository.save(todo);
        return todoMapper.toDto(save);
    }

    @Override
    public List<TodoDto> findByUserId(CurrentUser currentUser) {
        return todoMapper.toDto(todoRepository.findAllByUser(currentUser.getUser()));
    }

    public TodoDto findById(Long id) {
        Optional<Todo> byId = todoRepository.findById(id);
        if (byId.isPresent()) {
            return todoMapper.toDto(byId.get());
        } else {
            throw null;
        }
    }

    @Override
    public void remove(CurrentUser currentUser, Long id) {
        TodoDto byId = findById(id);
        if (currentUser.getUser().getId().equals(byId.getUser().getId())) {
            Todo todo = todoMapper.toEntity(byId);
            todoRepository.delete(todo);
        }
    }
}
