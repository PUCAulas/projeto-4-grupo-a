package main.java.views.inputs;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.java.enums.StatusClassificacao;
import main.java.enums.StatusEmprestimo;
import main.java.models.Biblioteca;
import main.java.models.itens.CD;
import main.java.models.itens.DVD;
import main.java.models.itens.Emprestavel;
import main.java.models.itens.Item;
import main.java.models.itens.Livro;
import main.java.services.ItemEmprestavelService;
import main.java.utils.DataUtil;
import main.java.utils.InputScannerUtil;
import main.java.utils.ObjectFactoryUtil;
import main.java.views.menus.AdmMenu;

public class ItemEmprestavelInput extends ItemInput {

    /**
     * Recebe os dados para a criação do DVD
     *
     * @param itemEmprestavelService servico de item emprestavel
     */
    public static void obterDadosDeDVD(ItemEmprestavelService itemEmprestavelService) {

        System.out.println("Informe o título do DVD: ");
        String titulo = InputScannerUtil.scanner.nextLine();

        System.out.println("Informe a data de publicação (dd/MM/yyyy): ");
        LocalDate dataPublicacao = LocalDate.parse(InputScannerUtil.scanner.nextLine(), DataUtil.fmt);

        System.out.println("Informe o status de classificação do DVD:");
        StatusClassificacao statusClassificacao = escolherStatusClassificacao();

        System.out.println("Informe o status de empréstimo do DVD:");
        StatusEmprestimo statusEmprestimo = escolherStatusEmprestimo();

        System.out.println("Informe o diretor do DVD: ");
        String diretor = InputScannerUtil.scanner.nextLine();

        System.out.println("Informe a duração em segundos: ");
        Duration duracao = Duration.ofSeconds(Long.parseLong(InputScannerUtil.scanner.nextLine()));

        System.out.println("Informe o idioma do DVD: ");
        String idioma = InputScannerUtil.scanner.nextLine();

        System.out.println("Informe a sinopse do DVD: ");
        String sinopse = InputScannerUtil.scanner.nextLine();

        System.out.println("Informe o gênero do DVD: ");
        String genero = InputScannerUtil.scanner.nextLine();

        DVD dvd = ObjectFactoryUtil.newDVD();
        itemEmprestavelService.setEmprestimo(dvd);

        try {
            itemEmprestavelService.criarDVD(titulo, dataPublicacao, statusClassificacao, statusEmprestimo, diretor, duracao, idioma, sinopse, genero);
            System.out.println("\nDVD cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }




    /**
     * Recebe os dados para a criação do CD
     *
     * @param itemEmprestavelService servico de item emprestavel
     */
    public static void obterDadosDeCD(ItemEmprestavelService itemEmprestavelService) {
        Scanner scanner = InputScannerUtil.scanner;

        System.out.println("Informe o título do CD: ");
        String titulo = scanner.nextLine();

        System.out.println("Informe a data de publicação (dd/MM/yyyy): ");
        LocalDate dataPublicacao = LocalDate.parse(scanner.nextLine(), DataUtil.fmt);

        System.out.println("Informe o status de classificação do CD:");
        StatusClassificacao statusClassificacao = escolherStatusClassificacao();

        System.out.println("Informe o status de empréstimo do CD:");
        StatusEmprestimo statusEmprestimo = escolherStatusEmprestimo();

        System.out.println("Informe o artista do CD: ");
        String artista = scanner.nextLine();

        System.out.println("Informe a duração em segundos: ");
        Duration duracao = Duration.ofSeconds(Long.parseLong(scanner.nextLine()));

        System.out.println("Informe as faixas do CD: ");
        List<String> faixas = new ArrayList<>();
        inserirConteudo(faixas);

        CD cd = ObjectFactoryUtil.newCD();
        itemEmprestavelService.setEmprestimo(cd);

        try {
            itemEmprestavelService.criarCD(titulo, dataPublicacao, statusClassificacao, statusEmprestimo, artista, duracao, faixas);
            System.out.println("\nCD cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Recebe os dados para a criação do livro
     *
     * @param itemEmprestavelService servico de item emprestavel
     */
    public static void obterDadosDeLivro(ItemEmprestavelService itemEmprestavelService) {
        Scanner scanner = InputScannerUtil.scanner;

        System.out.println("Informe o título do Livro: ");
        String titulo = scanner.nextLine();

        System.out.println("Informe a data de publicação (dd/MM/yyyy): ");
        LocalDate dataPublicacao = LocalDate.parse(scanner.nextLine(), DataUtil.fmt);

        System.out.println("Informe o status de classificação do Livro:");
        StatusClassificacao statusClassificacao = escolherStatusClassificacao();

        System.out.println("Informe o status de empréstimo do Livro:");
        StatusEmprestimo statusEmprestimo = escolherStatusEmprestimo();

        System.out.println("Informe o autor do Livro: ");
        String autor = scanner.nextLine();

        System.out.println("Informe o número de páginas: ");
        int numeroPaginas = Integer.parseInt(scanner.nextLine());

        System.out.println("Informe a editora do Livro: ");
        String editora = scanner.nextLine();

        System.out.println("Informe a edição do Livro: ");
        String edicao = scanner.nextLine();

        System.out.println("Informe o volume do Livro: ");
        String volume = scanner.nextLine();

        System.out.println("Informe o idioma: ");
        String idioma = scanner.nextLine();

        System.out.println("Informe o gênero do Livro: ");
        String genero = scanner.nextLine();

        System.out.println("Informe a sinopse do Livro: ");
        String sinopse = scanner.nextLine();

        Livro livro = ObjectFactoryUtil.newLivro();
        itemEmprestavelService.setEmprestimo(livro);

        try {
            itemEmprestavelService.criarLivro(titulo, dataPublicacao, statusClassificacao, statusEmprestimo, autor,
                    numeroPaginas,
                    editora, edicao, volume, idioma, genero, sinopse);
            System.out.println("\nLivro cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Recebe os dados para a atualização do DVD
     *
     * @param itemEmprestavelService servico de item emprestavel
     * @param biblioteca  biblioteca de referencia
     */
    public static void atualizarDadosDeDVD(ItemEmprestavelService itemEmprestavelService, Biblioteca biblioteca) {
        System.out.print("Informe o ID do item a ser atualizado: ");
        int id = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();

        for (Item item : biblioteca.getEstoque().getItens()) {
            if (item.getId() == id && item instanceof DVD) {
                AdmMenu.opcoesDVD();
                itemEmprestavelService.setEmprestimo(((Emprestavel) item));

                int escolha = InputScannerUtil.scanner.nextInt();
                InputScannerUtil.scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.println("Informe o novo título: ");
                        String titulo = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarDVD(titulo, ((DVD) item).getDataPublicacao(), ((DVD) item).getStatusClassificacao(), ((DVD) item).getStatusEmprestimo(), ((DVD) item).getDiretor(), ((DVD) item).getDuracao(),((DVD) item).getIdioma(), ((DVD) item).getSinopse(), ((DVD) item).getGenero());
                        break;
                    case 2:
                        System.out.println("Informe a nova data de publicação (dd/MM/yyyy): ");
                        LocalDate novaData = LocalDate.parse(InputScannerUtil.scanner.nextLine(), DataUtil.fmt);
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), novaData, ((DVD) item).getStatusClassificacao(), ((DVD) item).getStatusEmprestimo(), ((DVD) item).getDiretor(), ((DVD) item).getDuracao(), ((DVD) item).getIdioma(), ((DVD) item).getSinopse(), ((DVD) item).getGenero());
                        break;
                    case 3:
                        System.out.println("Informe a nova classificação: ");
                        StatusClassificacao novaClassificacao = escolherStatusClassificacao();
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), ((DVD) item).getDataPublicacao(), novaClassificacao, ((DVD) item).getStatusEmprestimo(), ((DVD) item).getDiretor(), ((DVD) item).getDuracao(), ((DVD) item).getIdioma(), ((DVD) item).getSinopse(), ((DVD) item).getGenero());
                        break;
                    case 4:
                        System.out.println("Informe o status do empréstimo: ");
                        StatusEmprestimo statusEmprestimo = escolherStatusEmprestimo();
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), ((DVD) item).getDataPublicacao(), ((DVD) item).getStatusClassificacao(), statusEmprestimo, ((DVD) item).getDiretor(), ((DVD) item).getDuracao(), ((DVD) item).getIdioma(), ((DVD) item).getSinopse(), ((DVD) item).getGenero());
                        break;
                    case 5:
                        System.out.println("Informe o novo diretor");
                        String novoDiretor = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), ((DVD) item).getDataPublicacao(), ((DVD) item).getStatusClassificacao(), ((DVD) item).getStatusEmprestimo(), novoDiretor, ((DVD) item).getDuracao(), ((DVD) item).getIdioma(), ((DVD) item).getSinopse(), ((DVD) item).getGenero());
                        break;
                    case 6:
                        System.out.println("Informe a duração: ");
                        Duration novaDuracao = Duration.ofSeconds(Long.parseLong(InputScannerUtil.scanner.nextLine()));
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), ((DVD) item).getDataPublicacao(), ((DVD) item).getStatusClassificacao(), ((DVD) item).getStatusEmprestimo(), ((DVD) item).getDiretor(), novaDuracao, ((DVD) item).getIdioma(), ((DVD) item).getSinopse(), ((DVD) item).getGenero());
                        break;
                    case 7:
                        System.out.println("Informe o idioma: ");
                        String novoIdioma = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), ((DVD) item).getDataPublicacao(), ((DVD) item).getStatusClassificacao(), ((DVD) item).getStatusEmprestimo(), ((DVD) item).getDiretor(), ((DVD) item).getDuracao(), novoIdioma, ((DVD) item).getSinopse(), ((DVD) item).getGenero());
                        break;
                    case 8:
                        System.out.println("Informe a sinopse ");
                        String novaSinopse = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), ((DVD) item).getDataPublicacao(), ((DVD) item).getStatusClassificacao(), ((DVD) item).getStatusEmprestimo(), ((DVD) item).getDiretor(), ((DVD) item).getDuracao(), ((DVD) item).getIdioma(), novaSinopse, ((DVD) item).getGenero());
                        break;
                    case 9:
                        System.out.println("Informe o gênero ");
                        String novoGenero = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarDVD(((DVD) item).getTitulo(), ((DVD) item).getDataPublicacao(), ((DVD) item).getStatusClassificacao(), ((DVD) item).getStatusEmprestimo(), ((DVD) item).getDiretor(), ((DVD) item).getDuracao(), ((DVD) item).getIdioma(), ((DVD) item).getSinopse(), novoGenero);
                        break;
                    default:
                        System.out.println("Atributo inválido.");
                }

                System.out.println("Item atualizado!");
                return;
            }
        }
        System.out.println("Item não encontrado ou tipo de item incorreto!");
    }

     /**
     * Recebe os dados para a atualização do CD
     *
     * @param itemEmprestavelService servico de item emprestavel
     * @param biblioteca  biblioteca de referencia
     */
    public static void atualizarDadosDeCD(ItemEmprestavelService itemEmprestavelService, Biblioteca biblioteca) {
        System.out.print("Informe o ID do item a ser atualizado: ");
        int id = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();

        for (Item item : biblioteca.getEstoque().getItens()) {
            if (item.getId() == id && item instanceof CD) {
                AdmMenu.opcoesCD();
                itemEmprestavelService.setEmprestimo(((Emprestavel) item));

                int escolha = InputScannerUtil.scanner.nextInt();
                InputScannerUtil.scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.println("Informe o novo título: ");
                        String titulo = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarCD(titulo, ((CD) item).getDataPublicacao(), ((CD) item).getStatusClassificacao(), ((CD) item).getStatusEmprestimo(), ((CD) item).getArtista(), ((CD) item).getDuracao(), ((CD) item).getFaixas());
                        break;
                    case 2:
                        System.out.println("Informe a nova data de publicação (dd/MM/yyyy): ");
                        LocalDate novaData = LocalDate.parse(InputScannerUtil.scanner.nextLine(), DataUtil.fmt);
                        itemEmprestavelService.atualizarCD(((CD) item).getTitulo(), novaData, ((CD) item).getStatusClassificacao(), ((CD) item).getStatusEmprestimo(), ((CD) item).getArtista(), ((CD) item).getDuracao(), ((CD) item).getFaixas());
                        break;
                    case 3:
                        System.out.println("Informe a nova classificação: ");
                        StatusClassificacao novaClassificacao = escolherStatusClassificacao();
                        itemEmprestavelService.atualizarCD(((CD) item).getTitulo(), ((CD) item).getDataPublicacao(), novaClassificacao, ((CD) item).getStatusEmprestimo(), ((CD) item).getArtista(), ((CD) item).getDuracao(), ((CD) item).getFaixas());
                        break;
                    case 4:
                        System.out.println("Informe o status do empréstimo: ");
                        StatusEmprestimo statusEmprestimo = escolherStatusEmprestimo();
                        itemEmprestavelService.atualizarCD(((CD) item).getTitulo(), ((CD) item).getDataPublicacao(), ((CD) item).getStatusClassificacao(), statusEmprestimo, ((CD) item).getArtista(), ((CD) item).getDuracao(), ((CD) item).getFaixas());
                        break;
                    case 5:
                        System.out.println("Informe o novo artista");
                        String novoArtista = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarCD(((CD) item).getTitulo(), ((CD) item).getDataPublicacao(), ((CD) item).getStatusClassificacao(), ((CD) item).getStatusEmprestimo(), novoArtista, ((CD) item).getDuracao(), ((CD) item).getFaixas());
                        break;
                    case 6:
                        System.out.println("Informe a duração em segundos: ");
                        Duration novaDuracao = Duration.ofSeconds(Long.parseLong(InputScannerUtil.scanner.nextLine()));
                        itemEmprestavelService.atualizarCD(((CD) item).getTitulo(), ((CD) item).getDataPublicacao(), ((CD) item).getStatusClassificacao(), ((CD) item).getStatusEmprestimo(), ((CD) item).getArtista(), novaDuracao, ((CD) item).getFaixas());
                        break;
                    case 7:
                        List<String> faixas = new ArrayList<>();
                        System.out.println("Informe as faixas do CD (FIM para sair): ");
                        ItemInput.inserirConteudo(faixas);
                        itemEmprestavelService.atualizarCD(((CD) item).getTitulo(), ((CD) item).getDataPublicacao(), ((CD) item).getStatusClassificacao(), ((CD) item).getStatusEmprestimo(), ((CD) item).getArtista(), ((CD) item).getDuracao(), faixas);
                        break;
                    default:
                        System.out.println("Atributo inválido.");
                }

                System.out.println("Item atualizado!");
                return;
            }
        }
        System.out.println("Item não encontrado ou tipo de item incorreto!");
    }

    /**
     * Recebe os dados para a atualização do livro
     *
     * @param itemEmprestavelService servico de item emprestavel
     * @param biblioteca  biblioteca de referencia
     */
    public static void atualizarDadosDeLivro(ItemEmprestavelService itemEmprestavelService, Biblioteca biblioteca) {
        System.out.print("Informe o ID do livro a ser atualizado: ");
        int id = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();

        for (Item item : biblioteca.getEstoque().getItens()) {
            if (item.getId() == id && item instanceof Livro) {
                AdmMenu.opcoesLivro();
                itemEmprestavelService.setEmprestimo(((Emprestavel) item));

                int escolha = InputScannerUtil.scanner.nextInt();
                InputScannerUtil.scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.println("Informe o novo título: ");
                        String titulo = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(titulo, ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 2:
                        System.out.println("Informe a nova data de publicação (dd/MM/yyyy): ");
                        LocalDate novaData = LocalDate.parse(InputScannerUtil.scanner.nextLine(), DataUtil.fmt);
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), novaData, ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 3:
                        System.out.println("Informe a nova classificação: ");
                        StatusClassificacao novaClassificacao = escolherStatusClassificacao();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), novaClassificacao, ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 4:
                        System.out.println("Informe o status do empréstimo: ");
                        StatusEmprestimo statusEmprestimo = escolherStatusEmprestimo();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), statusEmprestimo, ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 5:
                        System.out.println("Informe o novo autor: ");
                        String novoAutor = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), novoAutor, ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 6:
                        System.out.println("Informe a nova editora: ");
                        String novaEditora = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), novaEditora, ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 7:
                        System.out.println("Informe a edição: ");
                        String novaEdicao = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), novaEdicao, ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 8:
                        System.out.println("Informe o idioma: ");
                        String novoIdioma = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), novoIdioma, ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 9:
                        System.out.println("Informe a sinopse: ");
                        String novaSinopse = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), novaSinopse, ((Livro) item).getGenero(), ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 10:
                        System.out.println("Informe o gênero: ");
                        String novoGenero = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), novoGenero, ((Livro) item).getVolume(), ((Livro) item).getNumeroPaginas());
                        break;
                    case 11:
                        System.out.println("Informe o volume: ");
                        String novoVolume = InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), novoVolume, ((Livro) item).getNumeroPaginas());
                        break;
                    case 12:
                        System.out.println("Informe o número de páginas: ");
                        int numPaginas = InputScannerUtil.scanner.nextInt();
                        InputScannerUtil.scanner.nextLine();
                        itemEmprestavelService.atualizarLivro(((Livro) item).getTitulo(), ((Livro) item).getDataPublicacao(), ((Livro) item).getStatusClassificacao(), ((Livro) item).getStatusEmprestimo(), ((Livro) item).getAutor(), ((Livro) item).getEditora(), ((Livro) item).getEdicao(), ((Livro) item).getIdioma(), ((Livro) item).getSinopse(), ((Livro) item).getGenero(), ((Livro) item).getVolume(), numPaginas);
                        break;
                    default:
                        System.out.println("Atributo inválido.");
                }

                System.out.println("Item atualizado!");
                return;
            }
        }
        System.out.println("Livro não encontrado ou tipo de item incorreto!");
    }

    
    /**
     * Recebe dados para exclusão do item
     *
     * @param itemEmprestavelService servico de item emprestavel
     * @param biblioteca  biblioteca de referencia
     */
    public static void excluirItem(ItemEmprestavelService itemEmprestavelService, Biblioteca biblioteca) {

        System.out.print("Informe o ID do item: ");
        int id = InputScannerUtil.scanner.nextInt();

        Item itemDeletado = null;
        for (Item item : biblioteca.getEstoque().getItens()) {
            if (item.getId() == id && item instanceof Emprestavel) {
                itemDeletado = item;
                itemEmprestavelService.deletar(itemDeletado);
                break;
            }
        }

        if (itemDeletado != null) {
            System.out.println("Item deletado com sucesso!");
        } else {
            System.out.println("Item não encontrado!");
        }
    }




}
