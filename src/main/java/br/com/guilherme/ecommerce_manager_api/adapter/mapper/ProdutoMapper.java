package br.com.guilherme.ecommerce_manager_api.adapter.mapper;

import br.com.guilherme.ecommerce_manager_api.domain.entity.ProdutoEntity;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.produto.ProdutoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {

    ProdutoEntity toEntity(ProdutoRequestDTO dto);

    ProdutoResponseDTO toResponse(ProdutoEntity entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    void updateEntityFromDto(ProdutoRequestDTO dto, @MappingTarget ProdutoEntity entity);
}