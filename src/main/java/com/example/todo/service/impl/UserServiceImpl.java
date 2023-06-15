package com.example.todo.service.impl;

import com.example.todo.dto.UserDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.mapper.UserMapper;
import com.example.todo.repo.UserRepository;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean save(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto update(UserDto user) {
        User entity = userMapper.toEntity(user);
        return userMapper.toDto(userRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("entity not found");
        }
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return userMapper.toDto(byId.get());
        } else {
            throw new EntityNotFoundException("entity not found");
        }
    }
}
