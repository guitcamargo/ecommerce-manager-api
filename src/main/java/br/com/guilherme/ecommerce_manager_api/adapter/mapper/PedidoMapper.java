package br.com.guilherme.ecommerce_manager_api.adapter.mapper;


import br.com.guilherme.ecommerce_manager_api.application.service.AuthService;
import br.com.guilherme.ecommerce_manager_api.application.service.ProdutoService;
import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoEntity;
import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoItemEntity;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoResponseDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {PedidoItemMapper.class, ProdutoMapper.class})
public interface PedidoMapper {


    @Mapping(source = "items", target = "items")
    @Mapping(source = "preco", target = "valorTotal")
    PedidoResponseDTO toResponse(PedidoEntity entity);

    @Named("toEntityWithService")
    default PedidoEntity toEntity(PedidoRequestDTO dto, @Context ProdutoService service, @Context AuthService authService) {
        var itemsEntity = dto.items().stream()
                .map(item -> {
                    var produto = service.findEntityById(item.id());
                    return PedidoItemEntity.builder()
                            .produto(produto)
                            .quantidade(item.quantidade())
                            .preco(produto.getPreco())
                            .build();
                })
                .toList();

        var valorTotal = itemsEntity.stream()
                .map(item -> item.getPreco()
                        .multiply(BigDecimal.valueOf(item.getQuantidade()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var usuario = authService.extractUserLogged();

        var pedido = PedidoEntity.builder()
                .usuario(usuario)
                .status(PedidoEntity.PedidoStatusEnum.PENDENTE)
                .preco(valorTotal)
                .build();

        pedido.setItems(itemsEntity);
        itemsEntity.forEach(item -> item.setPedido(pedido));
        return pedido;
    }

}
