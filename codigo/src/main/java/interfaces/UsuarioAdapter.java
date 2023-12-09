package main.java.interfaces;

import main.java.enums.AreaCursoSuperior;
import main.java.enums.CategoriaInteresse;
import main.java.enums.FiltroPesquisa;
import main.java.models.Usuario;

import java.util.List;
import java.util.Map;

public interface UsuarioAdapter {

    AreaCursoSuperior getCurso();

    void setCurso(AreaCursoSuperior areaCursoSuperior);

    Map<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> getMapaDeInteresse();

    void setMapaDeInteresse(Map<Usuario, Map<FiltroPesquisa, Map<CategoriaInteresse, List<String>>>> mapaDeInteresse);

}
