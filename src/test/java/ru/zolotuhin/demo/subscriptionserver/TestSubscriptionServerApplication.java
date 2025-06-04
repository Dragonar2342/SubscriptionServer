package ru.zolotuhin.demo.subscriptionserver;

import org.springframework.boot.SpringApplication;

public class TestSubscriptionServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(SubscriptionServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
