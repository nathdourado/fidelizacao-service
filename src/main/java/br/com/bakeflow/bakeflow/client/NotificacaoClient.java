package br.com.bakeflow.bakeflow.client;

import br.com.bakeflow.bakeflow.event.PedidoFinalizadoEvent;
import br.com.bakeflow.bakeflow.model.Estoque;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacaoClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String notificacaoServiceUrl;

    public NotificacaoClient(@Value("${bakeflow.notificacao-service.url:http://localhost:8082}") String notificacaoServiceUrl) {
        this.notificacaoServiceUrl = notificacaoServiceUrl;
    }

    public void enviarPedidoFinalizado(PedidoFinalizadoEvent event) {
        enviar(new NotificacaoRequest(
                "PEDIDO_FINALIZADO",
                "Pedido finalizado",
                "O pedido #" + event.getPedidoId() + " do cliente " + event.getClienteNome()
                        + " foi finalizado no valor de R$ " + event.getValorTotal() + ".",
                "bakeflow-main",
                String.valueOf(event.getPedidoId())
        ));
    }

    public void enviarEstoqueBaixo(Estoque estoque, int quantidadeMinima) {
        if (estoque == null || estoque.getProduto() == null) {
            return;
        }

        enviar(new NotificacaoRequest(
                "ESTOQUE_BAIXO",
                "Estoque baixo",
                "O produto " + estoque.getProduto().getNome() + " está com apenas "
                        + estoque.getQuantidade() + " unidade(s). Quantidade mínima recomendada: "
                        + quantidadeMinima + ".",
                "bakeflow-main",
                String.valueOf(estoque.getProduto().getIdProduto())
        ));
    }

    public void enviarProdutoPrecisaRepor(Estoque estoque) {
        if (estoque == null || estoque.getProduto() == null) {
            return;
        }

        enviar(new NotificacaoRequest(
                "PRODUTO_REPOSICAO",
                "Produto precisa ser reposto",
                "O produto " + estoque.getProduto().getNome() + " chegou a "
                        + estoque.getQuantidade() + " unidade(s) e precisa ser reposto.",
                "bakeflow-main",
                String.valueOf(estoque.getProduto().getIdProduto())
        ));
    }

    private void enviar(NotificacaoRequest request) {
        try {
            restTemplate.postForEntity(
                    notificacaoServiceUrl + "/api/notificacoes/alertas",
                    request,
                    Void.class
            );
        } catch (RestClientException e) {
            System.err.println("Não foi possível comunicar com o microserviço de notificações: " + e.getMessage());
        }
    }
}