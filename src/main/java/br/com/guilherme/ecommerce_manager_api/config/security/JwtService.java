package br.com.guilherme.ecommerce_manager_api.config.security;

import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    public String generateToken(UsuarioEntity usuario) {
        return JWT.create()
                .withSubject(usuario.getId().toString())
                .withClaim("role", usuario.getPerfil().name())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign(Algorithm.HMAC256(secret));
    }

    public String extractUserId(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getClaim("roles").asList(String.class);
    }

    public boolean isValid(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}