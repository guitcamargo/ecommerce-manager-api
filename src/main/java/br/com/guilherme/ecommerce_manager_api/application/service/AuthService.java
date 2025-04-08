package br.com.guilherme.ecommerce_manager_api.application.service;

import br.com.guilherme.ecommerce_manager_api.config.security.JwtService;
import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDTO auth(AuthRequestDTO request) {
        log.info("m=auth Request de autorização: {}", request);
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );

        var usuario = (UsuarioEntity) auth.getPrincipal();
        var token = jwtService.generateToken(usuario);
        return AuthResponseDTO.builder()
                .tipo("Bearer")
                .token(token)
                .build();
    }


    public UsuarioEntity extractUserLogged() {
        return (UsuarioEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}