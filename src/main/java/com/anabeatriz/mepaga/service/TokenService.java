package com.anabeatriz.mepaga.service;

import com.anabeatriz.mepaga.model.Usuario;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;


@Service
public class TokenService {

    private static final String SECRET = "uma-chave-secreta-bem-grande-e-segura-123!";
    private static final long EXPIRATION = 86400000; // 24 horas

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }
}

