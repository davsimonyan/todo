package com.example.todo.endpoint;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Todo;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoEndpoint {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> add(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody Todo todo) {
        todo.setUser(currentUser.getUser());
        return ResponseEntity.ok(todoService.save(todo));
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> findAllByUser(@AuthenticationPrincipal CurrentUser currentUser) {
        List<TodoDto> byUserId = todoService.findByUserId(currentUser);
        return ResponseEntity.ok(byUserId);
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<TodoDto>> byStatus(@AuthenticationPrincipal CurrentUser currentUser,
                                                  @RequestParam Status status) {
        return ResponseEntity.ok(todoService.findByUserAndStatus(currentUser, status));
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<TodoDto>> byCategory(@AuthenticationPrincipal CurrentUser currentUser,
                                                    @RequestParam Category category) {
        return ResponseEntity.ok(todoService.findByUserAndCategory(currentUser, category));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Status status) {
        return ResponseEntity.ok(todoService.update(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable("id") Long id) {
        todoService.remove(currentUser, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
