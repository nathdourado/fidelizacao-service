package br.com.bakeflow.bakeflow.service;

import br.com.bakeflow.bakeflow.client.NotificacaoClient;
import br.com.bakeflow.bakeflow.model.Estoque;
import br.com.bakeflow.bakeflow.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstoqueService {

    private static final int QUANTIDADE_MINIMA_ALERTA = 5;

    private final EstoqueRepository repository;
    private final NotificacaoClient notificacaoClient;

    public EstoqueService(EstoqueRepository repository, NotificacaoClient notificacaoClient) {
        this.repository = repository;
        this.notificacaoClient = notificacaoClient;
    }

    public List<Estoque> findAllWithProduto() {
        return repository.findAllWithProduto();
    }

    public Estoque findById(Long id) {
        return repository.findById(id).orElse(null);
    }


    public Estoque buscarPorProduto(Long idProduto) {
        return repository.findByProdutoIdProduto(idProduto).orElse(null);
    }

    public void save(Estoque estoque) {
        Estoque estoqueSalvo = repository.save(estoque);
        enviarAlertasDeEstoque(estoqueSalvo);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void enviarAlertasDeEstoque(Estoque estoque) {
        if (estoque == null || estoque.getQuantidade() == null) {
            return;
        }

        if (estoque.getQuantidade() <= 0) {
            notificacaoClient.enviarProdutoPrecisaRepor(estoque);
        } else if (estoque.getQuantidade() <= QUANTIDADE_MINIMA_ALERTA) {
            notificacaoClient.enviarEstoqueBaixo(estoque, QUANTIDADE_MINIMA_ALERTA);
        }
    }

}