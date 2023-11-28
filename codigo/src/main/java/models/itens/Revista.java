package main.java.models.itens;

import java.util.List;

public final class Revista extends Item {

    private String editora;
    private String edicao;
    private List<String> artigos;

    /**
     * Construtor padrao da Revista
     */
    public Revista() {
        super();
    }

    /**
     * Lista artigos da revista
     * @return lista de artigos
     */
    public String listarArtigos() {
        StringBuilder artigos = new StringBuilder();
        for (String artigo : this.getArtigos()) {
            artigos.append(artigo);
            artigos.append("\n");
        }
        return artigos.toString();
    }

    /**
     * Imprime informacos da Revista
     *
     * @return informacoes da Revista
     */
    @Override
    public String toString() {
        return super.toString()
                + "\nEdição: "
                + this.getEdicao()
                + "\nEditora: "
                + this.getEditora()
                + "\nArtigos: \n"
                + this.listarArtigos();
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

    public List<String> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<String> artigos) {
        this.artigos = artigos;
    }
}
