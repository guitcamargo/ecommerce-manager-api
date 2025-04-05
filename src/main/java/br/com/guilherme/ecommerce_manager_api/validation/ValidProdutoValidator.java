package br.com.guilherme.ecommerce_manager_api.validation;

import br.com.guilherme.ecommerce_manager_api.application.service.ProdutoService;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ValidProdutoValidator implements ConstraintValidator<ValidProduto, String> {

    private final ProdutoService produtoService;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .filter(hash -> !hash.isBlank())
                .map(this::validateProduto)
                .orElse(false);
    }

    private boolean validateProduto(String produtoId) {
        try{
            produtoService.validate(produtoId);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }
    }
}
