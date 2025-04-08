package br.com.guilherme.ecommerce_manager_api.dto.relatorio;

import java.math.BigDecimal;

public record TicketMedioResponseDTO(Long idUsuario, String nome, BigDecimal ticketMedio) implements RelatorioResponseDTO {
}
