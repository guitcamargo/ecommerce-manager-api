package br.com.guilherme.ecommerce_manager_api.dto.produto;

import java.math.BigDecimal;

public record ProdutoSearchFilterDTO(String nome,
                                     String categoria,
                                     BigDecimal precoMin,
                                     BigDecimal precoMax
) {}
