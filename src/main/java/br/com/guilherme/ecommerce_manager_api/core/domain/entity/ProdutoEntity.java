package br.com.guilherme.ecommerce_manager_api.core.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = ProdutoEntity.TABLE_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProdutoEntity implements Serializable {

    public static final String TABLE_NAME = "produto";

    @Id
    @Column(name = "id_produto")
    private String id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private BigDecimal preco;

    private String categoria;

    private Long quantidadeEstoque;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

}