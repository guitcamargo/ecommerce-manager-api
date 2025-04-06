package br.com.guilherme.ecommerce_manager_api.application.service;

import br.com.guilherme.ecommerce_manager_api.adapter.mapper.ProdutoMapper;
import br.com.guilherme.ecommerce_manager_api.domain.document.ProdutoDocument;
import br.com.guilherme.ecommerce_manager_api.domain.specification.ProdutoCriteriaBuilder;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoSearchFilterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ProdutoMapper mapper;

    public List<ProdutoResponseDTO> findAllBy(ProdutoSearchFilterDTO filter) {
        log.info("Buscando produtos, filtro: {}", filter);
        Criteria criteria = ProdutoCriteriaBuilder.build(filter);
        CriteriaQuery query = new CriteriaQuery(criteria);

        return elasticsearchOperations.search(query, ProdutoDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .map(mapper::documentToResponse)
                .toList();
    }
}
