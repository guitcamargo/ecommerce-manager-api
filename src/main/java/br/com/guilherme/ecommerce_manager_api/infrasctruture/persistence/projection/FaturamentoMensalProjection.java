package br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection;

import java.math.BigDecimal;

public interface FaturamentoMensalProjection {
    BigDecimal getTotal();
}
