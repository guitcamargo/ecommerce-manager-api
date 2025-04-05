package br.com.guilherme.ecommerce_manager_api.adapter.exception;

import lombok.Builder;

import java.util.List;

@Builder
public record ExceptionResponseError(int httpStatus, List<String> errors, Long timestamp) {
}
