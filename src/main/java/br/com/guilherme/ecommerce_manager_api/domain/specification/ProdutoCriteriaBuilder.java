package br.com.guilherme.ecommerce_manager_api.domain.specification;

import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoSearchFilterDTO;
import org.springframework.data.elasticsearch.core.query.Criteria;

public class ProdutoCriteriaBuilder {

    public static Criteria build(ProdutoSearchFilterDTO filtro) {
        Criteria criteria = new Criteria();

        if (filtro.nome() != null && !filtro.nome().isBlank()) {
            criteria = criteria.and(new Criteria("nome").contains(filtro.nome()));
        }

        if (filtro.categoria() != null && !filtro.categoria().isBlank()) {
            criteria = criteria.and(new Criteria("categoria").is(filtro.categoria()));
        }

        if (filtro.precoMin() != null) {
            criteria = criteria.and(new Criteria("preco").greaterThanEqual(filtro.precoMin()));
        }

        if (filtro.precoMax() != null) {
            criteria = criteria.and(new Criteria("preco").lessThanEqual(filtro.precoMax()));
        }

        criteria = criteria.and(new Criteria("quantidadeEstoque").greaterThan(0));

        return criteria;
    }
}
