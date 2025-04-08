package br.com.guilherme.ecommerce_manager_api.dto.relatorio;

import java.math.BigDecimal;

public record FaturamentoMensalResponseDTO(BigDecimal totalFaturado) implements RelatorioResponseDTO {
}
