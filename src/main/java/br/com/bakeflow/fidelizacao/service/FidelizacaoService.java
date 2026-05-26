package br.com.bakeflow.fidelizacao.service;

import br.com.bakeflow.fidelizacao.dto.PedidoFinalizadoRequest;
import br.com.bakeflow.fidelizacao.model.FidelizacaoCliente;
import br.com.bakeflow.fidelizacao.model.HistoricoFidelizacao;
import br.com.bakeflow.fidelizacao.repository.FidelizacaoClienteRepository;
import br.com.bakeflow.fidelizacao.repository.HistoricoFidelizacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FidelizacaoService {

    private final FidelizacaoClienteRepository repository;
    private final HistoricoFidelizacaoRepository historicoRepository;

    public FidelizacaoService(FidelizacaoClienteRepository repository,
                              HistoricoFidelizacaoRepository historicoRepository) {
        this.repository = repository;
        this.historicoRepository = historicoRepository;
    }

    public List<FidelizacaoCliente> findAll() {
        return repository.findAll();
    }

    public List<HistoricoFidelizacao> findHistorico() {
        return historicoRepository.findAllByOrderByDataGeracaoDesc();
    }

    public long contarClientesFidelizados() {
        return repository.count();
    }

    public int somarBeneficiosGerados() {
        return repository.findAll()
                .stream()
                .mapToInt(f -> f.getBeneficiosGerados() == null ? 0 : f.getBeneficiosGerados())
                .sum();
    }

    public FidelizacaoCliente buscarClienteComMaisPontos() {
        return repository.findAll()
                .stream()
                .max((a, b) -> a.getTotalPontosAcumulados().compareTo(b.getTotalPontosAcumulados()))
                .orElse(null);
    }

    @Transactional
    public void processarPedidoFinalizado(PedidoFinalizadoRequest request) {
        if (historicoRepository.existsByPedidoId(request.getPedidoId())) {
            return;
        }

        int pontosGanhos = calcularPontos(request.getValorTotal());

        FidelizacaoCliente fidelizacao = repository
                .findByClienteId(request.getClienteId())
                .orElseGet(() -> criarFidelizacao(request));

        int novosPontos = fidelizacao.getPontos() + pontosGanhos;

        while (novosPontos >= 100) {
            fidelizacao.setBeneficiosGerados(fidelizacao.getBeneficiosGerados() + 1);
            novosPontos -= 100;
        }

        fidelizacao.setPontos(novosPontos);

        repository.save(fidelizacao);
        historicoRepository.save(criarHistorico(request, pontosGanhos));
    }

    private FidelizacaoCliente criarFidelizacao(PedidoFinalizadoRequest request) {
        FidelizacaoCliente novo = new FidelizacaoCliente();
        novo.setClienteId(request.getClienteId());
        novo.setClienteNome(request.getClienteNome());
        novo.setPontos(0);
        novo.setBeneficiosGerados(0);
        return novo;
    }

    private int calcularPontos(BigDecimal valorTotal) {
        int pontos = valorTotal.intValue();

        if (valorTotal.compareTo(new BigDecimal("150.00")) >= 0) {
            pontos += 20;
        }

        return pontos;
    }

    private HistoricoFidelizacao criarHistorico(PedidoFinalizadoRequest request, int pontosGanhos) {
        HistoricoFidelizacao historico = new HistoricoFidelizacao();
        historico.setPedidoId(request.getPedidoId());
        historico.setClienteId(request.getClienteId());
        historico.setClienteNome(request.getClienteNome());
        historico.setValorPedido(request.getValorTotal());
        historico.setPontosGerados(pontosGanhos);
        historico.setDataGeracao(LocalDateTime.now());
        return historico;
    }
}
