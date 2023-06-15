package com.example.todo.repo;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUser(User user);

    List<Todo> findAllByUserAndStatus(User user, Status status);

    List<Todo> findAllByUserAndCategory(User user, Category category);

}
