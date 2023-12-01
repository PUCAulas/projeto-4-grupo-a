package main.java.services;

import main.java.enums.StatusClassificacao;
import main.java.enums.StatusEmprestimo;
import main.java.exceptions.*;
import main.java.interfaces.GerenciarEmprestimo;
import main.java.models.Biblioteca;
import main.java.models.Estoque;
import main.java.models.Usuario;
import main.java.models.itens.*;
import main.java.userAdapter.UsuarioAdaptor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemEmprestavelService implements GerenciarEmprestimo {

    private Biblioteca biblioteca;
    private Emprestavel emprestavel;
    private Estoque estoque;

    /**
     * Construtor padrao de ItemEmprestavelService
     */
    public ItemEmprestavelService() {
    }

    /**
     * Construtor padrao de ItemEmprestavelService, com biblioteca
     */
    public ItemEmprestavelService(Biblioteca biblioteca) {
        this.emprestavel = null;
        this.biblioteca = biblioteca;
        this.estoque = biblioteca.getEstoque();
    }

     /**
     * Cadastra um DVD
     *
     * @param titulo                titulo do DVD
     * @param dataPublicacao        data de publicacao do DVD
     * @param statusClassificacao   classificacao da revista
     * @param statusEmprestimo      status empréstimo
     * @param diretor               diretor da produção DVD
     * @param duracao               duração do DVD
     * @param idioma                idioma do DVD
     * @param sinopse               sinopse do DVD
     * @param genero                gênero do DVD
     */
    public void criarDVD(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
                         StatusEmprestimo statusEmprestimo, String diretor, Duration duracao, String idioma, String sinopse,
                         String genero) {

        Emprestavel emprestavel = this.getEmprestimo();

        if (emprestavel instanceof DVD) {
            DVD dvd = (DVD) emprestavel;

            dvd.setTitulo(titulo);
            dvd.setDataPublicacao(dataPublicacao);
            dvd.setStatusClassificacao(statusClassificacao);
            dvd.setStatusEmprestimo(statusEmprestimo);
            dvd.setDiretor(diretor);
            dvd.setDuracao(duracao);
            dvd.setIdioma(idioma);
            dvd.setSinopse(sinopse);
            dvd.setGenero(genero);

            biblioteca.getEstoque().addItem(dvd);
        }

        this.setEmprestimo(null);
    }


   /**
     * Cadastra um CD
     *
     * @param titulo                titulo do CD
     * @param dataPublicacao        data de publicacao do CD
     * @param statusClassificacao   classificacao da revista
     * @param statusEmprestimo      status empréstimo
     * @param artista               artista do CD
     * @param duracao               duração do CD
     * @param faixas                lista de faixas do CD
     */
    public void criarCD(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
            StatusEmprestimo statusEmprestimo, String artista, Duration duracao, List<String> faixas) {

        Emprestavel emprestavel = this.getEmprestimo();

        if (emprestavel instanceof DVD) {
            CD cd = (CD) emprestavel;
            cd.setTitulo(titulo);
            cd.setDataPublicacao(dataPublicacao);
            cd.setStatusClassificacao(statusClassificacao);
            cd.setStatusEmprestimo(statusEmprestimo);
            cd.setArtista(artista);
            cd.setDuracao(duracao);
            cd.setFaixas(faixas);
            biblioteca.getEstoque().addItem(cd);
        }

        this.setEmprestimo(null);
    }

    /**
     * Cadastra um Livro
     *
     * @param titulo                titulo do livro
     * @param dataPublicacao        data de publicacao do livro
     * @param statusClassificacao   classificacao do livro
     * @param statusEmprestimo      status empréstimo do livro
     * @param autor                 autor do livro 
     * @param duracao               duração do livro
     * @param idioma                idioma do livro
     * @param sinopse               sinopse do livro
     * @param genero                gênero do livro
     */
    public void criarLivro(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
            StatusEmprestimo statusEmprestimo, String autor,
            int numeroPaginas, String editora, String edicao, String volume, String idioma, String genero,
            String sinopse) {

        Livro livro = (Livro) emprestavel;
        Emprestavel emprestavel = this.getEmprestimo();

        if(emprestavel instanceof Livro) {
            livro.setTitulo(titulo);
            livro.setDataPublicacao(dataPublicacao);
            livro.setStatusClassificacao(statusClassificacao);
            livro.setStatusEmprestimo(statusEmprestimo);
            livro.setAutor(autor);
            livro.setNumeroPaginas(numeroPaginas);
            livro.setEditora(editora);
            livro.setEdicao(edicao);
            livro.setVolume(volume);
            livro.setIdioma(idioma);
            livro.setGenero(genero);
            livro.setSinopse(sinopse);
            biblioteca.getEstoque().addItem(livro);
        }

        this.setEmprestimo(null);
    }


     /**
     * Atualiza DVD já cadastrado
     *
     * @param titulo                titulo do DVD
     * @param dataPublicacao        data de publicacao do DVD
     * @param statusClassificacao   classificacao da revista
     * @param statusEmprestimo      status empréstimo
     * @param diretor               diretor da produção DVD
     * @param duracao               duração do DVD
     * @param idioma                idioma do DVD
     * @param sinopse               sinopse do DVD
     * @param genero                gênero do DVD
     */ 
    public void atualizarDVD(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
            StatusEmprestimo statusEmprestimo, String diretor, Duration duracao, String idioma, String sinopse,
            String genero) {

        ((DVD) emprestavel).setTitulo(titulo);
        ((DVD) emprestavel).setDataPublicacao(dataPublicacao);
        ((DVD) emprestavel).setStatusClassificacao(statusClassificacao);
        ((DVD) emprestavel).setStatusEmprestimo(statusEmprestimo);
        ((DVD) emprestavel).setDiretor(diretor);
        ((DVD) emprestavel).setDuracao(duracao);
        ((DVD) emprestavel).setIdioma(idioma);
        ((DVD) emprestavel).setSinopse(sinopse);
        ((DVD) emprestavel).setGenero(genero);
    }

     /**
     * Atualiza CD já cadastrado
     *
     * @param titulo                titulo do CD
     * @param dataPublicacao        data de publicacao do CD
     * @param statusClassificacao   classificacao da revista
     * @param statusEmprestimo      status empréstimo
     * @param artista               artista do CD
     * @param duracao               duração do CD
     * @param faixas                lista de faixas do CD
     */
    public void atualizarCD(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
            StatusEmprestimo statusEmprestimo, String artista, Duration duracao, List<String> faixas) {

        ((CD) emprestavel).setTitulo(titulo);
        ((CD) emprestavel).setDataPublicacao(dataPublicacao);
        ((CD) emprestavel).setStatusClassificacao(statusClassificacao);
        ((CD) emprestavel).setStatusEmprestimo(statusEmprestimo);
        ((CD) emprestavel).setArtista(artista);
        ((CD) emprestavel).setDuracao(duracao);
        ((CD) emprestavel).setFaixas(faixas);
    }

  /**
     * Atualiza livro já cadastrado
     *
     * @param titulo                titulo do livro
     * @param dataPublicacao        data de publicacao do livro
     * @param statusClassificacao   classificacao do livro
     * @param statusEmprestimo      status empréstimo do livro
     * @param autor                 autor do livro 
     * @param duracao               duração do livro
     * @param idioma                idioma do livro
     * @param sinopse               sinopse do livro
     * @param genero                gênero do livro
     */
    public void atualizarLivro(String titulo, LocalDate dataPublicacao, StatusClassificacao statusClassificacao,
            StatusEmprestimo statusEmprestimo, String autor, String editora, String edicao, String volume,
            String idioma, String genero, String sinopse, int numeroPaginas) {

        ((Livro) emprestavel).setTitulo(titulo);
        ((Livro) emprestavel).setDataPublicacao(dataPublicacao);
        ((Livro) emprestavel).setStatusClassificacao(statusClassificacao);
        ((Livro) emprestavel).setStatusEmprestimo(statusEmprestimo);
        ((Livro) emprestavel).setAutor(autor);
        ((Livro) emprestavel).setEditora(editora);
        ((Livro) emprestavel).setEdicao(edicao);
        ((Livro) emprestavel).setVolume(volume);
        ((Livro) emprestavel).setIdioma(idioma);
        ((Livro) emprestavel).setGenero(genero);
        ((Livro) emprestavel).setSinopse(sinopse);
        ((Livro) emprestavel).setNumeroPaginas(numeroPaginas);
    }

    /**
     * Deleta um item emprestavel do estoque
     * 
     * @param itemEmprestavel 
     */
    public void deletar(Item itemEmprestavel) {

        biblioteca.getEstoque().getItens().remove(itemEmprestavel);
    }

    /**
     * Lista todos os itens
     */
    public void listar() {
        List<Item> itens = biblioteca.getEstoque().getItens();

        if (itens != null) {
            for (Item i : itens) {
                if (i instanceof Emprestavel) {
                    System.out.println(i);
                }
            }
        } else {
            System.out.println("Nenhum item encontrado no estoque.");
        }
    }

    /**
     * Lista os emprestáveis disponíveis na biblioteca
     * 
     * @param biblioteca
     * @throws Exception
     */
    public List<Emprestavel> listarDisponiveis(Biblioteca biblioteca) throws Exception {
        List<Item> itens = biblioteca.getEstoque().getItens();
        List<Emprestavel> disponiveis = new ArrayList<>();

        if (itens == null) {
            throw new Exception("Nenhum item encontrado no estoque.");
        }

        for (Item i : itens) {
            if (i instanceof Emprestavel) {
                if (((Emprestavel) i).getStatusEmprestimo() == StatusEmprestimo.DISPONIVEL)
                    disponiveis.add((Emprestavel) i);
            }
        }

        return disponiveis;
    }


    /**
     * Empresta um item ao usuário
     * 
     * @param id
     * @param usuario
     */
    public void emprestar(int id, Usuario usuario) throws ItemIndisponivelException, ItemNaoEmprestavelException,
            EmprestimoLimiteException, DevolucaoDoEmprestimoException {

        Emprestavel emprestavel = encontrarEmprestavelPorId(id);
        verificarUnicoEmprestavelNoEstoque();

        usuario.verificarLimiteParaEmprestimo();
        usuario.emprestavelEmAtrasoDoUsuario();

        this.setEmprestimo(emprestavel);

        verificarDisponibilidadeParaEmprestimo();

        this.getEmprestimo().setStatusEmprestimo(StatusEmprestimo.EMPRESTADO);
        this.getEmprestimo().setDataEmprestimo(LocalDate.now());
        this.getEmprestimo().setNumEmprestimos(emprestavel.getNumEmprestimos() + 1);

        usuario.setQtdItensEmprestadosAtualmente(usuario.getQtdItensEmprestadosAtualmente() + 1);
        usuario.addEmprestimo(this.getEmprestimo());

        this.setEmprestimo(null);

    }


     /**
     * Verifica a disponibilidade da instância do item emprestável na biblioteca
     * 
     */
    public void verificarDisponibilidadeParaEmprestimo() throws ItemIndisponivelException {

        if(this.getEmprestimo().getStatusEmprestimo() == StatusEmprestimo.EMPRESTADO)
                throw new ItemIndisponivelException("Este item já está emprestado!");

    }


     /**
     * Encontrar um item específico pelo seu id no estoque da biblioteca
     * 
     * @param id
     */
   public Emprestavel encontrarEmprestavelPorId(int id) throws ItemNaoEmprestavelException {
    Optional<Emprestavel> emprestavelOptional = biblioteca.getEstoque().getItens()
            .stream()
            .filter(x -> x.getId() == id)
            .filter(Emprestavel.class::isInstance)
            .map(Emprestavel.class::cast)
            .findFirst();

    Emprestavel emprestavel = emprestavelOptional.orElse(null);

    if (emprestavel == null) {
        throw new ItemNaoEmprestavelException("O item não é emprestável!");
    }

    return emprestavel;
}



     /**
     * Verifica se só tem um item disponível no estoque da biblioteca
     * @throws Exception
     */
    public void verificarUnicoEmprestavelNoEstoque() throws ItemIndisponivelException {

        long emprestaveisEmEstoque = this.getBiblioteca().getEstoque().getItens()
                .stream()
                .filter(x -> x instanceof Emprestavel &&
                        ((Emprestavel) x).getStatusEmprestimo() == StatusEmprestimo.DISPONIVEL)
                .count();

        if(emprestaveisEmEstoque == 1)
            throw new ItemIndisponivelException("Não pode fazer empréstimo, pois só tem 1 item na biblioteca que não foi emprestado");

    }


     /**
     * Devolve um item do usuário
     * @param id
     * @param usuario
     * @throws Exception
     */
    public void devolver(int id, Usuario usuario) throws ItemIndisponivelException, DevolucaoDoEmprestimoException,
            ItemNaoEmprestavelException {

        Emprestavel emprestavel = usuario.acharEmprestavelPorId(id);

        Emprestavel emprestavelNoEstoque = this.encontrarEmprestavelPorId(id);

        if(emprestavel != emprestavelNoEstoque)
            throw new ItemIndisponivelException("O item informado não existe!");

        if(emprestavelNoEstoque.getStatusEmprestimo() != StatusEmprestimo.EMPRESTADO)
            throw new DevolucaoDoEmprestimoException("O item informado já está disponível");

        emprestavelNoEstoque.setStatusEmprestimo(StatusEmprestimo.DISPONIVEL);
        emprestavelNoEstoque.setDataEmprestimo(null);

        usuario.setQtdItensEmprestadosAtualmente(usuario.getQtdItensEmprestadosAtualmente() - 1);

    }


    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Emprestavel getEmprestimo() {
        return emprestavel;
    }

    public void setEmprestimo(Emprestavel emprestavel) {
        this.emprestavel = emprestavel;
    }




}
