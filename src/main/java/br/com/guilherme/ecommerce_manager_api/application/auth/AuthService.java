package br.com.guilherme.ecommerce_manager_api.application.auth;

import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO auth(AuthRequestDTO request);
    UsuarioEntity extractUserLogged();
}
