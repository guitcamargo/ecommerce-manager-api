package br.com.guilherme.ecommerce_manager_api.application.produto;

import br.com.guilherme.ecommerce_manager_api.adapter.mapper.ProdutoMapper;
import br.com.guilherme.ecommerce_manager_api.domain.entity.ProdutoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.ProdutoRepository;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.ProdutoSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final ProdutoSearchRepository produtoSearchRepository;

    @Override
    public ProdutoResponseDTO findById(String id) {
        log.info("m=findById Buscando produto com id {}", id);
        return mapper.toResponse(this.repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofProduct(id)
                ));
    }

    @Override
    @Transactional
    public ProdutoResponseDTO create(ProdutoRequestDTO dto) {
        log.info("m=create Creating new produto: {}", dto);
        var saved = repository.save(mapper.toEntity(dto));
        this.indexProduto(saved);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(String id, ProdutoRequestDTO dto) {
        log.info("m=update Updating produto: {}", dto);
        ProdutoEntity existing = this.findEntityById(id);

        mapper.updateEntityFromDto(dto, existing);
        this.indexProduto(existing);
        var updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("m=delete Deleting produto");
        var entity = repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofProduct(id));
        repository.delete(entity);
        produtoSearchRepository.deleteById(entity.getId());
    }

    @Override
    public ProdutoEntity findEntityById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> NotFoundException.ofProduct(id)
                );
    }

    @Override
    public void validate(String produtoId) {
        this.findById(produtoId);
    }

    private void indexProduto(ProdutoEntity produto) {
        log.info("m=indexProduto indexando produto elasticsearch {}", produto);
        produtoSearchRepository.save(mapper.toDocument(produto));
    }

    @Override
    @Transactional
    public void updateStock(String idProduto, int quantidade) {
        log.info("m=updateStock atualizando estoque do produto {}", idProduto);
        ProdutoEntity produto = this.findEntityById(idProduto);

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        repository.save(produto);
        indexProduto(produto);
    }
}
