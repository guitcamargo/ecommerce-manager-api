package br.com.guilherme.ecommerce_manager_api.dto.produto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequestDTO (
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull @DecimalMin("0.0") BigDecimal preco,
        @NotBlank String categoria,
        @NotNull @Min(0) Long quantidadeEstoque
) {}