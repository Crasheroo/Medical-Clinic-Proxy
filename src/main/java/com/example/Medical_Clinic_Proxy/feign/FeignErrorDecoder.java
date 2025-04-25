package com.example.Medical_Clinic_Proxy.feign;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        boolean serviceUnavailable = response.status() == 500;
        return serviceUnavailable
                ? new RetryableException(response.status(), exception.getMessage(), response.request().httpMethod(), exception, (Long) null, response.request())
                : exception;
    }
}
