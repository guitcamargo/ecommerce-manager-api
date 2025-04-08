package br.com.guilherme.ecommerce_manager_api.application.produto;

import br.com.guilherme.ecommerce_manager_api.domain.entity.ProdutoEntity;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;

public interface ProdutoService {

    ProdutoResponseDTO findById(String id);
    ProdutoResponseDTO create(ProdutoRequestDTO dto);
    ProdutoResponseDTO update(String id, ProdutoRequestDTO dto);
    void delete(String id);
    ProdutoEntity findEntityById(String id);
    void validate(String produtoId);
    void updateStock(String idProduto, int quantidade);
}
