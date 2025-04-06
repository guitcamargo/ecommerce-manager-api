package br.com.guilherme.ecommerce_manager_api.dto.auth;

import lombok.Builder;

@Builder
public record AuthResponseDTO(String token, String tipo) {}