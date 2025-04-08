package br.com.guilherme.ecommerce_manager_api.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EstoqueInsuficienteException extends DefaultException {

    protected EstoqueInsuficienteException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public static EstoqueInsuficienteException ofProdutos(String produtos) {
        return new EstoqueInsuficienteException(String.format("Pedido não pode ser processado, estoque insuficiente para os produtos %s:", produtos));
    }

    public static EstoqueInsuficienteException errorCreate() {
        return new EstoqueInsuficienteException(String.format("O seu pedido foi alterado para o status CANCELADO"));
    }
}
