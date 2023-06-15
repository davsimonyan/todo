package com.example.todo.dto;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {

    private Long id;
    private String title;
    private Status status;
    private Category category;
    private User user;

}
