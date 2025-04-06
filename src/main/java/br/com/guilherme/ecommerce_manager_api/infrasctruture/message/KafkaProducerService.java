package br.com.guilherme.ecommerce_manager_api.infrasctruture.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendPedidoPaid(Long pedidoId) {
        try {
            log.info("Enviando pedido pago ao kafka");
            kafkaTemplate.send("order.paid", pedidoId.toString());
        } catch (Exception e) {
            log.error("Erro ao enviar pedido pago ao kafka", e);
        }
    }
}