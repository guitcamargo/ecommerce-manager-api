package br.com.guilherme.ecommerce_manager_api.service;

import br.com.guilherme.ecommerce_manager_api.BaseTest;
import br.com.guilherme.ecommerce_manager_api.adapter.mapper.PedidoMapper;
import br.com.guilherme.ecommerce_manager_api.application.pedido.PedidoServiceImpl;
import br.com.guilherme.ecommerce_manager_api.application.produto.ProdutoService;
import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoCanceladoException;
import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoStatusException;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.factory.PedidoFactory;
import br.com.guilherme.ecommerce_manager_api.factory.ProdutoFactory;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.message.KafkaProducerService;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.PedidoRepository;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.ProdutoSearchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidoServiceImplTest extends BaseTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock
    private PedidoRepository repository;
    @Mock
    private PedidoMapper mapper;
    @Mock
    private ProdutoService produtoService;
    @Mock
    private ProdutoSearchRepository produtoSearchRepository;
    @Mock
    private KafkaProducerService kafkaService;

    @Test
    void shouldCreatePedidoSuccessfully() throws PedidoCanceladoException {
        PedidoRequestDTO request = PedidoFactory.createRequestOneItem();
        PedidoEntity entity = PedidoFactory.createEntityOneItem();
        PedidoResponseDTO response = PedidoFactory.createResponse();

        when(mapper.toEntity(eq(request), any(), any())).thenReturn(entity);
        when(produtoSearchRepository.findById(any()))
                .thenReturn(Optional.of(ProdutoFactory.createDocument()));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.create(request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("1");
        assertThat(result.status()).isEqualTo("PENDENTE");
        verify(repository).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenStockIsNotEnough() {
        PedidoRequestDTO request = PedidoFactory.createRequestMultipleItems();
        PedidoEntity entity = PedidoFactory.createEntityMultipleItems();

        when(mapper.toEntity(eq(request), any(), any())).thenReturn(entity);
        when(produtoSearchRepository.findById(any()))
                .thenReturn(Optional.of(ProdutoFactory.createDocument()));

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(PedidoCanceladoException.class)
                .hasMessageContaining("Pedido Cancelado");
    }

    @Test
    void shouldProcessPaymentWithSuccess() {
        var pedido = PedidoFactory.pedidoElegivelPagamento();
        when(repository.findById(1L)).thenReturn(Optional.of(pedido));
        when(produtoSearchRepository.findById(anyString())).thenReturn(Optional.of(ProdutoFactory.documentComEstoque("1", 10L)));

        service.processPayment(1L);

        assertEquals(PedidoEntity.PedidoStatusEnum.PAGO, pedido.getStatus());
        verify(kafkaService).sendPedidoPaid(1L);

    }

    @Test
    void shouldThrowExceptionWhenProcessingPaymentWithInvalidStatus() {
        var pedido = PedidoFactory.pedidoPago();
        when(repository.findById(1L)).thenReturn(Optional.of(pedido));

        Assertions.assertThrows(PedidoStatusException.class, () -> service.processPayment(1L));
    }

    @Test
    void shouldProcessPostPaymentAndUpdateStock() {
        var pedido = PedidoFactory.pedidoElegivelPagamento();
        when(repository.findById(1L)).thenReturn(Optional.of(pedido));

        service.processPosPayment(1L);

        verify(produtoService, atLeastOnce()).updateStock(anyString(), anyInt());
    }


    @Test
    void shouldReturnTopClients() {
        var result = List.of(PedidoFactory.topClienteProjection());
        when(repository.findTopUsers(any(), any(), any(), any())).thenReturn(result);

        var response = service.findTopClients(LocalDateTime.now().minusDays(10), LocalDateTime.now(), Pageable.ofSize(5));

        assertEquals(1, response.size());
        assertEquals("User Test", response.get(0).nome());
    }

    @Test
    void shouldReturnTicketMedio() {
        var result = List.of(PedidoFactory.ticketMedioProjection());
        when(repository.getTicketMedio(any(), any(), any())).thenReturn(result);

        var response = service.getTicketMedio(LocalDateTime.now().minusDays(10), LocalDateTime.now());

        assertEquals(1, response.size());
        assertEquals(BigDecimal.valueOf(150.00), response.get(0).ticketMedio());
    }

    @Test
    void shouldReturnTotalRevenueForCurrentMonth() {
        var projection = PedidoFactory.totalRevenueForCurrentMonth();
        when(repository.getTotalRevenueForCurrentMonth(PedidoEntity.PedidoStatusEnum.PAGO)).thenReturn(projection);

        var result = service.getTotalRevenueForCurrentMonth();

        assertEquals(BigDecimal.valueOf(500.00), result.totalFaturado());
    }
}