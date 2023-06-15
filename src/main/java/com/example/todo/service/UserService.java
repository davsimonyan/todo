package com.example.todo.service;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;

public interface UserService {

    boolean save(User user);

    UserDto findById(Long id);

    void deleteById(Long id);

    UserDto update(UserDto userDto);

}

