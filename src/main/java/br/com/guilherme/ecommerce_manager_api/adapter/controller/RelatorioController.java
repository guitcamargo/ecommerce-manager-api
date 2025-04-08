package br.com.guilherme.ecommerce_manager_api.adapter.controller;

import br.com.guilherme.ecommerce_manager_api.application.service.RelatorioService;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.RelatorioRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.RelatorioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Endpoint para relatório")
@SecurityRequirement(name = "bearerAuth")
public class RelatorioController {

    private final RelatorioService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Gerar relatório", description = "Gera relatórios de acordo com o tipo")
    public List<? extends RelatorioResponseDTO> generate(@RequestBody RelatorioRequestDTO request) {
        return service.generateReports(request);
    }
}
