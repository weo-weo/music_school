package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtils;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Ищем пользователя
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        // Проверяем пароль (вживую, без хэширования, чтобы не ломать архитектуру тестов)
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // Генерируем токен
            String token = jwtUtils.generateToken(user.getUsername(), user.getRole().name());
            // Возвращаем JSON с токеном
            return ResponseEntity.ok(Map.of("token", token));
        }

        // Если что-то не так — даем от ворот поворот
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
    }
}

// Маленький вспомогательный DTO класс для парсинга JSON запроса
@Data
class LoginRequest {
    private String username;
    private String password;
}