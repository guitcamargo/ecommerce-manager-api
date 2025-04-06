package br.com.guilherme.ecommerce_manager_api.infrasctruture.repository;

import br.com.guilherme.ecommerce_manager_api.domain.document.ProdutoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoSearchRepository extends ElasticsearchRepository<ProdutoDocument, String> {
}
