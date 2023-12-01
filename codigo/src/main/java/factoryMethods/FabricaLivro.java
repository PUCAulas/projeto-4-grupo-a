package main.java.factoryMethods;

import main.java.models.itens.Emprestavel;
import main.java.models.itens.Livro;

public class FabricaLivro extends FabricaEmprestavel{
    @Override
    public Emprestavel criarEmprestavel() {
        return new Livro();
    }
}
