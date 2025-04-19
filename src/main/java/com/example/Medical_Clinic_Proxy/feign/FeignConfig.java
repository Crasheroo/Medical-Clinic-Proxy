package com.example.Medical_Clinic_Proxy.feign;

import feign.FeignException;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(1000, 5000, 3);
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return (methodKey, response) -> {
            FeignException exception = FeignException.errorStatus(methodKey, response);
            boolean serviceUnavailable = response.status() == 500;
            return serviceUnavailable
                    ? new RetryableException(response.status(), exception.getMessage(), response.request().httpMethod(), exception, (Long) null, response.request())
                    : exception;
        };
    }
}