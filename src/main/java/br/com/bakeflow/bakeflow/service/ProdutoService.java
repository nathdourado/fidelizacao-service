package br.com.bakeflow.bakeflow.service;

import br.com.bakeflow.bakeflow.model.Estoque;
import br.com.bakeflow.bakeflow.model.Produto;
import br.com.bakeflow.bakeflow.repository.EstoqueRepository;
import br.com.bakeflow.bakeflow.repository.Item_PedidoRepository;
import br.com.bakeflow.bakeflow.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final Item_PedidoRepository itemPedidoRepository;
    private final EstoqueRepository estoqueRepository;

    public ProdutoService(ProdutoRepository repository,
                          Item_PedidoRepository itemPedidoRepository,
                          EstoqueRepository estoqueRepository) {
        this.repository = repository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Produto produto) {
        repository.save(produto);
    }


    public void delete(Long idProduto) {

        Produto p = repository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));


        if (itemPedidoRepository.existsByProduto(p)) {
            throw new RuntimeException(
                    "Este produto já foi utilizado em pedidos e não pode ser excluído."
            );
        }


        Optional<Estoque> estOpt = estoqueRepository.findByProdutoIdProduto(idProduto);

        if (estOpt.isPresent()) {
            Estoque est = estOpt.get();

            if (est.getQuantidade() > 0) {
                throw new RuntimeException(
                        "O estoque deste produto está acima de zero. Zere o estoque antes de excluir."
                );
            }

            estoqueRepository.delete(est);
        }

        repository.delete(p);
    }
}