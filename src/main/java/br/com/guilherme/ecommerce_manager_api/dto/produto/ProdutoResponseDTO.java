package br.com.guilherme.ecommerce_manager_api.dto.produto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        String id,
        String nome,
        String descricao,
        BigDecimal preco,
        String categoria,
        Integer quantidadeEstoque,
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime dataCriacao,
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime dataAtualizacao
) {}