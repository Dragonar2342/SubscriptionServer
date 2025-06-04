package ru.zolotuhin.demo.subscriptionserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zolotuhin.demo.subscriptionserver.model.Subscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionResponseDto {
    private Long id;
    private String serviceName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public SubscriptionResponseDto(Subscription subscription) {
        this.id = subscription.getId();
        this.serviceName = subscription.getServiceName();
        this.startDate = subscription.getStartDate();
        this.endDate = subscription.getEndDate();
        this.createdAt = subscription.getCreatedAt();
    }
}
