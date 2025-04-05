package br.com.guilherme.ecommerce_manager_api.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends DefaultException {

    protected NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public static NotFoundException ofProduct(String id) {
        return new NotFoundException(String.format("Produto não encontrado - id: %s", id));
    }

    public static NotFoundException ofPedido(Long id) {
        return new NotFoundException(String.format("Pedido não encontrado - id: %d", id));
    }
}
