package br.com.guilherme.ecommerce_manager_api.application.service;

import br.com.guilherme.ecommerce_manager_api.adapter.mapper.ProdutoMapper;
import br.com.guilherme.ecommerce_manager_api.domain.entity.ProdutoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoResponseDTO findById(String id) {
        return mapper.toResponse(this.repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofProduct(id)
                ));
    }

    @Transactional
    public ProdutoResponseDTO create(ProdutoRequestDTO dto) {
        log.info("Creating new produto: {}", dto);
        var saved = repository.save(mapper.toEntity(dto));
        return mapper.toResponse(saved);
    }

    public List<ProdutoResponseDTO> findAll() {
        log.info("Finding all produtos");
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoResponseDTO update(String id, ProdutoRequestDTO dto) {
        log.info("Updating produto: {}", dto);
        ProdutoEntity existing = this.findEntityById(id);

        mapper.updateEntityFromDto(dto, existing);
        var updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void delete(String id) {
        log.info("Deleting produto");
        var entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        repository.delete(entity);
    }

    public ProdutoEntity findEntityById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofProduct(id)
                );
    }

    public void validate(String produtoId) {
        this.findById(produtoId);
    }
}
