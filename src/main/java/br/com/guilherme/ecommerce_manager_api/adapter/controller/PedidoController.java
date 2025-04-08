package br.com.guilherme.ecommerce_manager_api.adapter.controller;

import br.com.guilherme.ecommerce_manager_api.application.service.PedidoService;
import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoCanceladoException;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    private final PedidoService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido no sistema")
    @PreAuthorize("hasRole('USER')")
    public PedidoResponseDTO create(@Valid @RequestBody PedidoRequestDTO pedido) throws PedidoCanceladoException {
        return service.create(pedido);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}/pagamento")
    @Operation(summary = "Pagar pedido", description = "Realiza o pagamento de um pedido")
    @PreAuthorize("hasRole('USER')")
    public void payOrder(@PathVariable Long id) {
        service.processPayment(id);
    }
}

