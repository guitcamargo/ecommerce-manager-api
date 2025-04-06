package br.com.guilherme.ecommerce_manager_api.config.security;

import br.com.guilherme.ecommerce_manager_api.application.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthorizationFIlter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String WITHOUT_HEADER = "WITHOUT_HEADER";
    private static final String BEARER_AUTH = "Bearer ";

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoveryToken(request);

        if (token.equals(WITHOUT_HEADER)) {
            filterChain.doFilter(request, response);
            return;
        }

        var userId = jwtService.extractUserId(token);

        usuarioService.loadUserById(Long.valueOf(userId))
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()))
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_AUTH)) return WITHOUT_HEADER;
        return authHeader.replace(BEARER_AUTH, "");
    }


}