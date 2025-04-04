package br.com.guilherme.ecommerce_manager_api.core.repository;

import br.com.guilherme.ecommerce_manager_api.core.domain.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
} 