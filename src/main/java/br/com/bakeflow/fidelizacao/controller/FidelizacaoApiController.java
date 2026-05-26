package br.com.bakeflow.fidelizacao.controller;

import br.com.bakeflow.fidelizacao.dto.PedidoFinalizadoRequest;
import br.com.bakeflow.fidelizacao.service.FidelizacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fidelizacao")
public class FidelizacaoApiController {

    private final FidelizacaoService fidelizacaoService;

    public FidelizacaoApiController(FidelizacaoService fidelizacaoService) {
        this.fidelizacaoService = fidelizacaoService;
    }

    @PostMapping("/pedidos-finalizados")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void processarPedidoFinalizado(@RequestBody PedidoFinalizadoRequest request) {
        fidelizacaoService.processarPedidoFinalizado(request);
    }
}
