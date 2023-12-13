package com.epam.config.filter;

import com.epam.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final AuthClient authClient;
    private static final List<String> openApiEndpoints = List.of(
            "/main/public/**",
            "/eureka/**",
            "/auth/**"
    );
    private final Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
            .stream()
            .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public AuthFilter(@Lazy AuthClient authClient) {
        super(Config.class);
        this.authClient = authClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest httpRequest = exchange.getRequest();
            log.info("AuthFilter: {}", httpRequest.getURI().getPath());
            if (isSecured.test(httpRequest)) {
                String authHeaders = getAuthorizationHeader(httpRequest);
                boolean isValid = authClient.validateToken(authHeaders);
                if (!isValid) {
                    throw new RuntimeException("Invalid token");
                }
            }
            return chain.filter(exchange);
        };
    }

    private String getAuthorizationHeader(ServerHttpRequest httpRequest) {
        HttpHeaders httpHeaders = httpRequest.getHeaders();
        if (!httpHeaders.containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new RuntimeException("Missing authorization information");
        }
        String authHeaders = Objects.requireNonNull(httpHeaders.get(HttpHeaders.AUTHORIZATION)).get(0);
        if (!Objects.isNull(authHeaders) && authHeaders.startsWith("Bearer ")) {
            authHeaders = authHeaders.substring(7);
        }
        return authHeaders;
    }

    public static class Config {
    }
}
