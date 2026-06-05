package br.com.bakeflow.bakeflow.event;

import br.com.bakeflow.bakeflow.client.FidelizacaoClient;
import br.com.bakeflow.bakeflow.client.NotificacaoClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FidelizacaoListener {

    private final FidelizacaoClient fidelizacaoClient;
    private final NotificacaoClient notificacaoClient;

    public FidelizacaoListener(FidelizacaoClient fidelizacaoClient, NotificacaoClient notificacaoClient) {
        this.fidelizacaoClient = fidelizacaoClient;
        this.notificacaoClient = notificacaoClient;
    }

    @EventListener
    public void aoFinalizarPedido(PedidoFinalizadoEvent event) {
        fidelizacaoClient.enviarPedidoFinalizado(event);
        notificacaoClient.enviarPedidoFinalizado(event);
    }
}