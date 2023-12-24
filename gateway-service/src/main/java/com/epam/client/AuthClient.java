package com.epam.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("${auth.service.baseUrl}")
public interface AuthClient {
    Logger log = LoggerFactory.getLogger(AuthClient.class);

    @CircuitBreaker(name = "authClientCircuit", fallbackMethod = "validateTokenFallBack")
    @PostMapping("/token/validate")
    Boolean validateToken(@RequestParam(name = "token") String token);

    @SuppressWarnings("unused")
    default Boolean validateTokenFallBack(String token, Exception e) {
        log.error("Error message = '{}'", e.getMessage(), e.getCause());
        return false;
    }
}
