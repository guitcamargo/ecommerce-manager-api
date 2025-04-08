package br.com.guilherme.ecommerce_manager_api.application.usuario;

import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioService {

    UsuarioEntity findById(Long id);
    Optional<UserDetails> loadUserById(Long id);
}
