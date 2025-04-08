package br.com.guilherme.ecommerce_manager_api.service;

import br.com.guilherme.ecommerce_manager_api.BaseTest;
import br.com.guilherme.ecommerce_manager_api.adapter.mapper.ProdutoMapper;
import br.com.guilherme.ecommerce_manager_api.application.produto.ProdutoServiceImpl;
import br.com.guilherme.ecommerce_manager_api.domain.entity.ProdutoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.factory.ProdutoFactory;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.ProdutoRepository;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.ProdutoSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceImplTest extends BaseTest {


    @InjectMocks
    private ProdutoServiceImpl service;

    @Mock
    private ProdutoRepository repository;

    @Mock
    private ProdutoMapper mapper;

    @Mock
    private ProdutoSearchRepository produtoSearchRepository;

    private final String ID = "prod-123";

    private ProdutoEntity produtoEntity;
    private ProdutoRequestDTO produtoRequest;
    private ProdutoResponseDTO produtoResponse;

    @BeforeEach
    void setUp() {
        produtoEntity = ProdutoFactory.createEntity(ID);
        produtoRequest = ProdutoFactory.createRequest();
        produtoResponse = ProdutoFactory.createResponse(ID);
    }

    @Test
    void shouldFindProductById() {
        when(repository.findById(ID)).thenReturn(Optional.of(produtoEntity));
        when(mapper.toResponse(produtoEntity)).thenReturn(produtoResponse);

        var result = service.findById(ID);

        assertNotNull(result);
        assertEquals(produtoResponse, result);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenProductNotFound() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(ID));
    }

    @Test
    void shouldCreateNewProduct() {
        when(mapper.toEntity(produtoRequest)).thenReturn(produtoEntity);
        when(repository.save(produtoEntity)).thenReturn(produtoEntity);
        when(mapper.toResponse(produtoEntity)).thenReturn(produtoResponse);

        ProdutoResponseDTO result = service.create(produtoRequest);

        assertNotNull(result);
        verify(produtoSearchRepository).save(any());
    }

    @Test
    void shouldUpdateProduct() {
        when(repository.findById(ID)).thenReturn(Optional.of(produtoEntity));
        doNothing().when(mapper).updateEntityFromDto(produtoRequest, produtoEntity);
        when(repository.save(produtoEntity)).thenReturn(produtoEntity);
        when(mapper.toResponse(produtoEntity)).thenReturn(produtoResponse);

        ProdutoResponseDTO result = service.update(ID, produtoRequest);

        assertEquals(produtoResponse, result);
        verify(produtoSearchRepository).save(any());
    }

    @Test
    void shouldDeleteProduct() {
        when(repository.findById(ID)).thenReturn(Optional.of(produtoEntity));

        service.delete(ID);

        verify(repository).delete(produtoEntity);
        verify(produtoSearchRepository).deleteById(ID);
    }

    @Test
    void shouldUpdateStockSuccessfully() {
        when(repository.findById(ID)).thenReturn(Optional.of(produtoEntity));
        when(repository.save(any())).thenReturn(produtoEntity);

        service.updateStock(ID, 2);

        assertEquals(8, produtoEntity.getQuantidadeEstoque());
        verify(produtoSearchRepository).save(any());
    }
}
