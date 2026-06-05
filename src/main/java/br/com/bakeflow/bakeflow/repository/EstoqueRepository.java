package br.com.bakeflow.bakeflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bakeflow.bakeflow.model.Estoque;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByProdutoIdProduto(Long idProduto);
    @Query("SELECT e FROM Estoque e LEFT JOIN FETCH e.produto")
    List<Estoque> findAllWithProduto();

}