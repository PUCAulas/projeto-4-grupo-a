package main.java.interfaces;

import main.java.models.Usuario;

public interface GerenciarEmprestimo {

    /**
     * Empresta um item emprestavel a um usuario
     *
     * @param idItem id do item emprestavel
     * @param usuario usuario de referencia
     */
    void emprestar(int idItem, Usuario usuario) throws Exception;

    /**
     * Devolve um item previamente emprestado a um usuario
     *
     * @param id id do item emprestavel
     * @param usuario usuario de referencia
     */
    void devolver(int id, Usuario usuario) throws Exception;


}
