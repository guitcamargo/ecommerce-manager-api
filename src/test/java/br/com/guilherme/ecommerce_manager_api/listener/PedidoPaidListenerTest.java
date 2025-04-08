package br.com.guilherme.ecommerce_manager_api.listener;

import br.com.guilherme.ecommerce_manager_api.adapter.listener.PedidoPaidListener;
import br.com.guilherme.ecommerce_manager_api.application.pedido.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PedidoPaidListenerTest {

    private PedidoService pedidoService;
    private PedidoPaidListener listener;

    @BeforeEach
    void setUp() {
        pedidoService = mock(PedidoService.class);
        listener = new PedidoPaidListener(pedidoService);
    }

    @Test
    void shouldCallProcessPosPayment_whenValidMessageReceived() {
        String message = "123";
        listener.processMessage(message);
        verify(pedidoService).processPosPayment(123L);
    }

    @Test
    void shouldThrowException_whenInvalidMessageReceived() {
        String invalidMessage = "not-a-number";
        assertThrows(NumberFormatException.class, () ->
                listener.processMessage(invalidMessage));
    }
}