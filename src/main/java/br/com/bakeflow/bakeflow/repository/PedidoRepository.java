package br.com.bakeflow.bakeflow.repository;

import br.com.bakeflow.bakeflow.model.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    //ajuste para buscar outras tabelas
    @Query("""
        SELECT DISTINCT p FROM Pedido p
        LEFT JOIN FETCH p.cliente
        LEFT JOIN FETCH p.itens i
        LEFT JOIN FETCH i.produto
    """)
    List<Pedido> findAllWithClienteAndItens();

    boolean existsByClienteIdCliente(Long idCliente);

    @Query("""
    SELECT p FROM Pedido p
    LEFT JOIN FETCH p.cliente
    LEFT JOIN FETCH p.itens i
    LEFT JOIN FETCH i.produto
    WHERE p.idPedido = :id
""")
    Pedido findByIdFetch(Long id);

}