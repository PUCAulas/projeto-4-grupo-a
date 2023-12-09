package main.java.models;


import main.java.enums.AreaCursoSuperior;
import main.java.enums.CategoriaInteresse;
import main.java.enums.FiltroPesquisa;
import main.java.interfaces.UsuarioAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UsuarioAdaptarImpl implements UsuarioAdapter {

    private Usuario usuario;
    private Function<Usuario, AreaCursoSuperior> obterCurso;

    private Map<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> mapaDeInteresse;



    public UsuarioAdaptarImpl(Usuario usuario) {
        this.usuario = usuario;
        this.mapaDeInteresse = new HashMap<>();
        this.mapaDeInteresse.put(usuario, new HashMap<>());
    }

    @Override
    public AreaCursoSuperior getCurso() {
        return obterCurso.apply(usuario);
    }

    public void setCurso(AreaCursoSuperior novoCurso) {
        this.obterCurso = usuario -> novoCurso;
    }


    public Map<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> getMapaDeInteresse() {
        return mapaDeInteresse;
    }

    public void setMapaDeInteresse(Map<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> mapaDeInteresse) {
        this.mapaDeInteresse = mapaDeInteresse;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
