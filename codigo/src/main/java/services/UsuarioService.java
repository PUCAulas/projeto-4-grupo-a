package main.java.services;

import main.java.enums.AreaCursoSuperior;
import main.java.enums.CategoriaInteresse;
import main.java.enums.FiltroPesquisa;
import main.java.enums.Perfil;
import main.java.exceptions.UsuarioAutenticadoException;
import main.java.models.Biblioteca;
import main.java.models.Usuario;
import main.java.models.UsuarioAdaptarImpl;
import main.java.models.itens.CD;
import main.java.models.itens.DVD;
import main.java.models.itens.Emprestavel;
import main.java.models.itens.Item;
import main.java.models.itens.Livro;
import main.java.utils.InputScannerUtil;

import java.time.LocalDate;
import java.util.*;

public class UsuarioService {

    private Biblioteca biblioteca;
    private Usuario usuario;

    /**
     * Construtor padrao de UsuarioService, com Biblioteca
     *
     * @param biblioteca
     */
    public UsuarioService(Biblioteca biblioteca) {
        this.usuario = null;
        this.biblioteca = biblioteca;
    }

    /**
     * Cria nova instancia de UsuarioService
     *
     * @param nome           nome do usuario
     * @param email          email do usuario
     * @param senha          senha do usuario
     * @param dataNascimento data de nascimento do usuario
     * @param perfil         perfil do usuario
     * @throws Exception lanca excecao caso o email inserido ja exista
     */
    public void criar(String nome, String email, String senha, LocalDate dataNascimento, Perfil perfil)
            throws Exception {

        verificarEmailUnico(email);

        this.usuario.setPerfil(perfil);
        this.usuario.setNome(nome);
        this.usuario.setEmail(email);
        this.usuario.setSenha(senha);
        this.usuario.setDataNascimento(dataNascimento);
        this.biblioteca.addUsuario(this.usuario);

        this.setUsuario(null);
    }

    /**
     * Verifica se o email inserido ja existe
     *
     * @param email email de referencia
     * @throws Exception lanca excecao caso o email inserido ja exista
     */
    private void verificarEmailUnico(String email) throws Exception {
        boolean existeEmail = biblioteca.getUsuarios().stream()
                .anyMatch(usuario -> usuario.getEmail().equals(email));

        if (existeEmail)
            throw new Exception("E-mail já existe!");
    }

    /**
     * Atualiza informacoes do usuario
     *
     * @param name           nome do usuario
     * @param email          email do usuario
     * @param senha          senha do usuario
     * @param dataNascimento data de nascimento do usuario
     */
    public void atualizar(String name, String email, String senha, LocalDate dataNascimento) {

        this.getBiblioteca().removeUsuario(this.getUsuario());
        this.usuario.setNome(name);
        this.usuario.setEmail(email);
        this.usuario.setSenha(senha);
        this.usuario.setDataNascimento(dataNascimento);

        this.getBiblioteca().addUsuario(usuario);
    }

    /**
     * Delete usuario
     *
     * @param usuario usuario de referencia
     */
    public void deletar(Usuario usuario) {

        this.getBiblioteca().getUsuarios().remove(usuario);
    }

    /**
     * Autenticacao do usuario
     *
     * @param senha senha do usuario
     * @param email email do usuario
     * @return usuario
     * @throws Exception lanca excecao caso o usuario nao esteja cadastrado
     */
    public Usuario verificarSenhaEmail(String senha, String email) throws UsuarioAutenticadoException {
        Usuario u = null;
        for (Usuario usuario : biblioteca.getUsuarios()) {
            if (usuario.getSenha().equals(senha) && usuario.getEmail().equals(email)) {
                u = usuario;
                break;
            }
        }

        if (u == null)
            throw new UsuarioAutenticadoException("Usuário não existe!");

        return u;
    }

    /**
     * Lista usuarios cadastrados
     */
    public void listar() {
        for (Usuario u : biblioteca.getUsuarios()) {
            System.out.println(u);
        }
    }

    /**
     * Encontra um usuario pelo id
     *
     * @param id id de referencia
     * @return usuario
     * @throws Exception lanca excecao caso o usuario nao esteja cadastrado
     */
    public Usuario pesquisarUsuarioPorId(int id) throws Exception {
        Usuario u = null;

        for (Usuario usuario : biblioteca.getUsuarios()) {
            if (usuario.getId() == id)
                u = usuario;
        }

        if (u == null)
            throw new Exception("Usuário não existe!");

        return u;
    }

    /**
     * Verifica se usuario e admin
     * 
     * @param senha senha do usuario
     * @param email email do usuario
     * @return usuario
     * @throws Exception lanca excecao caso o usuario nao esteja cadastrado
     */
    public Usuario verificarAdm(String senha, String email) throws Exception {
        Usuario u = null;

        for (Usuario usuario : biblioteca.getUsuarios()) {
            if (usuario.getPerfil() == Perfil.ADM) {
                if (usuario.getSenha().equals(senha) && usuario.getEmail().equals(email))
                    u = usuario;
            }

        }

        if (u == null) {
            throw new Exception("Acesso não permitido (somente para administrador)!");
        }

        return u;
    }

