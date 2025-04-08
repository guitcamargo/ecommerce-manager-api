package br.com.guilherme.ecommerce_manager_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = PedidoEntity.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PedidoEntity {

    public static final String TABLE_NAME = "pedido";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PedidoStatusEnum status;

    @Column(nullable = false)
    private BigDecimal preco;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoItemEntity> items;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    public boolean isEligibleForPayment() {
        return this.status == PedidoStatusEnum.PENDENTE;
    }

    public void setPaymentStatus() {
        this.status = PedidoStatusEnum.PAGO;
    }

    public void setCancelledStatus() {
        this.status = PedidoStatusEnum.CANCELADO;
    }

    public void setIniciatedStatus() {
        this.status = PedidoStatusEnum.PENDENTE;
    }

    public enum PedidoStatusEnum {
        PENDENTE, PAGO, CANCELADO
    }

}
