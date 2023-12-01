package main.java.userAdapter;

import main.java.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdaptor {

    private String curso;
    private List<String> categoriasInteresse;
    private Usuario usuario;

    public UsuarioAdaptor(Usuario usuario) {
        this.curso = "";
        this.categoriasInteresse = new ArrayList<>();
        this.usuario = usuario;
    }
}
