package br.com.guilherme.ecommerce_manager_api.dto.relatorio;

import java.time.LocalDateTime;

public record RelatorioRequestDTO(
        TipoRelatorioEnum tipo,
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {}