package br.com.guilherme.ecommerce_manager_api.service;


import br.com.guilherme.ecommerce_manager_api.application.pedido.PedidoService;
import br.com.guilherme.ecommerce_manager_api.application.relatorio.RelatorioServiceImpl;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RelatorioServiceImplTest {

    @InjectMocks
    private RelatorioServiceImpl relatorioService;

    @Mock
    private PedidoService pedidoService;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inicio = LocalDateTime.of(2024, 1, 1, 0, 0);
        fim = LocalDateTime.of(2024, 12, 31, 23, 59);
    }

    @Test
    void shouldGenerateTopClientsReport() {
        var request = new RelatorioRequestDTO(TipoRelatorioEnum.TOP_CLIENTES, inicio, fim);

        var mockResult = List.of(new TopClientesResponseDTO(1L, "Guilherme", 5L, BigDecimal.valueOf(250.0)));
        when(pedidoService.findTopClients(inicio, fim, PageRequest.of(0, 5))).thenReturn(mockResult);

        var result = relatorioService.generateReports(request);

        assertEquals(1, result.size());
        assertEquals(mockResult, result);
        verify(pedidoService).findTopClients(inicio, fim, PageRequest.of(0, 5));
    }

    @Test
    void shouldGenerateTicketMedioReport() {
        var request = new RelatorioRequestDTO(TipoRelatorioEnum.TICKET_MEDIO, inicio, fim);

        var mockResult = List.of(new TicketMedioResponseDTO(1L, "Guilherme", BigDecimal.valueOf(125.0)));
        when(pedidoService.getTicketMedio(inicio, fim)).thenReturn(mockResult);

        var result = relatorioService.generateReports(request);

        assertEquals(1, result.size());
        assertEquals(mockResult, result);
        verify(pedidoService).getTicketMedio(inicio, fim);
    }

    @Test
    void shouldGenerateFaturamentoMensalReport() {
        var request = new RelatorioRequestDTO(TipoRelatorioEnum.FATURAMENTO_MENSAL, inicio, fim);

        var mockDto = new FaturamentoMensalResponseDTO(BigDecimal.valueOf(1000.0));
        when(pedidoService.getTotalRevenueForCurrentMonth()).thenReturn(mockDto);

        var result = relatorioService.generateReports(request);

        assertEquals(1, result.size());
        assertEquals(mockDto, result.get(0));
        verify(pedidoService).getTotalRevenueForCurrentMonth();
    }
}