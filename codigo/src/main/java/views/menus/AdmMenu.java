package main.java.views.menus;

import main.java.models.Biblioteca;
import main.java.models.Usuario;
import main.java.models.itens.Emprestavel;
import main.java.models.itens.Item;
import main.java.services.ItemEmprestavelService;
import main.java.services.ItemService;
import main.java.services.UsuarioService;
import main.java.utils.InputScannerUtil;
import main.java.views.inputs.*;

import java.util.Optional;

public class AdmMenu {

    /**
     * Menu principal do sistema
     *
     * @param usuarioService servico do usuario
     * @param itemService    servico de item nao emprestavel
     * @param itemEmprestavelService servico de item emprestavel
     * @param biblioteca     biblioteca de referencia
     */
    public static void menuPrincipal(UsuarioService usuarioService, ItemService itemService,
            ItemEmprestavelService itemEmprestavelService, Biblioteca biblioteca) {

        while (true) {
            System.out.println("\nEscolha a operação desejada:");
            System.out.println("1. Gerenciar usuários");
            System.out.println("2. Gerenciar itens não emprestáveis");
            System.out.println("3. Gerenciar itens emprestáveis");
            System.out.println("4. Pesquisar itens da biblioteca");
            System.out.println("5. Imprimir relatório de usuário");
            System.out.println("6. Imprimir relatório de itens");
            System.out.println("7. Voltar ao menu principal");

            System.out.print("Opção: ");
            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            if (escolha == 7) {
                System.out.println("Voltando ao menu principal...\n");
                break;
            }

            switch (escolha) {
                case 1:
                    menuUsuario(usuarioService);
                    break;
                case 2:
                    menuItensNaoEmprestaveis(itemService, biblioteca);
                    break;
                case 3:
                    menuItensEmprestaveis(itemEmprestavelService, biblioteca);
                    break;
                case 4:
                    try {
                        PesquisaMenu.pesquisa(usuarioService.getBiblioteca());
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                        System.out.println();
                    }
                    break;
                case 5:
                    Optional<Usuario> usuarioOptional = AdmInput.dadosDePesquisaDoUsuario(usuarioService);
                    usuarioOptional.ifPresent(biblioteca::imprimirRelatorioUsuario);
                    break;
                case 6:
                    try {
                        AdmInput.checkAdm(usuarioService);
                        biblioteca.imprimirRelatorioItem();
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.print("Pressione Enter para continuar...");
            InputScannerUtil.scanner.nextLine();
        }

    }

    /**
     * Menu do usuario
     *
     * @param usuarioService servico do usuario
     */
    public static void menuUsuario(UsuarioService usuarioService) {

        while (true) {
            System.out.println("Escolha a operação desejada:");
            System.out.println("1. Pesquisar usuário"); // por id
            System.out.println("2. Deletar usuário"); // Por id
            System.out.println("3. Listar todos os usuários cadastrados");
            System.out.println("4. Voltar");
            System.out.print("Opção: ");

            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            if (escolha == 4) {
                System.out.println("Voltando ao menu principal...\n");
                break;
            }

            switch (escolha) {
                case 1:
                    Optional<Usuario> usuarioOptional = AdmInput.dadosDePesquisaDoUsuario(usuarioService);
                    usuarioOptional.ifPresent(System.out::println);
                    break;
                case 2:
                    AdmInput.deletarUsuarioPorId(usuarioService);
                    break;
                case 3:
                    AdmInput.dadosDeTodosOsUsuarios(usuarioService);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.print("Pressione Enter para continuar...");
            InputScannerUtil.scanner.nextLine(); // Aguarda o usuário pressionar Enter
        }

    }

    /**
     * Menu de itens nao emprestaveis
     *
     * @param itemService servico do item nao emprestavel
     * @param biblioteca  biblioteca de referencia
     */
    public static void menuItensNaoEmprestaveis(ItemService itemService, Biblioteca biblioteca) {
        while (true) {
            System.out.println("Escolha a operação desejada:");
            System.out.println("1. Cadastrar item não emprestável");
            System.out.println("2. Atualizar Item não emprestável");
            System.out.println("3. Deletar Item não emprestável");
            System.out.println("4. Listar Item não emprestável");
            System.out.println("5. Voltar");
            System.out.print("Opção: ");

            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            if (escolha == 5) {
                System.out.println("Voltando ao menu principal...\n");
                // InputScannerUtil.close();
                break;
            }

            switch (escolha) {
                case 1:
                    Item item = new Item();
                    ItemService itemService2 = new ItemService(item, biblioteca);
                    criarItemNaoEmprestavel(itemService2);
                    break;
                case 2:
                    atualizarItemNaoEmprestavel(itemService, biblioteca);
                    break;
                case 3:
                    ItemNaoEmprestavelInput.excluirItem(itemService, biblioteca);
                    ;
                    break;
                case 4:
                    itemService.listar();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Cria um novo item nao emprestavel
     *
     * @param itemService servico do item nao emprestavel
     */
    public static void criarItemNaoEmprestavel(ItemService itemService) {
        System.out.println("Escolha o tipo de item a ser criado:");
        System.out.println("1. Revista");
        System.out.println("2. Tese");

        int escolha = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();
        switch (escolha) {
            case 1:
                ItemNaoEmprestavelInput.obterDadosDeRevista(itemService);
                break;
            case 2:
                ItemNaoEmprestavelInput.obterDadosDeTese(itemService);
                break;
            default:
                System.out.println("Tipo de item inválido.");
        }
    }

    /**
     * Atualiza um item nao emprestavel
     *
     * @param itemService servico do item nao emprestavel
     * @param biblioteca  biblioteca service
     */
    public static void atualizarItemNaoEmprestavel(ItemService itemService, Biblioteca biblioteca) {
        System.out.println("Escolha o tipo de item a ser atualizado:");
        System.out.println("1. Revista");
        System.out.println("2. Tese");

        int escolha = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();
        switch (escolha) {
            case 1:
                ItemNaoEmprestavelInput.atualizarDadosDeRevista(itemService, biblioteca);
                break;
            case 2:
                ItemNaoEmprestavelInput.atualizarDadosDeTese(itemService, biblioteca);
                break;
            default:
                System.out.println("Tipo de item inválido.");
        }
    }

    /**
     * Menu de itens emprestaveis
     *
     * @param itemEmprestavelService servico do item emprestavel
     * @param biblioteca             biblioteca de referencia
     */
    public static void menuItensEmprestaveis(ItemEmprestavelService itemEmprestavelService, Biblioteca biblioteca) {
        while (true) {
            System.out.println("Escolha a operação desejada:");
            System.out.println("1. Cadastrar item emprestável");
            System.out.println("2. Atualizar item emprestável");
            System.out.println("3. Deletar item emprestável");
            System.out.println("4. Listar item emprestável");
            System.out.println("5. Listar todos os itens emprestáveis");
            System.out.println("6. Voltar");
            System.out.print("Opção: ");

            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            if (escolha == 6) {
                System.out.println("Voltando ao menu principal...\n");
                // InputScannerUtil.close();
                break;
            }

            switch (escolha) {
                case 1:
                    Emprestavel emprestavel = new Emprestavel();
                    ItemEmprestavelService itemEmprestavelService2 = new ItemEmprestavelService(biblioteca);
                    criarItemEmprestavel(itemEmprestavelService2);
                    break;
                case 2:
                    atualizarItemEmprestavel(itemEmprestavelService, biblioteca);
                    break;
                case 3:
                    ItemEmprestavelInput.excluirItem(itemEmprestavelService, biblioteca);
                    break;
                case 4:
                    try {
                        itemEmprestavelService.listarDisponiveis(biblioteca);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 5:
                    itemEmprestavelService.listar();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Cria um novo item emprestavel
     *
     * @param itemEmprestavelService servico do item emprestavel
     */
    public static void criarItemEmprestavel(ItemEmprestavelService itemEmprestavelService) {
        System.out.println("Escolha o tipo de item a ser criado:");
        System.out.println("1. DVD");
        System.out.println("2. CD");
        System.out.println("3. Livro");

        int escolha = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();
        switch (escolha) {
            case 1:
                ItemEmprestavelInput.obterDadosDeDVD(itemEmprestavelService);
                break;
            case 2:
                ItemEmprestavelInput.obterDadosDeCD(itemEmprestavelService);
                break;
            case 3:
                ItemEmprestavelInput.obterDadosDeLivro(itemEmprestavelService);
                break;
            default:
                System.out.println("Tipo de item inválido.");
        }
    }

    /**
     * Atualiza um item emprestavel
     *
     * @param itemEmprestavelService servico do item emprestavel
     * @param biblioteca             biblioteca service
     */
    public static void atualizarItemEmprestavel(ItemEmprestavelService itemEmprestavelService, Biblioteca biblioteca) {
        System.out.println("Escolha o tipo de item a ser atualizado:");
        System.out.println("1. DVD");
        System.out.println("2. CD");
        System.out.println("3. Livro");

        int escolha = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();
        switch (escolha) {
            case 1:
                ItemEmprestavelInput.atualizarDadosDeDVD(itemEmprestavelService, biblioteca);
                break;
            case 2:
                ItemEmprestavelInput.atualizarDadosDeCD(itemEmprestavelService, biblioteca);
                break;
            case 3:
                ItemEmprestavelInput.atualizarDadosDeLivro(itemEmprestavelService, biblioteca);
                break;
            default:
                System.out.println("Tipo de item inválido.");
        }
    }

    /*
     * opcoes de campos para serem atualizados
     */
    public static void opcoesRevista() {

        System.out.println("Opção:");
        System.out.println("1. Título");
        System.out.println("2. Data de Publicação");
        System.out.println("3. Classificação");
        System.out.println("4. Edição");
        System.out.println("5. Editora");
        System.out.println("6. Artigos");
    }

    /*
     * opcoes de campos para serem atualizados
     */
    public static void opcoesTese() {

        System.out.println("Opção:");
        System.out.println("1. Título");
        System.out.println("2. Data de Publicação");
        System.out.println("3. Classificação");
        System.out.println("4. Autor");
        System.out.println("5. Orientador");
        System.out.println("6. Data da defesa");
        System.out.println("7. Capitulos");
    }

    /*
     * opcoes de campos para serem atualizados
     */
    public static void opcoesDVD() {
        System.out.println("Opção:");
        System.out.println("1. Título");
        System.out.println("2. Data de Publicação");
        System.out.println("3. Classificação");
        System.out.println("4. Status empréstimo");
        System.out.println("5. Diretor");
        System.out.println("6. Duração");
        System.out.println("7. Idioma");
        System.out.println("8. Sinopse");
        System.out.println("9. Genero");
    }

    /*
     * opcoes de campos para serem atualizados
     */
    public static void opcoesCD() {
        System.out.println("Opção:");
        System.out.println("1. Título");
        System.out.println("2. Data de Publicação");
        System.out.println("3. Classificação");
        System.out.println("4. Status empréstimo");
        System.out.println("5. Artista");
        System.out.println("6. Duração");
        System.out.println("7. Faixas");
    }

    /*
     * opcoes de campos para serem atualizados
     */
    public static void opcoesLivro() {
        System.out.println("Opção:");
        System.out.println("1. Título");
        System.out.println("2. Data de Publicação");
        System.out.println("3. Classificação");
        System.out.println("4. Status empréstimo");
        System.out.println("5. Autor");
        System.out.println("6. Edição");
        System.out.println("7. Idioma");
        System.out.println("8. Sinopse");
        System.out.println("9. Gênero");
        System.out.println("10. Volume");
        System.out.println("11. Número de páginas");
    }
}
