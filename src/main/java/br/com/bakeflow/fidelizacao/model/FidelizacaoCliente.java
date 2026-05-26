package br.com.bakeflow.fidelizacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fidelizacao_cliente")
public class FidelizacaoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private String clienteNome;

    private Integer pontos = 0;

    private Integer beneficiosGerados = 0;

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public Integer getBeneficiosGerados() {
        return beneficiosGerados;
    }

    public void setBeneficiosGerados(Integer beneficiosGerados) {
        this.beneficiosGerados = beneficiosGerados;
    }

    public Integer getTotalPontosAcumulados() {
        int pontosAtuais = pontos == null ? 0 : pontos;
        int beneficios = beneficiosGerados == null ? 0 : beneficiosGerados;
        return pontosAtuais + (beneficios * 100);
    }

    public String getNivel() {
        int total = getTotalPontosAcumulados();

        if (total >= 300) {
            return "Ouro";
        }

        if (total >= 100) {
            return "Prata";
        }

        return "Bronze";
    }
}
