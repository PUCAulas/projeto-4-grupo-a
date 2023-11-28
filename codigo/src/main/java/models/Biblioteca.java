package main.java.models;

import main.java.enums.FiltroPesquisa;
import main.java.interfaces.AutorFiltro;
import main.java.interfaces.Relatorio;
import main.java.models.itens.*;
import main.java.utils.InputScannerUtil;

import java.util.*;

public class Biblioteca implements Relatorio {

    private List<Usuario> usuarios;
    private Estoque estoque;

    /**
     * Construtor padrao da biblioteca
     */
    public Biblioteca() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Construtor padrao da biblioteca, com estoque
     *
     * @param estoque obj estoque
     */
    public Biblioteca(Estoque estoque) {
        this();
        this.estoque = estoque;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public void addUsuario(Usuario usuario) {
        this.getUsuarios().add(usuario);
    }

    public void removeUsuario(Usuario usuario) {
        this.getUsuarios().remove(usuario);
    }

    /**
     * Imprime o relatorio de um usuario
     *
     * @param usuario usuario de referencia
     * @return relatorio do usuario
     */
    public void imprimirRelatorioUsuario(Usuario usuario) {
        System.out.println("Relatório de usuario: ");
        System.out.println(usuario);
    }

    /**
     * Imprime relatorio dos itens
     */
    public void imprimirRelatorioItem() {
        List<Item> itemsEmprestaveis = this.getEstoque().getItens()
                .stream()
                .filter(item -> item instanceof Emprestavel)
                .sorted(Comparator.comparing(item -> item.getDataPublicacao().getYear()))
                .toList();

        System.out.println("Relatorio de itens:");
        itemsEmprestaveis.forEach(item -> System.out.println(item.toString()));
    }

    /**
     * Realiza pesquisa no estoque de acordo com o filtro indicado
     *
     * @param tipo filtro da pesquisa
     * @throws Exception lanca excecao caso nenhum item seja encontrado
     */
    public void pesquisar(FiltroPesquisa tipo) throws Exception {
        List<String> valoresAtributo = new ArrayList<>();

        if (this.getEstoque().getItens() == null) {
            throw new Exception("Não existe nenhum item cadastrado no estoque!");
        }

        for (Item item : this.getEstoque().getItens()) {
            String valor = obterValorParaTipo(item, tipo);

            if (valor != null) {
                valoresAtributo.add(valor);
            }
        }

        if (valoresAtributo.isEmpty()) {
            throw new Exception("Nenhum resultado encontrado!");
        }

        String option = choice(valoresAtributo);

        if (tipo == FiltroPesquisa.ANO) {
            this.anoPublicacao(option);
        } else {
            System.out.println(encontrarItem(tipo, option));
        }
        System.out.print("Pressione Enter para continuar...");
        InputScannerUtil.scanner.nextLine();
    }

    /**
     * Lista opcoes no menu do console
     *
     * @param itens itens de escolha
     * @return item selecionado
     */
    public String choice(List<String> itens) {
        Set<String> uniqueItems = new HashSet<>(itens);
        List<String> uniqueItemList = new ArrayList<>(uniqueItems);

        Collections.sort(uniqueItemList, String.CASE_INSENSITIVE_ORDER);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < uniqueItemList.size(); i++) {
            result.append((i + 1))
                    .append(" - ")
                    .append(uniqueItemList.get(i))
                    .append("\n");
        }
        System.out.println(" ");
        System.out.println(result);
        System.out.print("Escolha o número: ");
        int option = InputScannerUtil.scanner.nextInt();

        return uniqueItemList.get(option - 1);
    }

    /**
     * Informa o ano de publicacao de um item
     *
     * @param result opcao definida no metodo 'choice'
     */
    private void anoPublicacao(String result) {
        for (Item item : this.getEstoque().getItens()) {
            if (Integer.toString(item.getDataPublicacao().getYear()).equals(result)) {
                System.out.println(item);
                System.out.println("\n");
            }
        }
    }

    /**
     * Encontra o item de acordo com o filtro de pesquisa
     *
     * @param tipo   tipo de filtro
     * @param result opcao definida no metodo 'choice'
     * @return item encontrado
     */
    public Item encontrarItem(FiltroPesquisa tipo, String result) {

        Optional<Item> item = this.getEstoque().getItens().stream().filter(x -> {
            String valor = obterValorParaTipo(x, tipo);
            return valor != null && valor.equals(result);
        }).findFirst();

        return item.orElse(null);
    }

    /**
     * Obtem o valor do item a ser indicado no menu de escolha
     *
     * @param item item de referencia
     * @param tipo tipo de filtro
     * @return valor de item de acordo com o tipo
     */
    private String obterValorParaTipo(Item item, FiltroPesquisa tipo) {
        switch (tipo) {
            case TITULO:
                return item.getTitulo();
            case ANO:
                return Integer.toString(item.getDataPublicacao().getYear());
            case AUTOR:
                if (item instanceof AutorFiltro) {
                    return ((AutorFiltro) item).getAutor();
                }
                break;
            case LIVRO:
                if (item instanceof Livro) {
                    return item.getTitulo();
                }
                break;
            case REVISTA:
                if (item instanceof Revista) {
                    return item.getTitulo();
                }
                break;
            case TESE:
                if (item instanceof Tese) {
                    return item.getTitulo();
                }
                break;
            case CD:
                if (item instanceof CD) {
                    return item.getTitulo();
                }
                break;
            case DVD:
                if (item instanceof DVD) {
                    return item.getTitulo();
                }
                break;
        }
        return null;
    }
}
