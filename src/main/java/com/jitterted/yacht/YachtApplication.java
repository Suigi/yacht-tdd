package com.jitterted.yacht;

import com.jitterted.yacht.application.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YachtApplication {

    public static void main(String[] args) {
        SpringApplication.run(YachtApplication.class, args);
    }

    @Bean
    public GameService createGameService() {
        return GameService.create();
    }

}
