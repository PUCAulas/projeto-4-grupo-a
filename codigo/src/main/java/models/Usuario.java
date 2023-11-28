package main.java.models;

import main.java.enums.Perfil;
import main.java.exceptions.DevolucaoDoEmprestimoException;
import main.java.exceptions.EmprestimoLimiteException;
import main.java.exceptions.ItemIndisponivelException;
import main.java.models.itens.Emprestavel;
import main.java.utils.DataUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Usuario {
    
    private static int PROX_ID = 0;
    private final int QTD_MAX_ITENS_EMPRESTADOS = 3;
    private int id;
    private String senha;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private int qtdItensEmprestadosAtualmente;
    private List<Emprestavel> emprestaveis; //histórico de empréstimo
    private Perfil perfil;


    public List<Emprestavel> getItensEmprestados() {
        return emprestaveis;
    }
    

    /**
     * Construtor padrao de usuario
     */
    public Usuario() {
        this.id = PROX_ID++;
        emprestaveis = new ArrayList<>();
    }

    /**
     * Construtor padrao de usuario, com senha, email e data de nascimento
     *
     * @param senha          senha do usuario
     * @param email          email do usuario
     * @param dataNascimento data de nascimento do usuario
     */
    private Usuario(String senha, String email, LocalDate dataNascimento) {
        this();
        this.senha = senha;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    public int getId() {
        return id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getQtdItensEmprestadosAtualmente() {
        return qtdItensEmprestadosAtualmente;
    }

    public void setQtdItensEmprestadosAtualmente(int qtd) {
        qtdItensEmprestadosAtualmente = qtd;
    }

    public List<Emprestavel> getEmprestimos() {
        return emprestaveis;
    }

    public int getQTD_MAX_ITENS_EMPRESTADOS() {
        return QTD_MAX_ITENS_EMPRESTADOS;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    /**
     * Adiciona um novo emprestimo a lista de emprestaveis do usuario
     *
     * @param emprestavel item adicionado
     */
    public void addEmprestimo(Emprestavel emprestavel) {
        this.getEmprestimos().add(emprestavel);
    }

    /**
     * Remove um emprestimo a lista de emprestaveis do usuario
     *
     * @param emprestavel item removido
     */
    public void removerEmprestimo(Emprestavel emprestavel) {
        this.getEmprestimos().remove(emprestavel);
    }

    /**
     * Lista itens emprestados
     *
     * @return lista de itens emprestados
     */
    public String listarEmprestimo() {
        StringBuilder emprestimos = new StringBuilder();
        for (Emprestavel emprestavel : this.getEmprestimos()) {
            emprestimos.append(emprestavel);
            emprestimos.append("\n");
        }
        return emprestimos.toString();
    }

    /**
     * Verifica quantidade de itens emprestados do usuario
     *
     * @return numero de itens emprestados
     */
    public boolean verificarEmprestimo() {
        return this.getEmprestimos().isEmpty();
    }


    /**
     * Verifica se o limite de itens emprestados foi atingido
     * 
     * @throws Exception
     */

    public void verificarLimiteParaEmprestimo() throws EmprestimoLimiteException {
        if(this.getQtdItensEmprestadosAtualmente() == this.getQTD_MAX_ITENS_EMPRESTADOS())
            throw new EmprestimoLimiteException("O limite de itens emprestados por vez é de 3!");
    }

    /**
     * Pesquisa um item emprestavel pelo id
     * 
     * @param id id do item emprestavel
     * @return item emprestavel
     * @throws Exception
     */
    public Emprestavel acharEmprestavelPorId(int id) throws ItemIndisponivelException {
        Emprestavel itemEmprestavel = null;
        for (Emprestavel emprestavel : this.getItensEmprestados()) {
            if (emprestavel.getId() == id) {
                itemEmprestavel = emprestavel;
                break;
            }
        }

        if(itemEmprestavel == null)
            throw new ItemIndisponivelException("Item emprestável na lista do usuário não encontrado!");


        return itemEmprestavel;
    }

    /**
     * Verifica o item que esta em atraso
     * 
     * @throws Exception
     */
    public void emprestavelEmAtrasoDoUsuario() throws DevolucaoDoEmprestimoException {
        Optional<Emprestavel> emprestavelEmAtraso = this.getItensEmprestados().stream()
                .filter(this::devolucaoEmAtraso)
                .findFirst();

        if (emprestavelEmAtraso.isPresent()) {
            Emprestavel emprestavel = emprestavelEmAtraso.get();
            throw new DevolucaoDoEmprestimoException("Há um item em atraso, devolva-o antes de realizar outro empréstimo. " +
                    "ID: " + emprestavel.getId() + " | Status do empréstimo: " + emprestavel.getStatusEmprestimo());
        }

    }

    /**
     * Verifica se a devolucao esta em atraso
     * 
     * @param itemEmprestavel item de referencia
     * @return verdadeiro se esta atrasado
     */
    public boolean devolucaoEmAtraso(Emprestavel itemEmprestavel) {
        
        if(itemEmprestavel.getDataEmprestimo() != null) {
            return LocalDate.now().isAfter(itemEmprestavel.getDataEmprestimo().plusDays(10));
        } else {
            return false;
        }
    }

    /**
     * Imprime informacos do usuario
     *
     * @return informacoes do usuario
     */
    @Override
    public String toString() {
        return "Dados do usuário: "
                + "\nId: "
                + this.getId()
                + "\nNome: "
                + this.getNome()
                + "\nData de nascimento: "
                + this.getDataNascimento().format(DataUtil.fmt)
                + "\nQuantidade de itens emprestados: "
                + this.getQtdItensEmprestadosAtualmente()
                + "\nQuantidade máxima de itens para empréstimo: "
                + this.getQTD_MAX_ITENS_EMPRESTADOS()
                + "\nhistórico de empréstimos: \n"
                + (verificarEmprestimo() ? "Nenhum empréstimo feito!\n" : listarEmprestimo());
    }

}
