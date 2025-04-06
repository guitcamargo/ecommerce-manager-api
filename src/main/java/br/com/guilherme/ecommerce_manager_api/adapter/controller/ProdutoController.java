package br.com.guilherme.ecommerce_manager_api.adapter.controller;

import br.com.guilherme.ecommerce_manager_api.application.service.ProdutoSearchService;
import br.com.guilherme.ecommerce_manager_api.application.service.ProdutoService;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoSearchFilterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoSearchService produtoSearchService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Buscar produtos", description = "Lista todos os produtos / possível aplicar filtros")
    public List<ProdutoResponseDTO> buscarProdutos(@Valid ProdutoSearchFilterDTO filter) {
        return produtoSearchService.findAllBy(filter);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Busca um produto", description = "Busca um produto no sistema através do ID")
    public ProdutoResponseDTO findById(@PathVariable String id) {
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar produto", description = "Cria um novo produto no sistema")
    public ProdutoResponseDTO create(@RequestBody @Valid ProdutoRequestDTO dto) {
        return service.create(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza produto", description = "Atualiza um produto no sistema")
    public ProdutoResponseDTO update(@PathVariable String id,
                                     @RequestBody @Valid ProdutoRequestDTO dto) {
        return service.update(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}