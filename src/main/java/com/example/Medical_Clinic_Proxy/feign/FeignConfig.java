package com.example.Medical_Clinic_Proxy.feign;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .options(new Request.Options(5000, 5000))
                .retryer(new Retryer.Default());
    }
}