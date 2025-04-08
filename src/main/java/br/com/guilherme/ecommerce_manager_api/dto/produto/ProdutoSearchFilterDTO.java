package br.com.guilherme.ecommerce_manager_api.dto.produto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoSearchFilterDTO(String nome,
                                     String categoria,
                                     BigDecimal precoMin,
                                     BigDecimal precoMax
) {}
