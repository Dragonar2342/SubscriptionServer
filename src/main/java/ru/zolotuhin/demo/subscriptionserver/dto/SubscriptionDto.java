package ru.zolotuhin.demo.subscriptionserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zolotuhin.demo.subscriptionserver.model.Subscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Данные подписки")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    @Schema(description = "Название сервиса", example = "Netflix")
    @NotBlank(message = "Название сервиса не может быть пустым")
    @Size(max = 255, message = "Название сервиса не может превышать 255 символов")
    private String serviceName;

    @Schema(description = "Дата начала подписки (YYYY-MM-DD)", example = "2023-01-01")
    @NotNull(message = "Дата начала не может быть пустой")
    private LocalDate startDate;

    @Schema(description = "Дата окончания подписки (YYYY-MM-DD)", example = "2023-12-31")
    @NotNull(message = "Дата окончания не может быть пустой")
    private LocalDate endDate;
}
