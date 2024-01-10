package com.company.controller;

import com.company.config.security.SessionUser;
import com.company.domain.User;
import com.company.dto.UserByUsername;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    //spring boot security test
    private final UserService userService;
    private final SessionUser sessionUser;

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public String getToken(@RequestBody UserByUsername dto) {
        log.info("Generating token");
        return userService.generateToken(dto);
    }

    @PostMapping("/token/validate")
    @ResponseStatus(HttpStatus.OK)
    public Boolean validateToken(@RequestParam(name = "token") String token) {
        log.info("Validating token");
        return userService.validateToken(token);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public User getUser() {
        log.info("Getting user");
        return sessionUser.user();
    }
}
