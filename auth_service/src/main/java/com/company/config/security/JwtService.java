package com.company.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    @Value("${jwt.access.token.secret.key}")
    private String accessTokenSecretKey;
    @Value("${jwt.access.token.expiry}")
    private long expiryInMinutes;

    public String generateAccessToken(@NonNull String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryInMinutes))
                .signWith(signKey(), io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isValidToken(@NonNull String token) {
        return getExpiry(token)
                .after(new Date());
    }

    public String getUsername(@NonNull String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpiry(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signKey() {
        byte[] bytes = Decoders.BASE64.decode(this.accessTokenSecretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
