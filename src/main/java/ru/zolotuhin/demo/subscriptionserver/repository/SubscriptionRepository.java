package ru.zolotuhin.demo.subscriptionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.zolotuhin.demo.subscriptionserver.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);

    @Query("SELECT s.serviceName, COUNT(s) as count " +
            "FROM Subscription s " +
            "GROUP BY s.serviceName " +
            "ORDER BY count DESC " +
            "LIMIT 3")
    List<Object[]> findTop3PopularSubscriptions();
}
