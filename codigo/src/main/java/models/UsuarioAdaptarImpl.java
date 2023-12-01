package main.java.models;

import main.java.enums.CategoriaInteresse;
import main.java.interfaces.UsuarioAdapter;
import main.java.interfaces.UsuarioSetterOperations;

import java.util.List;
import java.util.function.Function;

public class UsuarioAdaptarImpl implements UsuarioAdapter {

    private Usuario usuario;
    private Function<Usuario, String> obterCurso;
    private Function<Usuario, List<String>> obterCategoriasDeInteresse;

    private UsuarioSetterOperations<Usuario, Void> definirCurso; // para adicionar a lista
    private UsuarioSetterOperations<Usuario, Void> definirCategoriasDeInteresse;



    public UsuarioAdaptarImpl(
            Usuario usuario,
            Function<Usuario, String> obterCurso,
            Function<Usuario, List<String>> obterCategoriasDeInteresse,
            UsuarioSetterOperations<Usuario, Void> definirCurso,
            UsuarioSetterOperations<Usuario, Void> definirCategoriasDeInteresse
    ) {
        this.usuario = usuario;
        this.obterCurso = obterCurso;
        this.obterCategoriasDeInteresse = obterCategoriasDeInteresse;
        this.definirCurso = definirCurso;
        this.definirCategoriasDeInteresse = definirCategoriasDeInteresse;
    }

    @Override
    public String getCurso() {
        return obterCurso.apply(usuario);
    }

    @Override
    public List<String> getCategoriaInteresse() {
        return obterCategoriasDeInteresse.apply(usuario);
    }

    @Override
    public void setCurso(String curso) {

    }


    @Override
    public void setCategoriaInteresse(List<String> categoriaInteresse) {

    }


    @Override
    public void addCategoriaInteresse(CategoriaInteresse categoriaInteresse) {

    }

    @Override
    public void removeCategoriaInteresse(CategoriaInteresse categoriaInteresse) {

    }




}
