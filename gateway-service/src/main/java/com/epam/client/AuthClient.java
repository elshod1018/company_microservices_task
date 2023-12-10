package com.epam.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${auth.service.baseUrl}")
public interface AuthClient {
    @PostMapping("/token/validate")
    Boolean validateToken(@RequestParam(name = "token") String token);
}
