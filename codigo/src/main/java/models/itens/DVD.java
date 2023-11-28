package main.java.models.itens;

import java.time.Duration;

public final class DVD extends Emprestavel {

    private String diretor;
    private Duration duracao;
    private String idioma;
    private String sinopse;
    private String genero;

    /**
     * Construtor padrao do DVD
     */
    public DVD() {
        super();
    }

    /**
     * Imprime informacos do DVD
     *
     * @return informacoes do DVD
     */
    @Override
    public String toString() {
        long segundos = this.getDuracao().getSeconds();
        int duracaoEmInt = (int) segundos;
        return super.toString()
                + "\nDiretor: "
                + this.getDiretor()
                + "\nDuração (em segundos): "
                + duracaoEmInt
                + " s"
                + "\nIdioma: "
                + this.getIdioma()
                + "\nGênero: "
                + this.getGenero()
                + "\nSinopse: \n"
                + this.getSinopse();
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
