package main.java.services;

import main.java.enums.StatusClassificacao;
import main.java.models.Biblioteca;
import main.java.models.itens.Emprestavel;
import main.java.models.itens.Item;
import main.java.models.itens.Revista;
import main.java.models.itens.Tese;

import java.time.LocalDate;
import java.util.List;

public class ItemService {

    public Biblioteca biblioteca;
    public Item item;

    /**
     * Construtor padrao ItemService
     */
    public ItemService() {
    }

    /**
     * Construtor padrao ItemService, com Item e Biblioteca
     */
    public ItemService(Item item, Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.item = item;
    }

    /**
     * Construtor padrao ItemService, com Biblioteca
     */
    public ItemService(Biblioteca biblioteca) {
        this.item = null;
        this.biblioteca = biblioteca;
    }

    /**
     * Cria uma nova revista
     *
     * @param titulo              titulo da revista
     * @param dataPublicacao      data de publicacao da revista
     * @param statusClassificacao classificacao da revista
     * @param editora             editora da revista
     * @param edicao              edicao da revista
     * @param artigos             artigos da revista
     */
    public void criarRevista(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
                             String editora, String edicao, List<String> artigos) {
        Revista revista = (Revista) item;

        revista.setTitulo(titulo);
        revista.setDataPublicacao(dataPublicacao);
        revista.setStatusClassificacao(statusClassificacao);
        revista.setEditora(editora);
        revista.setEdicao(edicao);
        revista.setArtigos(artigos);

        biblioteca.getEstoque().addItem(revista);
    }

    /**
     * Cria uma nova tese
     *
     * @param titulo              titulo da tese
     * @param dataPublicacao      date de publicacao da tese
     * @param statusClassificacao classificacao da tese
     * @param autor               autor da tese
     * @param orientador          orientador da tese
     * @param dataDefesa          data de defesa da tese
     * @param capitulos           capitulos da tese
     */
    public void criarTese(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
                          String autor, String orientador, LocalDate dataDefesa, List<String> capitulos) {
        Tese tese = (Tese) item;

        tese.setTitulo(titulo);
        tese.setDataPublicacao(dataPublicacao);
        tese.setStatusClassificacao(statusClassificacao);
        tese.setAutor(autor);
        tese.setOrientador(orientador);
        tese.setDataDefesa(dataDefesa);
        tese.setCapitulos(capitulos);

        biblioteca.getEstoque().addItem(tese);
    }

    /**
     * Atualiza informacoes de uma revista
     *
     * @param titulo            titulo da revista
     * @param novaData          data de publicacao da revista
     * @param novaClassificacao classificacao da revista
     * @param editora           editora da revista
     * @param edicao            edicao da revista
     * @param artigos           artigos da revista
     */
    public void atualizarRevista(String titulo, LocalDate novaData, StatusClassificacao novaClassificacao,
                                 String edicao, String editora, List<String> artigos) {

        ((Revista) item).setTitulo(titulo);
        ((Revista) item).setDataPublicacao(novaData);
        ((Revista) item).setStatusClassificacao(novaClassificacao);
        ((Revista) item).setEditora(editora);
        ((Revista) item).setEdicao(edicao);
        ((Revista) item).setArtigos(artigos);
    }

    /**
     * Atualiza informacoes de uma tese
     *
     * @param titulo              titulo da tese
     * @param dataPublicacao      date de publicacao da tese
     * @param statusClassificacao classificacao da tese
     * @param autor               autor da tese
     * @param orientador          orientador da tese
     * @param dataDefesa          data de defesa da tese
     * @param capitulos           capitulos da tese
     */
    public void atualizarTese(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
                              String autor, String orientador, LocalDate dataDefesa, List<String> capitulos) {

        ((Tese) item).setTitulo(titulo);
        ((Tese) item).setDataPublicacao(dataPublicacao);
        ((Tese) item).setStatusClassificacao(statusClassificacao);
        ((Tese) item).setAutor(autor);
        ((Tese) item).setOrientador(orientador);
        ((Tese) item).setDataDefesa(dataDefesa);
        ((Tese) item).setCapitulos(capitulos);
    }

    /**
     * Delete um item
     *
     * @param item item de referencia
     */
    public void deletar(Item item) {

        biblioteca.getEstoque().getItens().remove(item);
    }

    /**
     * Lista itens nao emprestaveis do estoque
     */
    public void listar() {
        List<Item> itens = biblioteca.getEstoque().getItens();

        if (itens != null) {
            for (Item i : itens) {
                if(!(i instanceof Emprestavel)) {
                System.out.println(i);
                }
            }
        } else {
            System.out.println("Nenhum item encontrado no estoque.");
        }
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
