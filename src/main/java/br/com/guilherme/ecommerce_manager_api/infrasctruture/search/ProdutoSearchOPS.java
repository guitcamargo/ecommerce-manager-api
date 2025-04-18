package br.com.guilherme.ecommerce_manager_api.infrasctruture.search;

import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoSearchFilterDTO;

import java.util.List;

public interface ProdutoSearchOPS {
    List<ProdutoResponseDTO> findAllBy(ProdutoSearchFilterDTO filter);
}
