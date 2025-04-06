package br.com.guilherme.ecommerce_manager_api.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PedidoStatusException extends DefaultException {

    protected PedidoStatusException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public static PedidoStatusException ofStatus(String status) {
        return new PedidoStatusException(String.format("Pedido não pode ser pago, status atual: %s", status));
    }
}
