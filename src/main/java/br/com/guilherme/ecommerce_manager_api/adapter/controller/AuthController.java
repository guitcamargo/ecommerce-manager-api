package br.com.guilherme.ecommerce_manager_api.adapter.controller;

import br.com.guilherme.ecommerce_manager_api.application.service.AuthService;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoint para autenticação de usuários")
public class AuthController {

    private final AuthService service;

    @PostMapping
    public AuthResponseDTO auth(@Valid @RequestBody
                                AuthRequestDTO request) {

        return service.auth(request);
    }
}