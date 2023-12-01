package main.java.interfaces;

import main.java.enums.CategoriaInteresse;

import java.util.List;

public interface UsuarioAdapter {

    String getCurso();

    void setCurso(String curso);

    List<String> getCategoriaInteresse();

    void setCategoriaInteresse(List<String> categoriaInteresse);

    void addCategoriaInteresse(CategoriaInteresse categoriaInteresse);

    void removeCategoriaInteresse(CategoriaInteresse categoriaInteresse);

}
