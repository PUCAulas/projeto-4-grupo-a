package main.java.models;

import main.java.enums.StatusEmprestimo;
import main.java.models.itens.Emprestavel;
import main.java.models.itens.Item;

import java.util.ArrayList;
import java.util.List;

public class Estoque {

    private List<Item> itens;

    /**
     * Construtor padrao de estoque
     */
    public Estoque() {
        this.itens = new ArrayList<>();
    }

    /**
     * Construtor padrao de estoque, com lista de itens
     *
     * @param itens lista de itens
     */
    private Estoque(List<Item> itens) {
        this.itens = itens;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    /**
     * Adiciona novo item ao estoque
     *
     * @param item item a ser adicionado
     */
    public void addItem(Item item) {
        this.getItens().add(item);
    }

    /**
     * Remove item do estoque
     *
     * @param item item a ser removido
     */
    public void removeItem(Item item) {

        this.getItens().remove(item);
    }

    /**
     * Obtem a quantidade de itens que estao no estoque
     *
     * @return quantidade de itens
     */
    public int qtdEmEstoque() {

        return this.getItens().size();
    }



}
