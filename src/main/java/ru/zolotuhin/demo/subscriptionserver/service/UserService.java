package ru.zolotuhin.demo.subscriptionserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.zolotuhin.demo.subscriptionserver.dto.UserDto;
import ru.zolotuhin.demo.subscriptionserver.exception.ResourceNotFoundException;
import ru.zolotuhin.demo.subscriptionserver.model.User;
import ru.zolotuhin.demo.subscriptionserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserDto userDto) {
        log.info("Creating new user with email: {}", userDto.getEmail());
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, UserDto userDto) {
        log.info("Updating user with id: {}", id);
        User user = getUserById(id);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
