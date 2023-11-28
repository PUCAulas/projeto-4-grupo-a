package main.java.models.itens;

import main.java.enums.StatusClassificacao;
import main.java.utils.DataUtil;

import java.time.LocalDate;

public class Item {

    private static int PROX_ID = 0;
    protected int id;
    private String titulo;
    private LocalDate dataPublicacao;
    private StatusClassificacao statusClassificacao;

    /**
     * Construtor padrao do Item
     */
    public Item() {
        this.id = PROX_ID++;
    }

    /**
     * Imprime informacos do Item
     *
     * @return informacoes do Item
     */
    @Override
    public String toString() {
        return "\nDados do item:"
                + "\nId: "
                + this.getId()
                + "\nTítulo: "
                + this.getTitulo()
                + "\nData de publicação: "
                + this.getDataPublicacao().format(DataUtil.fmt)
                + "\nClassificação indicativa: "
                + this.getStatusClassificacao().getClassificacao();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public StatusClassificacao getStatusClassificacao() {
        return statusClassificacao;
    }

    public void setStatusClassificacao(StatusClassificacao statusClassificacao) {
        this.statusClassificacao = statusClassificacao;
    }
}
