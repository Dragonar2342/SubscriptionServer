package ru.zolotuhin.demo.subscriptionserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zolotuhin.demo.subscriptionserver.dto.SubscriptionDto;
import ru.zolotuhin.demo.subscriptionserver.dto.SubscriptionResponseDto;
import ru.zolotuhin.demo.subscriptionserver.model.Subscription;
import ru.zolotuhin.demo.subscriptionserver.service.SubscriptionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Управление подписками", description = "API для работы с подписками пользователей")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{userId}/subscriptions")
    @Operation(summary = "Добавить подписку пользователю", description = "Добавляет новую подписку для указанного пользователя")
    @ApiResponse(responseCode = "201", description = "Подписка успешно добавлена")
    @ApiResponse(responseCode = "400", description = "Некорректные данные подписки")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<SubscriptionResponseDto> addSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionService.addSubscriptionToUser(userId, subscriptionDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SubscriptionResponseDto(subscription));
    }

    @GetMapping("/users/{userId}/subscriptions")
    @Operation(summary = "Получить подписки пользователя", description = "Возвращает список всех подписок указанного пользователя")
    @ApiResponse(responseCode = "200", description = "Список подписок получен")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subscriptionId}")
    @Operation(summary = "Удалить подписку", description = "Удаляет подписку пользователя по её идентификатору")
    @ApiResponse(responseCode = "204", description = "Подписка успешно удалена")
    @ApiResponse(responseCode = "404", description = "Пользователь или подписка не найдена")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("api/subscriptions/top")
    @Operation(summary = "Топ-3 популярных подписок", description = "Возвращает 3 самых популярных сервиса по количеству подписок")
    @ApiResponse(responseCode = "200", description = "Список популярных подписок получен")
    public ResponseEntity<List<Map<String, Object>>> getTop3PopularSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTop3PopularSubscriptions());
    }
}
