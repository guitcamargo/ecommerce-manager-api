package br.com.guilherme.ecommerce_manager_api.application.service;

import br.com.guilherme.ecommerce_manager_api.adapter.mapper.PedidoMapper;
import br.com.guilherme.ecommerce_manager_api.domain.document.ProdutoDocument;
import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.EstoqueInsuficienteException;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoStatusException;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.message.KafkaProducerService;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.repository.PedidoRepository;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.repository.ProdutoSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final ProdutoService produtoService;
    private final ProdutoSearchRepository produtoSearchRepository;
    private final KafkaProducerService kafkaService;

    public PedidoResponseDTO create(PedidoRequestDTO pedido) {
        log.info("Criando novo pedido");
        var entity = mapper.toEntity(pedido, produtoService);
        try {
            validateItemsStock(entity);
            return mapper.toResponse(repository.save(entity));
        } catch (EstoqueInsuficienteException e) {
            log.error("Erro ao criar pedido, msg: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void processPayment(Long pedidoId) {
        log.info("Processando pagamento para o pedido {}", pedidoId);
        var pedido = this.findEntityById(pedidoId);
        
        if (!pedido.isEligibleForPayment()) {
            throw PedidoStatusException.ofStatus(pedido.getStatus().name());
        }
        try {
            validateItemsStock(pedido);
            pedido.setPaymentStatus();
            repository.save(pedido);
            kafkaService.sendPedidoPaid(pedidoId);
        } catch (EstoqueInsuficienteException e) {
            log.error("Erro ao processar pagamento. {}", e.getMessage(), e);
            throw e;
        }
    }

    private PedidoEntity findEntityById(Long pedidoId){
        return repository.findById(pedidoId).orElseThrow(
                () -> NotFoundException.ofPedido(pedidoId)
        );
    }

    private void validateItemsStock(PedidoEntity entity) {
        entity.getItems().stream()
                .filter(item -> {
                    var id = item.getProduto().getId();
                    var quantidadeRequisitada = Long.valueOf(item.getQuantidade());

                    var estoqueAtual = produtoSearchRepository.findById(id)
                            .map(ProdutoDocument::getQuantidadeEstoque)
                            .orElse(0L);

                    return estoqueAtual < quantidadeRequisitada;
                })
                .map(item -> item.getProduto().getId())
                .reduce((a, b) -> a + ", " + b)
                .ifPresent(ids -> {
                    log.warn("Estoque insuficiente para os produtos: {}", ids);
                    throw EstoqueInsuficienteException.ofProdutos(ids);
                });
    }

    @Transactional
    public void processPosPayment(Long pedidoId) {
        var pedido = this.repository.findById(pedidoId)
                .orElseThrow(() -> NotFoundException.ofPedido(pedidoId));

        pedido.getItems().forEach(item ->
                produtoService.updateStock(item.getProduto().getId(), item.getQuantidade())
        );

    }
}