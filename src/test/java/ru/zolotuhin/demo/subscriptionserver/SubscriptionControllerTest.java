package ru.zolotuhin.demo.subscriptionserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.zolotuhin.demo.subscriptionserver.controller.SubscriptionController;
import ru.zolotuhin.demo.subscriptionserver.dto.SubscriptionDto;
import ru.zolotuhin.demo.subscriptionserver.exception.GlobalExceptionHandler;
import ru.zolotuhin.demo.subscriptionserver.model.Subscription;
import ru.zolotuhin.demo.subscriptionserver.model.User;
import ru.zolotuhin.demo.subscriptionserver.repository.SubscriptionRepository;
import ru.zolotuhin.demo.subscriptionserver.repository.UserRepository;
import ru.zolotuhin.demo.subscriptionserver.service.SubscriptionService;
import ru.zolotuhin.demo.subscriptionserver.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
@Import({SubscriptionService.class, UserService.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionRepository subscriptionRepository;

    @MockitoBean
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void addSubscription_ShouldReturnCreated() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setServiceName("Netflix");
        subscription.setStartDate(LocalDate.of(2023, 1, 1));
        subscription.setEndDate(LocalDate.of(2023, 12, 31));
        subscription.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setServiceName("Netflix");
        subscriptionDto.setStartDate(LocalDate.of(2023, 1, 1));
        subscriptionDto.setEndDate(LocalDate.of(2023, 12, 31));

        mockMvc.perform(post("/users/1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.serviceName").value("Netflix"));
    }

    @Test
    void getUserSubscriptions_ShouldReturnSubscriptions() throws Exception {
        User user = new User();
        user.setId(1L);

        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscription1.setServiceName("Netflix");
        subscription1.setUser(user);

        Subscription subscription2 = new Subscription();
        subscription2.setId(2L);
        subscription2.setServiceName("Spotify");
        subscription2.setUser(user);

        when(userRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.findByUserId(1L)).thenReturn(List.of(subscription1, subscription2));

        mockMvc.perform(get("/users/1/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].serviceName").value("Netflix"))
                .andExpect(jsonPath("$[1].serviceName").value("Spotify"));
    }

    @Test
    void deleteSubscription_ShouldReturnNoContent() throws Exception {
        User user = new User();
        user.setId(1L);

        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setServiceName("Netflix");
        subscription.setUser(user);

        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        doNothing().when(subscriptionRepository).delete(subscription);

        mockMvc.perform(delete("/users/1/subscriptions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getTop3PopularSubscriptions_ShouldReturnPopularSubscriptions() throws Exception {
        Object[] netflix = new Object[]{"Netflix", 15L};
        Object[] spotify = new Object[]{"Spotify", 10L};
        Object[] disney = new Object[]{"Disney+", 8L};

        when(subscriptionRepository.findTop3PopularSubscriptions())
                .thenReturn(List.of(netflix, spotify, disney));

        mockMvc.perform(get("/api/subscriptions/top")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].serviceName").value("Netflix"))
                .andExpect(jsonPath("$[0].subscriptionCount").value(15))
                .andExpect(jsonPath("$[1].serviceName").value("Spotify"))
                .andExpect(jsonPath("$[1].subscriptionCount").value(10))
                .andExpect(jsonPath("$[2].serviceName").value("Disney+"))
                .andExpect(jsonPath("$[2].subscriptionCount").value(8));
    }

    @Test
    void getTop3PopularSubscriptions_WhenNoSubscriptions_ShouldReturnEmptyList() throws Exception {
        when(subscriptionRepository.findTop3PopularSubscriptions())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/subscriptions/top")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
