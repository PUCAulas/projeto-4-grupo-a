package main.java.models.itens;

import main.java.interfaces.AutorFiltro;

public final class Livro extends Emprestavel implements AutorFiltro {

    private String autor;
    private int numeroPaginas;
    private String editora;
    private String edicao;
    private String volume;
    private String idioma;
    private String sinopse;
    private String genero;

    /**
     * Construtor padrao de Livro
     */
    public Livro() {
        super();
    }

    /**
     * Imprime informacos do Livro
     *
     * @return informacoes do Livro
     */
    @Override
    public String toString() {
        return super.toString()
                + "\nAutor: "
                + this.getAutor()
                + "\nNúmero de páginas: "
                + this.getNumeroPaginas()
                + "\nEdição: "
                + this.getEdicao()
                + "\nEditora: "
                + this.getEditora()
                + "\nIdioma: "
                + this.getIdioma()
                + "\nGênero: "
                + this.getGenero()
                + "\nVolume: "
                + this.getVolume();
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
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
