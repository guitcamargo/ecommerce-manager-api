package br.com.guilherme.ecommerce_manager_api.adapter.listener;

import br.com.guilherme.ecommerce_manager_api.application.pedido.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoPaidListener {

    private final PedidoService service;

    @KafkaListener(topics = "order.paid", groupId = "produto-consumer")
    public void processMessage(String idPedido) {
        log.info("Recebido evento de pagamento do pedido {}", idPedido);

        Long pedidoId = Long.parseLong(idPedido);
        service.processPosPayment(pedidoId);
    }
}
