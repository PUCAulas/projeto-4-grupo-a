package main.java.services;

import main.java.enums.Perfil;
import main.java.models.Biblioteca;
import main.java.models.Usuario;

import java.time.LocalDate;


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
    public void criar(String nome, String email, String senha, LocalDate dataNascimento, Perfil perfil) throws Exception {

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
    public Usuario verificarSenhaEmail(String senha, String email) throws Exception {
        Usuario u = null;
        for (Usuario usuario : biblioteca.getUsuarios()) {
            if (usuario.getSenha().equals(senha) && usuario.getEmail().equals(email)) {
                u = usuario;
                break;
            }
        }

        if (u == null)
            throw new Exception("Usuário não existe!");

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
