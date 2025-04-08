package br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository;

import br.com.guilherme.ecommerce_manager_api.domain.entity.PedidoEntity;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection.FaturamentoMensalProjection;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection.TicketMedioProjection;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.projection.TopClientesProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    @Query("""
    SELECT p.usuario.id AS idUsuario, p.usuario.nome AS nome,
        COUNT(p.id) AS totalPedido, SUM(p.preco) AS valorTotal
        FROM PedidoEntity p
            WHERE (:inicio IS NULL OR p.dataCriacao >= :inicio)
            AND (:fim IS NULL OR p.dataCriacao <= :fim)
            AND p.status=:status
    GROUP BY p.usuario.id, p.usuario.nome
        ORDER BY totalPedido DESC
    """)
    List<TopClientesProjection> findTopUsers(LocalDateTime inicio, LocalDateTime fim,
                                             PedidoEntity.PedidoStatusEnum status,
                                             Pageable pageable);

    @Query("""
        SELECT p.usuario.id as idUsuario,
            p.usuario.nome as nome,
            AVG(p.preco) as ticketMedio
        FROM PedidoEntity p
            WHERE (:inicio IS NULL OR p.dataCriacao >= :inicio)
              AND (:fim IS NULL OR p.dataCriacao <= :fim)
              AND p.status=:status
        GROUP BY p.usuario.id, p.usuario.nome
    """)
    List<TicketMedioProjection> getTicketMedio(LocalDateTime inicio,
                                               LocalDateTime fim,
                                               PedidoEntity.PedidoStatusEnum status);

    @Query("""
        SELECT SUM(p.preco) as total
        FROM PedidoEntity p
        WHERE MONTH(p.dataCriacao) = MONTH(CURRENT_DATE)
          AND YEAR(p.dataCriacao) = YEAR(CURRENT_DATE)
          AND p.status=:status
    """)
    FaturamentoMensalProjection getTotalRevenueForCurrentMonth(PedidoEntity.PedidoStatusEnum status);

}