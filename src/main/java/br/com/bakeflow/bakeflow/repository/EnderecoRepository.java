package br.com.bakeflow.bakeflow.repository;

import br.com.bakeflow.bakeflow.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bakeflow.bakeflow.model.Endereco;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("""
        SELECT e FROM Estoque e
        LEFT JOIN FETCH e.produto
    """)
    List<Estoque> findAllWithProduto();


}