    public void fazerSugestao(UsuarioService usuarioService) {
        Set<Item> sugestoes = new HashSet<>();
        
        // Considerar categorias de interesse do usuário
        // Considerar o histórico do usuário com seu atributo "emprestaveis"
        // Considerar a área de curso do usuário - FEITO

        // Recomendar no mínimo 3 itens

        // Recomendação com base no curso:

        // sugestoes = addSugerirPeloCurso(new UsuarioAdaptarImpl(usuarioService.getUsuario()));

        while (true) {
            System.out.println("\nEscolha como deseja receber sugestões de itens:");
            System.out.println("1. Por curso");
            System.out.println("2. Por interesse");
            System.out.println("3. Pelo histórico de empréstimos");
            System.out.print("\nOpção: ");
    
            int escolha = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();
    
            if (escolha == 8) {
                System.out.println("Voltando ao menu principal...\n");
                break;
            }
    
            switch (escolha) {
                case 1:
                    sugestoes = addSugerirPeloCurso(new UsuarioAdaptarImpl(usuarioService.getUsuario()));
                    break;
                case 2:
                    sugestoes = addSugerirPorInteresse(new UsuarioAdaptarImpl(usuarioService.getUsuario()));
                    break;
                case 3:
                sugestoes = usuarioService.addSugerirPeloHistorico();
                break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
    
            // Aqui você pode fazer o que quiser com o conjunto de sugestões,
            // por exemplo, imprimir os itens sugeridos.
            System.out.println("Itens sugeridos:");
            for (Item sugestao : sugestoes) {
                System.out.println(sugestao);
            }
        }

    }

    public Set<Item> addSugerirPeloCurso(UsuarioAdaptarImpl usuarioAdaptar) {
        AreaCursoSuperior areaCursoSuperior = usuarioAdaptar.getCurso();
        Set<Item> sugestoes = new HashSet<>();
        List<String> generosInteresse;
        List<Item> itens = biblioteca.getEstoque().getItens();

        if (areaCursoSuperior == AreaCursoSuperior.CIENCIAS_EXATAS) {
            generosInteresse = Arrays.asList("Ficção Científica", "Divulgação Científica", "Matemática e" +
                    " Lógica", "História da Ciência", "Biografia de Cientista", "Documentário Científico");
            for (Item item : itens) {
                if ((item instanceof Livro livro && generosInteresse.contains(livro.getGenero())) ||
                        (item instanceof DVD dvd && generosInteresse.contains(dvd.getGenero()))) {
                    sugestoes.add(item);
                }
            }
        } else if (areaCursoSuperior == AreaCursoSuperior.CIENCIAS_SOCIAIS) {
            generosInteresse = Arrays.asList("Romance", "Ficção", "Policial", "Biografia", "Pol" +
                    "ítica", "Filosofia", "Sociologia", "Psicologia", "Drama", "Suspense");

            for (Item item : itens) {
                if ((item instanceof Livro livro && generosInteresse.contains(livro.getGenero())) ||
                        (item instanceof DVD dvd && generosInteresse.contains(dvd.getGenero()))) {
                    sugestoes.add(item);
                }
            }
        } else {
            generosInteresse = Arrays.asList("Drama Médico", "Biografia", "Saúde", "Ética Médica", "Biologia",
                    "Corpo Humano");

            for (Item item : itens) {
                if ((item instanceof Livro livro && generosInteresse.contains(livro.getGenero())) ||
                        (item instanceof DVD dvd && generosInteresse.contains(dvd.getGenero()))) {
                    sugestoes.add(item);
                }
            }
        }
        return sugestoes;
    }

    public Set<Item> addSugerirPorInteresse(UsuarioAdaptarImpl usuarioAdaptar) {
        Set<Item> sugestoes = new HashSet<>();
        List<String> categoriaInteresse = usuarioAdaptar.getCategoriaInteresse();
        List<Item> itens = biblioteca.getEstoque().getItens();

        for (Item item : itens) {
            if ((item instanceof Livro livro && categoriaInteresse.contains("livro")) ||
                    (item instanceof DVD dvd && categoriaInteresse.contains("dvd"))){
                sugestoes.add(item);
            }
        }
        
        return sugestoes;
    }

    public Set<Item> addSugerirPeloHistorico() {
        Set<Item> sugestoes = new HashSet<>();
        List<Emprestavel> historicoItems = usuario.getEmprestimos();

        for (Item item : historicoItems) {
            if (item instanceof Livro livro) {
                sugestoes.add(item);
            } else if (item instanceof DVD dvd) {
                sugestoes.add(item);
            } else if (item instanceof CD cd) {
                sugestoes.add(item);
            }
        }

        return sugestoes;
    }

    public Biblioteca getBiblioteca() {

        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {

        this.biblioteca = biblioteca;
    }

    public Usuario getUsuario() {

        return usuario;
    }

    public void setUsuario(Usuario usuario) {

        this.usuario = usuario;
    }

}
