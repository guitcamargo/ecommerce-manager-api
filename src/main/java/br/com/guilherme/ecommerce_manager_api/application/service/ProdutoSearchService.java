package br.com.guilherme.ecommerce_manager_api.application.service;

import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoSearchFilterDTO;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.search.ProdutoSearchOPS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoSearchService {

    private ProdutoSearchOPS produtoSearchOPS;

    public List<ProdutoResponseDTO> findAllBy(ProdutoSearchFilterDTO filter) {
        log.info("Buscando produtos, filtro: {}", filter);
        return produtoSearchOPS.findAllBy(filter);
    }


}
