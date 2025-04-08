package br.com.guilherme.ecommerce_manager_api.dto.pedido;

import br.com.guilherme.ecommerce_manager_api.validation.ValidProduto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record PedidoRequestDTO(
        @NotEmpty(message = "Obrigatório pelo menos um item no pedido")
        @Valid
        List<PedidoItemRequestDTO> items) {
    public record PedidoItemRequestDTO(@ValidProduto String id, @Min(1) Integer quantidade) {}
}

