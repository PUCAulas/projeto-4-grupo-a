package main.java.models;


import main.java.enums.AreaCursoSuperior;
import main.java.enums.CategoriaInteresse;
import main.java.enums.FiltroPesquisa;
import main.java.interfaces.UsuarioAdapter;
import main.java.interfaces.UsuarioSetterOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UsuarioAdaptarImpl implements UsuarioAdapter {

    private Usuario usuario;
    private Function<Usuario, AreaCursoSuperior> obterCurso;

    private Map<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> mapaDeInteresse;


    private UsuarioSetterOperations<Usuario, Void> definirCurso; // para adicionar a lista
    private UsuarioSetterOperations<Usuario, Void> definirCategoriasDeInteresse;



    public UsuarioAdaptarImpl(Usuario usuario) {
        this.usuario = usuario;
        this.mapaDeInteresse = new HashMap<>();
        this.mapaDeInteresse.put(usuario, new HashMap<>());
    }

    @Override
    public AreaCursoSuperior getCurso() {
        return obterCurso.apply(usuario);
    }

    @Override
    public List<String> getCategoriaInteresse() {
        return null;
    }

    @Override
    public void setCurso(String curso) {

    }


    @Override
    public void setCategoriaInteresse(List<String> categoriaInteresse) {

    }


    @Override
    public void addCategoriaInteresse(FiltroPesquisa categoriaInteresse) {

    }

    @Override
    public void removeCategoriaInteresse(FiltroPesquisa categoriaInteresse) {

    }




}
