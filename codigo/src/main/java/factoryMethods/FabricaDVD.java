package main.java.factoryMethods;

import main.java.models.itens.DVD;
import main.java.models.itens.Emprestavel;

public class FabricaDVD extends FabricaEmprestavel {
    @Override
    public Emprestavel criarEmprestavel() {
        return new DVD();
    }
}
