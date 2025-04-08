package br.com.guilherme.ecommerce_manager_api.factory;

import br.com.guilherme.ecommerce_manager_api.domain.document.ProdutoDocument;
import br.com.guilherme.ecommerce_manager_api.domain.entity.ProdutoEntity;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoFactory {

    private static final LocalDateTime DATA_CADASTRO = LocalDateTime.of(2025,5,8, 0,0);

    public static ProdutoEntity createEntity(String id) {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(id);
        entity.setNome("Produto Teste");
        entity.setDescricao("Descrição");
        entity.setPreco(BigDecimal.TEN);
        entity.setQuantidadeEstoque(10L);
        entity.setDataAtualizacao(DATA_CADASTRO);
        entity.setDataAtualizacao(DATA_CADASTRO);
        return entity;
    }

    public static ProdutoRequestDTO createRequest() {
        return new ProdutoRequestDTO("Produto Teste", "Descrição", BigDecimal.TEN, "especial", 10l);
    }

    public static ProdutoResponseDTO createResponse(String id) {
        return new ProdutoResponseDTO(id, "Produto Teste", "Descrição", BigDecimal.TEN, "especial", 10, DATA_CADASTRO, DATA_CADASTRO);
    }

    public static ProdutoDocument createDocument(){
        return ProdutoDocument.builder()
                .quantidadeEstoque(1L).build();
    }

    public static ProdutoEntity produtoComEstoque(String id, Long estoque) {
        var produto = new ProdutoEntity();
        produto.setId(id);
        produto.setNome("Produto Teste");
        produto.setQuantidadeEstoque(estoque);
        return produto;
    }

    public static ProdutoDocument documentComEstoque(String id, Long estoque) {
        var doc = new ProdutoDocument();
        doc.setId(id);
        doc.setQuantidadeEstoque(estoque);
        return doc;
    }
}
