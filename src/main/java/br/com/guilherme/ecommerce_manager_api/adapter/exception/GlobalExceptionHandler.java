package br.com.guilherme.ecommerce_manager_api.adapter.exception;

import br.com.guilherme.ecommerce_manager_api.domain.exception.DefaultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionResponseError> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("m=handleMissingRequestHeaderException, msg=Header not specified, header={}, ex", ex.getHeaderName(), ex);
        var exception = ExceptionResponseError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errors(List.of("Header not specified"))
                .timestamp(System.currentTimeMillis())
                .build();
        return ResponseEntity.status(exception.httpStatus()).body(exception);
    }

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ExceptionResponseError> handleDefaultException(DefaultException ex) {
        log.error("m=handleDefaultException, msg={}, ex", ex.getMessage(), ex);
        var httpStatus = ex.getHttpStatus();
        var exception = ExceptionResponseError.builder()
                .httpStatus(httpStatus.value())
                .errors(List.of(ex.getMessage()))
                .timestamp(ex.getTimestamp())
                .build();
        return ResponseEntity.status(exception.httpStatus()).body(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseError> handleValidation(MethodArgumentNotValidException ex) {
        log.error("m=handleValidation, ex={}", ex.getMessage());

        List<String> mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        var response = ExceptionResponseError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errors(mensagens)
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
