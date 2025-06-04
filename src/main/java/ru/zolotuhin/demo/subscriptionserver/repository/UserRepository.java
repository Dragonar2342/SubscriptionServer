package ru.zolotuhin.demo.subscriptionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zolotuhin.demo.subscriptionserver.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
