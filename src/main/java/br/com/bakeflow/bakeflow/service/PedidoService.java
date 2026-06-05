package br.com.bakeflow.bakeflow.service;

import br.com.bakeflow.bakeflow.model.*;
import br.com.bakeflow.bakeflow.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import br.com.bakeflow.bakeflow.event.PedidoFinalizadoEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;
    private final ApplicationEventPublisher eventPublisher;

  public PedidoService(PedidoRepository repository,
                     ProdutoService produtoService,
                     EstoqueService estoqueService,
                     ApplicationEventPublisher eventPublisher) {
    this.repository = repository;
    this.produtoService = produtoService;
    this.estoqueService = estoqueService;
    this.eventPublisher = eventPublisher;
}

    public Pedido findById(Long id) {
        return repository.findByIdFetch(id);
    }

    public List<Pedido> findAll() {
        return repository.findAllWithClienteAndItens();
    }

    @Transactional
    public Pedido save(Pedido pedido) {

        BigDecimal total = BigDecimal.ZERO;

        for (Item_Pedido item : pedido.getItens()) {

            Produto p = produtoService.findById(item.getProduto().getIdProduto());
            item.setProduto(p);

            Estoque est = estoqueService.buscarPorProduto(p.getIdProduto());
            if (est == null || est.getQuantidade() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + p.getNome());
            }

            est.setQuantidade(est.getQuantidade() - item.getQuantidade());
            estoqueService.save(est);

            BigDecimal valor = p.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            item.setValor(valor);

            total = total.add(valor);

            item.setPedido(pedido);
        }

        pedido.setValorTotal(total);

        Pedido pedidoSalvo = repository.save(pedido);

        if ("FINALIZADO".equalsIgnoreCase(pedidoSalvo.getStatus())) {
            eventPublisher.publishEvent(new PedidoFinalizadoEvent(
                    pedidoSalvo.getIdPedido(),
                    pedidoSalvo.getCliente().getIdCliente(),
                    pedidoSalvo.getCliente().getNome(),
                    pedidoSalvo.getValorTotal()
            ));
        }

        return pedidoSalvo; 
    }

    public void delete(Long idPedido) {


        Pedido pedido = repository.findByIdFetch(idPedido);
        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado.");
        }

        for (Item_Pedido item : pedido.getItens()) {

            Estoque est = estoqueService.buscarPorProduto(item.getProduto().getIdProduto());

            if (est != null) {
                est.setQuantidade(est.getQuantidade() + item.getQuantidade());
                estoqueService.save(est);
            }
        }

        repository.delete(pedido);
    }

}