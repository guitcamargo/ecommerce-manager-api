package br.com.guilherme.ecommerce_manager_api.factory;

import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoItemEntity;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection.FaturamentoMensalProjection;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection.TicketMedioProjection;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection.TopClientesProjection;

import java.math.BigDecimal;
import java.util.List;

public class PedidoFactory {

    public static PedidoRequestDTO createRequestOneItem() {
        return PedidoRequestDTO.builder()
                .items(List.of(
                        new PedidoRequestDTO.PedidoItemRequestDTO("prod-1", 1)
                )).build();
    }

    public static PedidoRequestDTO createRequestMultipleItems() {
        return PedidoRequestDTO.builder()
                .items(List.of(
                        new PedidoRequestDTO.PedidoItemRequestDTO("prod-1", 2),
                        new PedidoRequestDTO.PedidoItemRequestDTO("prod-2", 2)
                )).build();
    }


    public static PedidoEntity createEntityOneItem() {
        return PedidoEntity.builder()
                .id(1L)
                .usuario(UsuarioFactory.buildUser())
                .status(PedidoEntity.PedidoStatusEnum.PENDENTE)
                .items(List.of(
                        PedidoItemEntity.builder()
                                .produto(ProdutoFactory.createEntity("prod-1"))
                                .quantidade(1)
                                .build()
                ))
                .build();
    }

    public static PedidoEntity createEntityMultipleItems() {
        return PedidoEntity.builder()
                .id(1L)
                .usuario(UsuarioFactory.buildUser())
                .status(PedidoEntity.PedidoStatusEnum.PENDENTE)
                .items(List.of(
                        PedidoItemEntity.builder()
                                .produto(ProdutoFactory.createEntity("prod-1"))
                                .quantidade(2)
                                .build(),
                        PedidoItemEntity.builder()
                                .produto(ProdutoFactory.createEntity("prod-1"))
                                .quantidade(1)
                                .build()
                ))
                .build();
    }

    public static PedidoResponseDTO createResponse() {
        return PedidoResponseDTO.builder().id("1").status(PedidoEntity.PedidoStatusEnum.PENDENTE.name()).build();
    }

    public static PedidoEntity pedidoElegivelPagamento() {
        var pedido = new PedidoEntity();
        pedido.setId(1L);
        pedido.setStatus(PedidoEntity.PedidoStatusEnum.PENDENTE);
        pedido.setUsuario(UsuarioFactory.buildUser());
        pedido.setItems(List.of(
                new PedidoItemEntity(1L,ProdutoFactory.produtoComEstoque("1", 10L), 2, BigDecimal.TEN, pedido)
        ));
        return pedido;
    }

    public static PedidoEntity pedidoPago() {
        var pedido = pedidoElegivelPagamento();
        pedido.setStatus(PedidoEntity.PedidoStatusEnum.PAGO);
        return pedido;
    }

    public static TopClientesProjection topClienteProjection() {
        return new TopClientesProjection() {
            public Long getIdUsuario() { return 1L; }
            public String getNome() { return "User Test"; }
            public Long getTotalPedido() { return 5L; }
            public BigDecimal getValorTotal() { return BigDecimal.valueOf(500.00); }
        };
    }

    public static TicketMedioProjection ticketMedioProjection() {
        return new TicketMedioProjection() {
            public Long getIdUsuario() { return 1L; }
            public String getNome() { return "User Test"; }
            public BigDecimal getTicketMedio() { return BigDecimal.valueOf(150.00); }
        };
    }

    public static FaturamentoMensalProjection totalRevenueForCurrentMonth() {
        return new FaturamentoMensalProjection() {
            public BigDecimal getTotal() { return BigDecimal.valueOf(500.00); }
        };
    }
}
