package br.com.guilherme.ecommerce_manager_api.domain.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProdutoDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String nome;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String descricao;

    @Field(type = FieldType.Double)
    private Double preco;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String categoria;

    @Field(type = FieldType.Integer)
    private Long quantidadeEstoque;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

}
