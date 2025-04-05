package br.com.guilherme.ecommerce_manager_api.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class DefaultException extends RuntimeException {
    private final Long timestamp = System.currentTimeMillis();
    protected DefaultException(String message) {
        super(message);
    }
    public abstract HttpStatus getHttpStatus();
}
