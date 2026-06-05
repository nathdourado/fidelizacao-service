package br.com.bakeflow.bakeflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bakeflow.bakeflow.model.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("""
        SELECT c FROM Cliente c
        LEFT JOIN FETCH c.endereco
    """)
    List<Cliente> findAllWithEndereco();


}