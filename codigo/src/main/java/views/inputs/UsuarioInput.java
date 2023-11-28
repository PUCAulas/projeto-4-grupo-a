package main.java.views.inputs;

import main.java.enums.Perfil;
import main.java.enums.StatusEmprestimo;
import main.java.exceptions.*;
import main.java.models.Usuario;
import main.java.models.itens.Emprestavel;
import main.java.services.ItemEmprestavelService;
import main.java.services.UsuarioService;
import main.java.utils.DataUtil;
import main.java.utils.InputScannerUtil;
import main.java.utils.ObjectFactoryUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioInput {

    /**
     * Menu de cadastro de usuario
     *
     * @param usuarioService servico de usuario
     */
    public static void obterDadosDeCadastro(UsuarioService usuarioService) {

        String name, email, senha;
        LocalDate dataNascimento;

        System.out.print("\nInforme o nome do usuário: ");
        name = InputScannerUtil.scanner.nextLine();

        System.out.print("Informe o e-mail do usuário: ");
        email = InputScannerUtil.scanner.nextLine();

        System.out.print("Informe a senha do usuário: ");
        senha = InputScannerUtil.scanner.nextLine();

        System.out.print("Informe a data de nascimento (dd/MM/yyyy): ");
        dataNascimento = LocalDate.parse(InputScannerUtil.scanner.nextLine(), DataUtil.fmt);

        Usuario usuario = ObjectFactoryUtil.newUsuario();
        usuarioService.setUsuario(usuario);
        try {
            usuarioService.criar(name, email, senha, dataNascimento, Perfil.USUARIO);
            System.out.println("\nUsuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Menu de atualizacao de usuario
     *
     * @param usuarioService servico de usuario
     */
    public static void obterDadosDeAtualizacao(UsuarioService usuarioService) {
        try {
            Usuario usuario = obterUsuarioCadastrado(usuarioService);
            usuarioService.setUsuario(usuario);

            System.out.println("\nOpção:");
            System.out.println("1. Nome");
            System.out.println("2. Senha");
            System.out.println("3. Data de nascimento");
            System.out.print("Escolha: ");

            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            switch (escolha) {
                case 1:
                    System.out.print("Informe o novo nome: ");
                    String nome = InputScannerUtil.scanner.nextLine();
                    usuarioService.atualizar(nome, usuario.getEmail(), usuario.getSenha(), usuario.getDataNascimento());
                    break;
                case 2:
                    System.out.print("Informe a nova senha: ");
                    String senha = InputScannerUtil.scanner.nextLine();
                    usuarioService.atualizar(usuario.getNome(), usuario.getEmail(), senha, usuario.getDataNascimento());
                    break;
                case 3:
                    System.out.print("Informe a nova data de nascimento (dd/MM/yyyy): ");
                    LocalDate dataNascimento = LocalDate.parse(InputScannerUtil.scanner.nextLine(), DataUtil.fmt);
                    usuarioService.atualizar(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), dataNascimento);
                default:
                    System.out.println("Atributo inválido.");
            }

            System.out.println("Usuário atualizado com sucesso");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Mostra os itens emprestaveis que estao disponiveis
     * 
     * @param usuarioService servico de usuario
     * @return lista de emprestaveis disponiveis
     * @throws Exception
     */
    public static List<Emprestavel> EmprestadosDisponiveis(UsuarioService usuarioService) throws ItemIndisponivelException {
        List<Emprestavel> emprestaveis = new ArrayList<>();

        try {
            ItemEmprestavelService itemEmprestavelService = new ItemEmprestavelService(usuarioService.getBiblioteca());
            emprestaveis = itemEmprestavelService.listarDisponiveis(usuarioService.getBiblioteca());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            System.out.println();
        }

        if (emprestaveis.isEmpty())
            throw new ItemIndisponivelException("Não existe empréstimos disponíveis");

        return emprestaveis;
    }

    /**
     * recebe dados do item que o usuario quer pegar emprestado
     * 
     * @param usuarioService servico de usuario
     * @throws Exception
     */
    public static void escolherItemParaEmprestimo(UsuarioService usuarioService)  throws ItemIndisponivelException, ItemNaoEmprestavelException,
            EmprestimoLimiteException, DevolucaoDoEmprestimoException, UsuarioAutenticadoException {

            Usuario usuario = obterUsuarioCadastrado(usuarioService);
            ItemEmprestavelService itemEmprestavelService = new ItemEmprestavelService(usuarioService.getBiblioteca());

            System.out.print("Escolha o id do item que você quer pegar emprestado: ");
            int choice = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            itemEmprestavelService.emprestar(choice, usuario);

    }

    /**
     * Mostra itens emprestados do usuario e recebe dados para devolucao dos itens
     * 
     * @param usuarioService servico de usuario
     * @throws Exception
     */
    public static void obterDadosParaDevolucao(UsuarioService usuarioService) throws ItemIndisponivelException, DevolucaoDoEmprestimoException,
            ItemNaoEmprestavelException, UsuarioAutenticadoException {

            Usuario usuario = obterUsuarioCadastrado(usuarioService);

            List<Emprestavel> emprestaveis = usuario.getItensEmprestados();

            System.out.println("Quantidade de itens emprestados por você: " + emprestaveis.size());

            System.out.println("Seu histórico de empréstimos:");
            boolean temEmprestado = false;
            for (Emprestavel emprestavel : usuario.getItensEmprestados()) {
                
                if(emprestavel.getStatusEmprestimo() == StatusEmprestimo.EMPRESTADO) {
                    temEmprestado = true;
                    System.out.println(
                        "ID: " + emprestavel.getId() + " | Status do empréstimo: " + emprestavel.getStatusEmprestimo());
                }
            }

            if(!temEmprestado)
                System.out.println("Nenhum item emprestado!");


            System.out.print("Digite o ID do item que deseja devolver (ou 0 para sair): ");
            int emprestavelId = InputScannerUtil.scanner.nextInt();

            if (emprestavelId == 0) {
                return;
            }

            ItemEmprestavelService itemEmprestavelService = new ItemEmprestavelService(usuarioService.getBiblioteca());

            itemEmprestavelService.devolver(emprestavelId, usuario);

    }   

    /**
     * recebe dados para exclusao de usuario
     *
     * @param usuarioService servico de usuario
     */
    public static void obterDadosDeExclusao(UsuarioService usuarioService) {
        try {
            Usuario usuario = obterUsuarioCadastrado(usuarioService);
            usuarioService.deletar(usuario);
            System.out.println("Usuário deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * recebe dados de login de usuario
     *
     * @param usuarioService servico de usuario
     */
    public static Usuario obterUsuarioCadastrado(UsuarioService usuarioService) throws UsuarioAutenticadoException { // login
        System.out.print("\nInforme sua senha: ");
        String senha = InputScannerUtil.scanner.nextLine();
        System.out.print("Informe seu email: ");
        String email = InputScannerUtil.scanner.nextLine();

        return usuarioService.verificarSenhaEmail(senha, email);
    }

}
