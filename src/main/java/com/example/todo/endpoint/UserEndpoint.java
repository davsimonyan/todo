package com.example.todo.endpoint;

import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserAuthRequestDto;
import com.example.todo.dto.UserAuthResponseDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.mapper.UserMapper;
import com.example.todo.repo.UserRepository;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.UserService;
import com.example.todo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userRepository.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        User user = userMapper.map(createUserRequestDto);
        if (userService.save(user)) {
            return ResponseEntity.ok(userMapper.toDto(user));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        UserDto byId = userService.findById(id);
        UserDto userDto = UserDto.builder()
                .id(byId.getId())
                .email(byId.getEmail())
                .name(byId.getName())
                .surname(byId.getSurname())
                .role(byId.getRole())
                .build();
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable("id") Long id) {
        if (currentUser.getUser().getRole().equals(Role.ADMIN)) {
            userService.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody UserDto userDto) {
        if (currentUser.getUser().getId().equals(userDto.getId())) {
        return ResponseEntity.ok(userService.update(userDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
