package br.com.guilherme.ecommerce_manager_api.dto.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        String id,
        String status,
        BigDecimal valorTotal,
        LocalDateTime dataCriacao,
        List<PedidoItemResponseDTO> items
) {

}