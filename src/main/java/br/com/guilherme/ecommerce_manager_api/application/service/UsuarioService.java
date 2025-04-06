package br.com.guilherme.ecommerce_manager_api.application.service;

import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;


    public UsuarioEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> NotFoundException.ofUsuario(id));
    }

    public Optional<UserDetails> loadUserById(Long id) {
        return Optional.of(this.findById(id));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));
    }
}