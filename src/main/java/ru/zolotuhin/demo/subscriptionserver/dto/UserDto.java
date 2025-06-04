package ru.zolotuhin.demo.subscriptionserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Данные пользователя")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 255, message = "Имя не может превышать 255 символов")
    private String name;

    @Schema(description = "Email пользователя", example = "ivan@example.com")
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Size(max = 255, message = "Email не может превышать 255 символов")
    private String email;
}
