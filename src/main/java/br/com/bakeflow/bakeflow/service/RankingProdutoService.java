package br.com.bakeflow.bakeflow.service;

import br.com.bakeflow.bakeflow.dto.ProdutoRankingDto;
import br.com.bakeflow.bakeflow.repository.Item_PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingProdutoService {

    private final Item_PedidoRepository itemPedidoRepository;

    public RankingProdutoService(Item_PedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<ProdutoRankingDto> buscarTop3ProdutosMaisVendidos() {
        return itemPedidoRepository.buscarRankingProdutosMaisVendidos()
                .stream()
                .limit(3)
                .toList();
    }
}