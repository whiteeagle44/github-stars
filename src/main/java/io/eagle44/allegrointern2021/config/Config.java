package io.eagle44.allegrointern2021.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
