package com.epam.client;

import com.epam.dto.UserByUsername;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${auth.service.baseUrl}")
public interface AuthClient {
    @GetMapping("/token")
    String getToken(@RequestBody UserByUsername dto);

}
