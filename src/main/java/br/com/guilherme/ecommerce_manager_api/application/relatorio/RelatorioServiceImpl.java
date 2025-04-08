package br.com.guilherme.ecommerce_manager_api.application.relatorio;

import br.com.guilherme.ecommerce_manager_api.application.pedido.PedidoService;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.RelatorioRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.RelatorioResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatorioServiceImpl implements RelatorioService{

    private final PedidoService pedidoService;

    @Override
    public List<? extends RelatorioResponseDTO> generateReports(RelatorioRequestDTO request) {
        log.info("m=generateReports iniciando geração de relatorio, request:{}", request);
        var inicio = request.dataInicio();
        var fim = request.dataFim();

        return switch (request.tipo()) {
            case TOP_CLIENTES -> pedidoService.findTopClients(inicio, fim, PageRequest.of(0, 5));
            case TICKET_MEDIO -> pedidoService.getTicketMedio(inicio, fim);
            case FATURAMENTO_MENSAL -> List.of(pedidoService.getTotalRevenueForCurrentMonth());
        };
    }
}
