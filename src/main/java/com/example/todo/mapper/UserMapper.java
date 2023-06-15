package com.example.todo.mapper;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserAuthRequestDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {

    User map(CreateUserRequestDto dto);

    User map(UserAuthRequestDto dto);
}
