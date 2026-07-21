package com.mohaymen.norification;

import org.springframework.boot.SpringApplication;

public class TestNorificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(NotificationServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
