package br.com.bakeflow.bakeflow.repository;

import br.com.bakeflow.bakeflow.dto.ProdutoRankingDto;
import br.com.bakeflow.bakeflow.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bakeflow.bakeflow.model.Item_Pedido;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Item_PedidoRepository extends JpaRepository<Item_Pedido, Long> {

    boolean existsByProduto(Produto produto);

    @Query("""
        SELECT new br.com.bakeflow.bakeflow.dto.ProdutoRankingDto(
            p.idProduto,
            p.nome,
            SUM(i.quantidade),
            SUM(i.valor)
        )
        FROM Item_Pedido i
        JOIN i.produto p
        GROUP BY p.idProduto, p.nome
        ORDER BY SUM(i.quantidade) DESC, SUM(i.valor) DESC
    """)
    List<ProdutoRankingDto> buscarRankingProdutosMaisVendidos();

}