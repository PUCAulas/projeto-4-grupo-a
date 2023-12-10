package main.java.services;

import main.java.enums.*;
import main.java.exceptions.SugestaoException;
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

    public Set<Item> fazerSugestao(UsuarioAdaptarImpl usuarioAdaptar) throws SugestaoException {
        Set<Item> sugestoes = new HashSet<>();

        sugestoes.addAll(addSugerirPeloCurso(usuarioAdaptar));
        sugestoes.addAll(addSugerirPorCategoriaInteresse(usuarioAdaptar));
        sugestoes.addAll(addSugerirPeloHistorico());

        if (sugestoes.isEmpty()) {
            throw new SugestaoException("Não há nenhuma sugestão para você");
        }


        return sugestoes;
    }

    public Set<Item> addSugerirPeloCurso(UsuarioAdaptarImpl usuarioAdaptar) {
        AreaCursoSuperior areaCursoSuperior = usuarioAdaptar.getCurso();
        Set<Item> sugestoes = new HashSet<>();
        List<String> generosInteresse;
        List<Item> itens = biblioteca.getEstoque().getItens();

        if (areaCursoSuperior == AreaCursoSuperior.CIENCIAS_EXATAS) {
            generosInteresse = Arrays.asList("Ficção Científica", "Divulgação Científica", "Matemática e" +
                    " Lógica", "História da Ciência", "Biografia de Cientista", "Documentário Científico", "Ciencia");
            for (Item item : itens) {
                if ((item instanceof Livro livro && generosInteresse.contains(livro.getGenero())) ||
                        (item instanceof DVD dvd && generosInteresse.contains(dvd.getGenero()))) {
                    sugestoes.add(item);
                }
            }
        } else if (areaCursoSuperior == AreaCursoSuperior.CIENCIAS_SOCIAIS || areaCursoSuperior == AreaCursoSuperior.ARTES_E_DESIGN) {
            generosInteresse = Arrays.asList("Romance", "Ficção", "Policial", "Biografia", "Pol" +
                    "ítica", "Filosofia", "Sociologia", "Psicologia", "Drama", "Suspense");

            for (Item item : itens) {
                if ((item instanceof Livro livro && generosInteresse.contains(livro.getGenero())) ||
                        (item instanceof DVD dvd && generosInteresse.contains(dvd.getGenero()))) {
                    sugestoes.add(item);
                }
            }
        } else if(areaCursoSuperior == AreaCursoSuperior.CIENCIAS_DA_SAUDE) {
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

    public Set<Item> addSugerirPorCategoriaInteresse(UsuarioAdaptarImpl usuarioAdaptar) {

        Map<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> mapaDeIntesse =
                usuarioAdaptar.getMapaDeInteresse();
        Set<Item> itensSugeridos = new HashSet<>();

        for (Map.Entry<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> entryUsuario :
                mapaDeIntesse.entrySet()) {
            Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>> values = entryUsuario.getValue();

            for (Map.Entry<FiltroPesquisa, Map<CategoriaInteresse, List<String>>> value : values.entrySet()) {

                FiltroPesquisa filtroPesquisa = value.getKey();
                Map<CategoriaInteresse, List<String>> interesseListMap = value.getValue();

                for (Map.Entry<CategoriaInteresse, List<String>> interesse : interesseListMap.entrySet()) {
                    CategoriaInteresse categoriaInteresse = interesse.getKey();
                    List<String> interesseList = interesse.getValue();
                    itensSugeridos.addAll(catInteresse(filtroPesquisa, categoriaInteresse, interesseList));
                }
            }
        }

        return itensSugeridos;
    }



    public Set<Item> catInteresse(FiltroPesquisa filtroPesquisa, CategoriaInteresse categoriaInteresse,
                                  List<String> interesses) {

        List<Item> itens = this.getBiblioteca().getEstoque().getItens();
        Set<Item> interessaUsuario = new HashSet<>();

        String atributoInteresse = null;

        for (Item item : itens) {

            if (!verificarTipoItem(item, filtroPesquisa)) {
                continue;
            }

            atributoInteresse = obterAtributoInteresse(filtroPesquisa, categoriaInteresse);

            if (atributoInteresse != null) {

                String valorAtributo = obterValorAtributo(item, atributoInteresse);

                for (String interesse : interesses) {

                    if (valorAtributo != null && valorAtributo.equals(interesse)) {
                        interessaUsuario.add(item);
                        break;
                    }
                }
            }
        }

        return interessaUsuario;
    }

    private boolean verificarTipoItem(Item item, FiltroPesquisa filtroPesquisa) {
        switch (filtroPesquisa) {
            case LIVRO:
                return item instanceof Livro;
            case DVD:
                return item instanceof DVD;
            case CD:
                return item instanceof CD;
            default:
                return false;
        }
    }

    private String obterAtributoInteresse(FiltroPesquisa filtroPesquisa, CategoriaInteresse categoriaInteresse) {
        switch (filtroPesquisa) {
            case LIVRO:
                return (categoriaInteresse == CategoriaInteresse.AUTOR) ? "autor" :
                        (categoriaInteresse == CategoriaInteresse.GENERO) ? "genero" : null;
            case DVD:
                return (categoriaInteresse == CategoriaInteresse.DIRETOR) ? "diretor" :
                        (categoriaInteresse == CategoriaInteresse.GENERO) ? "genero" : null;
            case CD:
                return (categoriaInteresse == CategoriaInteresse.ARTISTA) ? "artista" : null;
            default:
                return null;
        }
    }

    private String obterValorAtributo(Item item, String atributo) {
        switch (atributo) {
            case "autor":
                return (item instanceof Livro livro) ? livro.getAutor() : null;
            case "genero":
                return (item instanceof Livro livro) ? livro.getGenero() :
                        (item instanceof DVD dvd) ? dvd.getGenero() : null;
            case "diretor":
                return (item instanceof DVD dvd) ? dvd.getDiretor() : null;
            case "artista":
                return (item instanceof CD cd) ? cd.getArtista() : null;
            default:
                return null;
        }
    }


    public Set<Item> addSugerirPeloHistorico() {
        Set<Item> sugestoes = new HashSet<>();
        List<Emprestavel> historicoItems = usuario.getEmprestimos();

        for (Item item : historicoItems) {
            Emprestavel convertItem = (Emprestavel) item;
            StatusEmprestimo statusEmprestimo = convertItem.getStatusEmprestimo();

            if(statusEmprestimo == StatusEmprestimo.DISPONIVEL)
               sugestoes.add(item);

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
