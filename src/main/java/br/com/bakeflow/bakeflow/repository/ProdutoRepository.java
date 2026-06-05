package br.com.bakeflow.bakeflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bakeflow.bakeflow.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    

}