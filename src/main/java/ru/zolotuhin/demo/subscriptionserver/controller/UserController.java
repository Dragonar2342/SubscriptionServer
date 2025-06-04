package ru.zolotuhin.demo.subscriptionserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zolotuhin.demo.subscriptionserver.dto.UserDto;
import ru.zolotuhin.demo.subscriptionserver.dto.UserResponseDto;
import ru.zolotuhin.demo.subscriptionserver.model.User;
import ru.zolotuhin.demo.subscriptionserver.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Управление пользователями", description = "API для работы с пользователями")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать нового пользователя", description = "Создает нового пользователя с указанными данными")
    @ApiResponse(responseCode = "201", description = "Пользователь успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает данные пользователя по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные пользователя", description = "Обновляет данные существующего пользователя")
    @ApiResponse(responseCode = "200", description = "Данные пользователя успешно обновлены")
    @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его идентификатору")
    @ApiResponse(responseCode = "204", description = "Пользователь успешно удален")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
