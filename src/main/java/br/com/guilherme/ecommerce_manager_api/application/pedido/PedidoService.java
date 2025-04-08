package br.com.guilherme.ecommerce_manager_api.application.pedido;

import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoCanceladoException;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.FaturamentoMensalResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.TicketMedioResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.TopClientesResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    PedidoResponseDTO create(PedidoRequestDTO pedido) throws PedidoCanceladoException;
    void processPayment(Long pedidoId);
    void processPosPayment(Long pedidoId);
    List<TopClientesResponseDTO> findTopClients(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
    List<TicketMedioResponseDTO> getTicketMedio(LocalDateTime inicio, LocalDateTime fim);
    FaturamentoMensalResponseDTO getTotalRevenueForCurrentMonth();
}
