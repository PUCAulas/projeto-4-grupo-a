package main.java.interfaces;

import main.java.enums.AreaCursoSuperior;
import main.java.enums.FiltroPesquisa;

import java.util.List;

public interface UsuarioAdapter {

    AreaCursoSuperior getCurso();

    void setCurso(String curso);

    List<String> getCategoriaInteresse();

    void setCategoriaInteresse(List<String> categoriaInteresse);

    void addCategoriaInteresse(FiltroPesquisa categoriaInteresse);

    void removeCategoriaInteresse(FiltroPesquisa categoriaInteresse);

}
