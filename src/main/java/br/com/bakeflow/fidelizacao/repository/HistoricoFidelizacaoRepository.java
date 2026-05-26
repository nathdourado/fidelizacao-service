package br.com.bakeflow.fidelizacao.repository;

import br.com.bakeflow.fidelizacao.model.HistoricoFidelizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoFidelizacaoRepository extends JpaRepository<HistoricoFidelizacao, Long> {

    boolean existsByPedidoId(Long pedidoId);

    List<HistoricoFidelizacao> findAllByOrderByDataGeracaoDesc();
}
