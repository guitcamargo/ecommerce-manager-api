package br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository;

import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UserDetails> findByLogin(String login);
} 