package br.com.bakeflow.bakeflow.client;

import br.com.bakeflow.bakeflow.event.PedidoFinalizadoEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class FidelizacaoClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String fidelizacaoServiceUrl;

    public FidelizacaoClient(@Value("${bakeflow.fidelizacao-service.url:http://localhost:8081}") String fidelizacaoServiceUrl) {
        this.fidelizacaoServiceUrl = fidelizacaoServiceUrl;
    }

    public void enviarPedidoFinalizado(PedidoFinalizadoEvent event) {
        try {
            restTemplate.postForEntity(
                    fidelizacaoServiceUrl + "/api/fidelizacao/pedidos-finalizados",
                    event,
                    Void.class
            );
        } catch (RestClientException e) {
            System.err.println("Não foi possível comunicar com o microserviço de fidelização: " + e.getMessage());
        }
    }
}