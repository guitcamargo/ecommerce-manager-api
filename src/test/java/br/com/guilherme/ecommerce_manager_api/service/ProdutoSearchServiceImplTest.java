package br.com.guilherme.ecommerce_manager_api.service;

import br.com.guilherme.ecommerce_manager_api.BaseTest;
import br.com.guilherme.ecommerce_manager_api.application.produtoSearch.ProdutoSearchServiceImpl;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoSearchFilterDTO;
import br.com.guilherme.ecommerce_manager_api.factory.ProdutoFactory;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.search.ProdutoSearchOPS;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ProdutoSearchServiceImplTest extends BaseTest {

    @InjectMocks
    private ProdutoSearchServiceImpl service;

    @Mock
    private ProdutoSearchOPS produtoSearchOPS;

    @Test
    void shouldFindAllByFilter() {
        ProdutoSearchFilterDTO filter =  ProdutoSearchFilterDTO.builder().build(); //filtro vazio.
        List<ProdutoResponseDTO> expected = List.of(ProdutoFactory.createResponse("id-prod"));

        when(produtoSearchOPS.findAllBy(filter)).thenReturn(expected);

        List<ProdutoResponseDTO> result = service.findAllBy(filter);

        assertThat(result).isEqualTo(expected);
    }
}