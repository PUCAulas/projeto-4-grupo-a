package main.java.models.itens;

import java.time.Duration;
import java.util.List;

public final class CD extends Emprestavel {

    private String artista;
    private Duration duracao;
    private List<String> faixas;

    /**
     * Construtor padrao de CD
     */
    public CD() {
        super();
    }

    /**
     * Lista as faixas de um CD
     *
     * @return lista de faixas em um CD
     */
    public String listarFaixas() {
        StringBuilder faixas = new StringBuilder();
        for (String faixa : this.getFaixas()) {
            faixas.append(faixa);
            faixas.append("\n");
        }
        return faixas.toString();
    }

    /**
     * Imprime informacos do CD
     *
     * @return informacoes do CD
     */
    @Override
    public String toString() {
        long segundos = this.getDuracao().getSeconds();
        int duracaoEmInt = (int) segundos;

        return super.toString()
                + "\nArtista: "
                + this.getArtista()
                + "\nDuração (em segundos): "
                + duracaoEmInt
                + " s"
                + "\nTodas as faixas: \n"
                + listarFaixas();
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public List<String> getFaixas() {
        return faixas;
    }

    public void setFaixas(List<String> faixas) {
        this.faixas = faixas;
    }

}
