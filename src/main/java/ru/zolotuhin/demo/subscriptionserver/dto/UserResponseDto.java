package ru.zolotuhin.demo.subscriptionserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zolotuhin.demo.subscriptionserver.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private List<SubscriptionResponseDto> subscriptions;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.subscriptions = user.getSubscriptions().stream()
                .map(SubscriptionResponseDto::new)
                .collect(Collectors.toList());
    }
}
