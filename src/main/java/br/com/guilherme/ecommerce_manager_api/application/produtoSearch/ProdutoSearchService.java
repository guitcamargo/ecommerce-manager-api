package br.com.guilherme.ecommerce_manager_api.application.produtoSearch;

import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoSearchFilterDTO;

import java.util.List;

public interface ProdutoSearchService {
    List<ProdutoResponseDTO> findAllBy(ProdutoSearchFilterDTO filter);
}
