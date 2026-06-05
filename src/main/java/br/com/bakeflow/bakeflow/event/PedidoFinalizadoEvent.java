package br.com.bakeflow.bakeflow.event;

import java.math.BigDecimal;

public class PedidoFinalizadoEvent {

    private Long pedidoId;
    private Long clienteId;
    private String clienteNome;
    private BigDecimal valorTotal;

    public PedidoFinalizadoEvent(Long pedidoId, Long clienteId, String clienteNome, BigDecimal valorTotal) {
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.valorTotal = valorTotal;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}