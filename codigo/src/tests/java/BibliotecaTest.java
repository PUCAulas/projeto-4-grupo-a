package tests.java;

import main.java.enums.FiltroPesquisa;
import main.java.enums.StatusClassificacao;
import main.java.enums.StatusEmprestimo;
import main.java.models.Biblioteca;
import main.java.models.Estoque;
import main.java.models.itens.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class BibliotecaTest {

    Estoque estoque;

    Biblioteca biblioteca;

    Emprestavel emprestavel = new Emprestavel();

    Revista revista = new Revista();


    @BeforeEach
    void setup() {
        estoque = new Estoque();
        biblioteca = Biblioteca.getINSTANCE();
        emprestavel.setTitulo("Don quixote");
        emprestavel.setDataPublicacao(LocalDate.of(1605, 1, 1));
        emprestavel.setStatusClassificacao(StatusClassificacao.MAIOR_DE_16);
        emprestavel.setStatusEmprestimo(StatusEmprestimo.EMPRESTADO);

        revista.setDataPublicacao(LocalDate.of(2005, 1, 1));
        revista.setTitulo("Playboy");
        revista.setStatusClassificacao(StatusClassificacao.MAIOR_DE_18);
        revista.setEditora("Abril");
        revista.setEdicao("1ª");
        List<String> artigos = new ArrayList<>();
        artigos.add("A festa do rei da playboy");
        artigos.add("O dia da Juliana");
        revista.setArtigos(artigos);

        // Fazendo a lista de itens
        List<Item> itens = new ArrayList<>();
        itens.add(emprestavel);
        itens.add(revista);

        // Adicionando lista de itens ao estoque
        estoque.setItens(itens);

        // Adicionando estoque a biblioteca
        biblioteca.setEstoque(estoque);
    }



    @Test
    public void choiceTest() {
        List<String> itens = new ArrayList<>();
        itens.add("Maria Eduarda");
        itens.add("Carlos");
        itens.add("Zambreta");
        itens.add("Bianca");

        InputStream sistemaEntradaOriginal = System.in;

        String input = "2";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String resultado = biblioteca.choice(itens);

        System.setIn(sistemaEntradaOriginal);

        assertEquals("Carlos", resultado);
    }

    @Test
    public void encontrarItemNullTest() {
        FiltroPesquisa filtro = FiltroPesquisa.TITULO;
        String pesquisa = "Item Inexistente";

        Item resultado = biblioteca.encontrarItem(filtro, pesquisa);

        assertNull(resultado);
    }


    @Test
    public void encontrarItemTest() {
        FiltroPesquisa filtro = FiltroPesquisa.TITULO;
        String pesquisa = "Don quixote";

        Item resultado = biblioteca.encontrarItem(filtro, pesquisa);


        assertEquals("Don quixote", resultado.getTitulo());
    }



}
