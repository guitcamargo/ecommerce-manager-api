package br.com.guilherme.ecommerce_manager_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidProdutoValidator.class})
public @interface ValidProduto {

    String message() default "identificador do produto inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}