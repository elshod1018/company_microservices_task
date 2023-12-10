package com.company.config.security;

import com.company.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SessionUser {
    public User user() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken)
            return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails)
            return userDetails.user();
        return null;
    }

    public Integer id() {
        User user = user();
        if (Objects.isNull(user)) {
            return -1;
        }
        return user.getId();
    }
}
