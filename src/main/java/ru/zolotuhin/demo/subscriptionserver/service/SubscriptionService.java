package ru.zolotuhin.demo.subscriptionserver.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.zolotuhin.demo.subscriptionserver.dto.SubscriptionDto;
import ru.zolotuhin.demo.subscriptionserver.exception.ResourceNotFoundException;
import ru.zolotuhin.demo.subscriptionserver.model.Subscription;
import ru.zolotuhin.demo.subscriptionserver.model.User;
import ru.zolotuhin.demo.subscriptionserver.repository.SubscriptionRepository;
import ru.zolotuhin.demo.subscriptionserver.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public Subscription addSubscriptionToUser(Long userId, SubscriptionDto subscriptionDto) {
        log.info("Adding subscription to user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Subscription subscription = new Subscription();
        subscription.setServiceName(subscriptionDto.getServiceName());
        subscription.setStartDate(subscriptionDto.getStartDate());
        subscription.setEndDate(subscriptionDto.getEndDate());
        subscription.setUser(user);

        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        log.info("Fetching subscriptions for user with id: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return subscriptionRepository.findByUserId(userId);
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Deleting subscription with id: {} for user with id: {}", subscriptionId, userId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Subscription not found for user with id: " + userId);
        }

        subscriptionRepository.delete(subscription);
    }

    public List<Map<String, Object>> getTop3PopularSubscriptions() {
        return subscriptionRepository.findTop3PopularSubscriptions().stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("serviceName", result[0]);
                    map.put("subscriptionCount", result[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }
}
