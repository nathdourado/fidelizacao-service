package br.com.bakeflow.bakeflow.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_endereco")
    private Long endereco_id;

    @Column(name = "cep")
    @NotEmpty(message = "Campo obrigatório")
    private String cep;

    @Column(name = "numero")
    @NotEmpty(message = "Campo obrigatório")
    private String numero;

    @Transient
    private String logradouro;

    @Transient
    private String bairro;

    @Transient
    private String cidade;

    @Transient
    private String estado;


    public Long getId() {
        return endereco_id;
    }

    public void setId(Long id) {
        this.endereco_id = id;
    }


    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}