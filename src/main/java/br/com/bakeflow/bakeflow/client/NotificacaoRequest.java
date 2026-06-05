package br.com.bakeflow.bakeflow.client;

public class NotificacaoRequest {

    private String tipo;
    private String titulo;
    private String mensagem;
    private String destinatarioEmail;
    private String origem;
    private String referenciaId;

    public NotificacaoRequest(String tipo, String titulo, String mensagem, String origem, String referenciaId) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.origem = origem;
        this.referenciaId = referenciaId;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getDestinatarioEmail() {
        return destinatarioEmail;
    }

    public String getOrigem() {
        return origem;
    }

    public String getReferenciaId() {
        return referenciaId;
    }
}