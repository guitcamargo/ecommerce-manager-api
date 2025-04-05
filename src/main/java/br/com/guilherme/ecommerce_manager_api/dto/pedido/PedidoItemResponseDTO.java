package br.com.guilherme.ecommerce_manager_api.dto.pedido;

import java.math.BigDecimal;

public record PedidoItemResponseDTO(String id, Integer quantidade, BigDecimal precoUnitario) {}