package br.com.guilherme.ecommerce_manager_api.dto.relatorio;

import java.math.BigDecimal;

public record TopClientesResponseDTO(Long idUsuario, String nome, Long totalPedidos, BigDecimal valorTotal)
        implements RelatorioResponseDTO {}
