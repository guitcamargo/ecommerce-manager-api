package br.com.guilherme.ecommerce_manager_api.adapter.mapper;


import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoItemEntity;
import br.com.guilherme.ecommerce_manager_api.dto.pedido.PedidoItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoItemMapper {

    @Mapping(source = "preco", target = "precoUnitario")
    PedidoItemResponseDTO toResponse(PedidoItemEntity entity);

}
