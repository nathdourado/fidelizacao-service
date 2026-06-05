package br.com.bakeflow.bakeflow.dto;

import java.math.BigDecimal;

public class ProdutoRankingDto {

    private final Long produtoId;
    private final String nomeProduto;
    private final Long quantidadeVendida;
    private final BigDecimal valorAgregado;

    public ProdutoRankingDto(Long produtoId, String nomeProduto, Long quantidadeVendida, BigDecimal valorAgregado) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorAgregado = valorAgregado == null ? BigDecimal.ZERO : valorAgregado;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Long getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public BigDecimal getValorAgregado() {
        return valorAgregado;
    }
}