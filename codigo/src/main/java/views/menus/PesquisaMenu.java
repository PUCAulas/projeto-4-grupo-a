package main.java.views.menus;

import main.java.enums.FiltroPesquisa;
import main.java.exceptions.ItemIndisponivelException;
import main.java.models.Biblioteca;
import main.java.utils.InputScannerUtil;

public class PesquisaMenu {

    /**
     * Menu de pesquisa de dados
     *
     * @param biblioteca biblioteca de referencia
     * @throws Exception lanca excecao caso o item nao seja encontrado
     */
    public static void pesquisa(Biblioteca biblioteca) throws ItemIndisponivelException {

        while (true) {
            System.out.println("\nEscolha um filtro de pesquisa:");
            System.out.println("1. Por Título");
            System.out.println("2. Por Ano");
            System.out.println("3. Por Autor");
            System.out.println("4. Por Tipo de Item");
            System.out.println("5. Sair");
            System.out.print("Opção: ");

            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            if (escolha == 5) {
                System.out.println("Voltando...\n");
                break;
            }

            FiltroPesquisa filtro = null;

            if (escolha == 4) {
                System.out.print("Digite o tipo de item (livro, revista, tese, DVD, CD): ");
                String tipoItem = InputScannerUtil.scanner.nextLine();
                try {
                    filtro = FiltroPesquisa.valueOf(tipoItem.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println(
                            "Tipo de item inválido. Certifique-se de usar um dos valores válidos (livro, revista, tese, DVD, CD).");
                    continue;
                }
            } else {
                switch (escolha) {
                    case 1:
                        filtro = FiltroPesquisa.TITULO;
                        break;
                    case 2:
                        filtro = FiltroPesquisa.ANO;
                        break;
                    case 3:
                        filtro = FiltroPesquisa.AUTOR;
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                }
            }

            if (filtro != null) {
                biblioteca.pesquisar(filtro);
            }

        }
    }




}
