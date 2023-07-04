package com.xxx.WhatsAppWebClone.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenProvider {

    SecretKey secretKey= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication){
        String jwt= Jwts.builder()
                .setIssuer("Emine Tekcam")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .signWith(secretKey)
                .compact();
        return jwt;
    }

    public String getEmailFromToken(String jwt){
        jwt=jwt.substring(7);
        Claims claims= Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        String email=String.valueOf(claims.get("email"));
        return email;
    }
}
