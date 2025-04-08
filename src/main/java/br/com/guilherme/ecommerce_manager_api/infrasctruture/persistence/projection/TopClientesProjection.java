package br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection;

import java.math.BigDecimal;

public interface TopClientesProjection {
    Long getIdUsuario();
    String getNome();
    Long getTotalPedido();
    BigDecimal getValorTotal();
}
