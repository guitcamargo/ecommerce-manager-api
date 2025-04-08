package br.com.guilherme.ecommerce_manager_api.application.relatorio;

import br.com.guilherme.ecommerce_manager_api.dto.relatorio.RelatorioRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.RelatorioResponseDTO;

import java.util.List;

public interface RelatorioService {

    List<? extends RelatorioResponseDTO> generateReports(RelatorioRequestDTO request);
}
