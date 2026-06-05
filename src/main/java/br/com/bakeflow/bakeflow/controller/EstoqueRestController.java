package br.com.bakeflow.bakeflow.controller;

import br.com.bakeflow.bakeflow.model.Estoque;
import br.com.bakeflow.bakeflow.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueRestController {

    private final EstoqueService service;

    public EstoqueRestController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<Integer> buscarQtd(@PathVariable Long idProduto) {
        Estoque e = service.buscarPorProduto(idProduto);
        return ResponseEntity.ok(e != null ? e.getQuantidade() : 0);
    }
}