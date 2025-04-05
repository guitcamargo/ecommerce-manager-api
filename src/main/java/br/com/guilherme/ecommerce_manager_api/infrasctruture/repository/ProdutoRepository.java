package br.com.guilherme.ecommerce_manager_api.infrasctruture.repository;

import br.com.guilherme.ecommerce_manager_api.domain.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, String> {
} 