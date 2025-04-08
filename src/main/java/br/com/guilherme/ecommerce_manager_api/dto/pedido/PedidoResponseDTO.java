package br.com.guilherme.ecommerce_manager_api.dto.pedido;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PedidoResponseDTO(
        String id,
        String status,
        BigDecimal valorTotal,
        LocalDateTime dataCriacao,
        List<PedidoItemResponseDTO> items
) {

}