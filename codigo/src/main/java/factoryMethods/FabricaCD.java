package main.java.factoryMethods;

import main.java.models.itens.CD;
import main.java.models.itens.Emprestavel;

public class FabricaCD extends FabricaEmprestavel{
    @Override
    public Emprestavel criarEmprestavel() {
        return new CD();
    }
}
