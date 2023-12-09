package main.java.views.inputs;

import main.java.enums.*;
import main.java.exceptions.*;
import main.java.models.Usuario;
import main.java.models.UsuarioAdaptarImpl;
import main.java.models.itens.Emprestavel;
import main.java.models.itens.Item;
import main.java.services.ItemEmprestavelService;
import main.java.services.UsuarioService;
import main.java.utils.DataUtil;
import main.java.utils.InputScannerUtil;
import main.java.utils.ObjectFactoryUtil;
import main.java.views.menus.UsuarioMenu;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

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


    public static void imprimirSugestao(UsuarioService usuarioService) {
        try{
            Usuario usuario = obterUsuarioCadastrado(usuarioService);
            usuarioService.setUsuario(usuario);

            System.out.println("\nAjude-nos a sugerir itens! ");

            TimeUnit.SECONDS.sleep(2);

            UsuarioAdaptarImpl usuarioAdaptar = UsuarioMenu.menuCategoriaInterese(usuarioService);
            definirCursoPorMenu(usuarioAdaptar);

            System.out.println("\n");
            System.out.println("\n");
            System.out.println("A lista a seguir é preparada com base no seu histórico de empréstimos, suas " +
                    "declarações de interesse e a área pela qual você pertence");

            TimeUnit.SECONDS.sleep(2);

            System.out.println("Aguarde mais um pouquinho...");

            TimeUnit.SECONDS.sleep(2);

            System.out.println("Quase tudo pronto...");

            TimeUnit.SECONDS.sleep(2);

            System.out.println("Aqui está sua lista de sugestões:");

            Set<Item> itensSugeridos = usuarioService.fazerSugestao(usuarioAdaptar);

            List<Item> itens = new ArrayList<>(itensSugeridos);
            for (Item item: itensSugeridos) {
                System.out.println(item);
                TimeUnit.SECONDS.sleep(2);
            }
            System.out.println("Aperte Enter caso queira sair da lista de sugestões");
            InputScannerUtil.scanner.nextLine();

        } catch (UsuarioAutenticadoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (SugestaoException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public static void definirCursoPorMenu(UsuarioAdaptarImpl usuarioAdaptar) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha a área do seu curso:");
        int i = 1;

        for (AreaCursoSuperior area : AreaCursoSuperior.values()) {
            System.out.println(i + ". " + area.name());
            i++;
        }
        System.out.print("Escolha: ");
        int opcaoEscolhida = scanner.nextInt();

        if (opcaoEscolhida >= 1 && opcaoEscolhida <= AreaCursoSuperior.values().length) {
            AreaCursoSuperior areaEscolhida = AreaCursoSuperior.values()[opcaoEscolhida - 1];
            usuarioAdaptar.setCurso(areaEscolhida);
        } else {
            System.out.println("Opção inválida.");
        }
    }



    public static UsuarioAdaptarImpl infoSugerirCd(UsuarioAdaptarImpl usuarioAdaptar) {
        FiltroPesquisa filtroPesquisa = FiltroPesquisa.CD;
        CategoriaInteresse categoriaInteresse = CategoriaInteresse.ARTISTA;
        List<String> interesses = new ArrayList<>();
        String interesse;

        System.out.println("Quais artistas de CD você curte (ou digite 'fim' para encerrar): ");
        int i = 1;

        while (true) {
            System.out.print(i + " : ");
            interesse = InputScannerUtil.scanner.nextLine();
            if ("fim".equalsIgnoreCase(interesse)) {
                break;
            }
            interesses.add(interesse);
            i++;
        }

        adicionarValorAoMapaDeInteresse(usuarioAdaptar, filtroPesquisa,
                categoriaInteresse, interesses);

        return usuarioAdaptar;
    }

    public static UsuarioAdaptarImpl infoSugerirDvd(UsuarioAdaptarImpl usuarioAdaptar) {
        FiltroPesquisa filtroPesquisa = FiltroPesquisa.DVD;
        CategoriaInteresse categoriaInteresse1 = CategoriaInteresse.GENERO;
        CategoriaInteresse categoriaInteresse2 = CategoriaInteresse.DIRETOR;
        List<String> interesses1 = new ArrayList<>();
        List<String> interesses2 = new ArrayList<>();
        String interesse1;
        String interesse2;

        System.out.println("Quais gêneros de filme você curte (digite 'fim' para encerrar): ");

        int i = 1;
        while (true) {
            System.out.print(i + " : ");
            interesse1 = InputScannerUtil.scanner.nextLine();

            if ("fim".equalsIgnoreCase(interesse1)) {
                break;
            }

            interesses1.add(interesse1);
            i++;
        }
        adicionarValorAoMapaDeInteresse(usuarioAdaptar, filtroPesquisa,
                categoriaInteresse1, interesses1);

        System.out.println("\n Quais diretores de filmes você mais curte (digite 'fim' para encerrar): ");

        i = 1;

        while (true) {
            System.out.print(i + " : ");
            interesse2 = InputScannerUtil.scanner.nextLine();

            if ("fim".equalsIgnoreCase(interesse2)) {
                break;
            }

            interesses2.add(interesse2);
            i++;
        }
        adicionarValorAoMapaDeInteresse(usuarioAdaptar, filtroPesquisa,
                categoriaInteresse2, interesses2);

        return usuarioAdaptar;
    }

    public static UsuarioAdaptarImpl infoSugerirLivro(UsuarioAdaptarImpl usuarioAdaptar) {
        FiltroPesquisa filtroPesquisa = FiltroPesquisa.LIVRO;
        CategoriaInteresse categoriaInteresse1 = CategoriaInteresse.GENERO;
        CategoriaInteresse categoriaInteresse2 = CategoriaInteresse.AUTOR;
        List<String> interesses1 = new ArrayList<>();
        List<String> interesses2 = new ArrayList<>();
        String interesse1;
        String interesse2;

        System.out.println("Quais gêneros de livro você curte (digite 'fim' para encerrar): ");

        int i = 1;
        while (true) {
            System.out.print(i + " : ");
            interesse1 = InputScannerUtil.scanner.nextLine();

            if ("fim".equalsIgnoreCase(interesse1)) {
                break;
            }

            interesses1.add(interesse1);
            i++;
        }
        adicionarValorAoMapaDeInteresse(usuarioAdaptar, filtroPesquisa,
                categoriaInteresse1, interesses1);

        System.out.println("\n Quais autores de livro você curte (digite 'fim' para encerrar): ");

        i = 1;
        while (true) {
            System.out.print(i + " : ");
            interesse2 = InputScannerUtil.scanner.nextLine();

            if ("fim".equalsIgnoreCase(interesse2)) {
                break;
            }

            interesses2.add(interesse2);
            i++;
        }
        adicionarValorAoMapaDeInteresse(usuarioAdaptar, filtroPesquisa,
                categoriaInteresse2, interesses2);

        return usuarioAdaptar;
    }

    private static void adicionarValorAoMapaDeInteresse(
            UsuarioAdaptarImpl usuarioAdaptar, FiltroPesquisa filtroPesquisa, CategoriaInteresse categoriaInteresse,
            List<String> interesses) {

        Usuario usuario = usuarioAdaptar.getUsuario();
        Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>> mapaFiltro =
                usuarioAdaptar.getMapaDeInteresse().computeIfAbsent(usuario, u -> new HashMap<>());

        // Acesse o mapa interno relacionado ao filtro de pesquisa
        mapaFiltro.computeIfAbsent(filtroPesquisa, fp -> new HashMap<>())
                .computeIfAbsent(categoriaInteresse, ci -> new ArrayList<>())
                .addAll(interesses);
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


    public static void obterDadosParaSugestao(UsuarioService usuarioService) throws UsuarioAutenticadoException {
        Usuario usuario = obterUsuarioCadastrado((usuarioService));
        System.out.println("Baseado em seus interesses, podemos fazer as seguintes sugestões de itens:");
        System.out.println("\n");
        // Chama função de sugestão
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
