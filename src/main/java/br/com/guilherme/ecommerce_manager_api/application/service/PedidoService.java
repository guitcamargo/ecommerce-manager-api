package br.com.guilherme.ecommerce_manager_api.application.service;

import br.com.guilherme.ecommerce_manager_api.adapter.mapper.PedidoMapper;
import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.domain.exception.PedidoStatusException;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final ProdutoService produtoService;

    public PedidoResponseDTO create(PedidoRequestDTO pedido) {
        PedidoEntity entity = mapper.toEntity(pedido, produtoService);
        return mapper.toResponse(repository.save(entity));
    }

    public void processPayment(Long pedidoId) {
        var pedido = this.findEntityById(pedidoId);
        
        if (!pedido.isEligibleForPayment()) {
            throw PedidoStatusException.ofStatus(pedido.getStatus().name());
        }
        pedido.setPaymentStatus();
        repository.save(pedido);
    }

    private PedidoEntity findEntityById(Long pedidoId){
        return repository.findById(pedidoId).orElseThrow(
                () -> NotFoundException.ofPedido(pedidoId)
        );
    }

} 