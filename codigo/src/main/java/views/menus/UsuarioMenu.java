package main.java.views.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.enums.CategoriaInteresse;
import main.java.enums.FiltroPesquisa;
import main.java.exceptions.*;
import main.java.models.Biblioteca;
import main.java.models.Usuario;
import main.java.models.UsuarioAdaptarImpl;
import main.java.models.itens.Emprestavel;
import main.java.services.UsuarioService;
import main.java.utils.InputScannerUtil;
import main.java.views.inputs.UsuarioInput;

public class UsuarioMenu {

    /**
     * Menu principal do usuario
     * 
     * @param usuarioService servico do usuario
     */
    public static void menuPrincipal(UsuarioService usuarioService) {

        while (true) {
            System.out.println("\nEscolha a operação desejada:");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Atualizar usuário"); // login
            System.out.println("3. Deletar usuário");
            System.out.println("4. pesquisar itens da biblioteca");
            System.out.println("5. Pegar emprestado");
            System.out.println("6. Devolver");
            System.out.println("7. Sugestão de itens");
            System.out.println("8. Voltar");
            System.out.print("\nOpção: ");

            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            if (escolha == 8) {
                System.out.println("Voltando ao menu principal...\n");
                break;
            }

            switch (escolha) {
                case 1:
                    UsuarioInput.obterDadosDeCadastro(usuarioService);
                    break;
                case 2:
                    UsuarioInput.obterDadosDeAtualizacao(usuarioService);
                    break;
                case 3:
                    UsuarioInput.obterDadosDeExclusao(usuarioService);
                    break;
                case 4:
                    pesquisarItens(usuarioService);
                    break;
                case 5:
                    pegarEmprestado(usuarioService);
                    break;
                case 6:
                    devolverEmprestimo(usuarioService);
                    break;
                case 7:
                    UsuarioInput.imprimirSugestao(usuarioService);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * lista os itens disponiveis para emprestimo
     * 
     * @param emprestaveis itens emprestaveis de referencia
     */
    public static void imprimirListaEmprestaveis(List<Emprestavel> emprestaveis) {
        System.out.println("Lista de itens disponíveis para empréstimo:");
        for (Emprestavel emprestavel : emprestaveis) {
            System.out.println(emprestavel);
        }
    }


    public static void pegarEmprestado(UsuarioService usuarioService) {
        try {
            List<Emprestavel> emprestaveis = UsuarioInput.EmprestadosDisponiveis(usuarioService);
            imprimirListaEmprestaveis(emprestaveis);

            UsuarioInput.escolherItemParaEmprestimo(usuarioService);
            System.out.println("Empréstimo realizado com sucesso!");
            System.out.println("Aperte Enter para continuar...");
            InputScannerUtil.scanner.nextLine();
        } catch (ItemIndisponivelException | ItemNaoEmprestavelException | EmprestimoLimiteException |
                DevolucaoDoEmprestimoException | UsuarioAutenticadoException e) {

            System.out.println("Erro: " + e.getMessage());
        }
    }


    public static void devolverEmprestimo(UsuarioService usuarioService) {
        try {
            UsuarioInput.obterDadosParaDevolucao(usuarioService);
        } catch (ItemIndisponivelException | DevolucaoDoEmprestimoException | ItemNaoEmprestavelException | UsuarioAutenticadoException e) {

            System.out.println(e.getMessage());
        }
    }


    public static void pesquisarItens(UsuarioService usuarioService) {
        try {
            PesquisaMenu.pesquisa(usuarioService.getBiblioteca());
        } catch (ItemIndisponivelException e) {
            System.out.println("Erro: " + e.getMessage());
            System.out.println();
        }
    }



    public static UsuarioAdaptarImpl menuCategoriaInterese(UsuarioService usuarioService) {

        UsuarioAdaptarImpl usuarioAdaptar = new UsuarioAdaptarImpl(usuarioService.getUsuario());
        UsuarioAdaptarImpl newUsuario = null;

        while (true) {
            System.out.println("Escolha um item de seu interesse ou FIM para sair: ");
            System.out.println("1 - CD");
            System.out.println("2 - DVD");
            System.out.println("3 - Livro");
            System.out.println("4 - FIM");
            System.out.print("Escolha: ");
            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            if (escolha == 4) {
                System.out.println("Voltando ao menu principal...\n");
                break;
            }

            switch (escolha) {
                case 1:
                    newUsuario = UsuarioInput.infoSugerirCd(usuarioAdaptar);
                    break;
                case 2:
                    newUsuario = UsuarioInput.infoSugerirDvd(usuarioAdaptar);
                    break;
                case 3:
                    newUsuario = UsuarioInput.infoSugerirLivro(usuarioAdaptar);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        }

        return newUsuario;

    }



}
