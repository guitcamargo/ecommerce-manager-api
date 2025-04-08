package br.com.guilherme.ecommerce_manager_api.application.pedido;

import br.com.guilherme.ecommerce_manager_api.adapter.mapper.PedidoMapper;
import br.com.guilherme.ecommerce_manager_api.application.auth.AuthService;
import br.com.guilherme.ecommerce_manager_api.application.produto.ProdutoService;
import br.com.guilherme.ecommerce_manager_api.domain.document.ProdutoDocument;
import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.EstoqueInsuficienteException;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoCanceladoException;
import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoStatusException;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.FaturamentoMensalResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.TicketMedioResponseDTO;
import br.com.guilherme.ecommerce_manager_api.dto.relatorio.TopClientesResponseDTO;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.message.KafkaProducerService;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.PedidoRepository;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.ProdutoSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final ProdutoService produtoService;
    private final ProdutoSearchRepository produtoSearchRepository;
    private final KafkaProducerService kafkaService;
    private final AuthService authService;

    @Override
    @Transactional(noRollbackFor = PedidoCanceladoException.class)
    public PedidoResponseDTO create(PedidoRequestDTO pedido) throws PedidoCanceladoException {
        log.info("m=create Criando novo pedido, pedido={}", pedido);
        var entity = mapper.toEntity(pedido, produtoService, authService);
        try {
            validateItemsStock(entity);
            return mapper.toResponse(repository.save(entity));
        } catch (EstoqueInsuficienteException e) {
            log.error("m=create Erro ao criar pedido, msg: {}", e.getMessage(), e);
            entity.setCancelledStatus();
            repository.save(entity);
            throw new PedidoCanceladoException("Pedido Cancelado - "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public void processPayment(Long pedidoId) {
        log.info("m=processPayment Processando pagamento para o pedido {}", pedidoId);
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
            log.error("m=processPayment Erro ao processar pagamento. {}", e.getMessage(), e);
            throw e;
        }
    }

    private PedidoEntity findEntityById(Long pedidoId){
        log.info("m=findEntityById pedidoId={}", pedidoId);
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
                    log.warn("m=validateItemsStock Estoque insuficiente para os produtos: {}", ids);
                    throw EstoqueInsuficienteException.ofProdutos(ids);
                });
    }

    @Override
    @Transactional
    public void processPosPayment(Long pedidoId) {
        log.info("m=processPosPayment atualizando estoque, pedidoId:{}", pedidoId);
        var pedido = this.repository.findById(pedidoId)
                .orElseThrow(() -> NotFoundException.ofPedido(pedidoId));

        pedido.getItems().forEach(item ->
                produtoService.updateStock(item.getProduto().getId(), item.getQuantidade())
        );

    }

    @Override
    public List<TopClientesResponseDTO> findTopClients(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return repository.findTopUsers(inicio, fim, PedidoEntity.PedidoStatusEnum.PAGO, pageable)
                .stream()
                .map(r ->
                        new TopClientesResponseDTO(r.getIdUsuario(), r.getNome(), r.getTotalPedido(), r.getValorTotal()))
                .toList();
    }

    @Override
    public List<TicketMedioResponseDTO> getTicketMedio(LocalDateTime inicio, LocalDateTime fim) {
        return repository.getTicketMedio(inicio, fim, PedidoEntity.PedidoStatusEnum.PAGO)
                .stream()
                .map(r ->
                        new TicketMedioResponseDTO(r.getIdUsuario(), r.getNome(), r.getTicketMedio()))
                .toList();
    }

    @Override
    public FaturamentoMensalResponseDTO getTotalRevenueForCurrentMonth() {
        var projection = repository.getTotalRevenueForCurrentMonth(PedidoEntity.PedidoStatusEnum.PAGO);
        return new FaturamentoMensalResponseDTO(projection.getTotal());
    }
}