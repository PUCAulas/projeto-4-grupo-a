package tests.java;

import main.java.models.Estoque;
import main.java.models.itens.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstoqueTest {

    Estoque estoque;
    Item item1;
    Item item2;

    @BeforeEach
    public void setUp() {
        estoque = new Estoque();
        item1 = new Item();
        item2 = new Item();
    }

    @Test
    public void addItemTest() {
        estoque.addItem(item1);
        assertEquals(1, estoque.qtdEmEstoque());
    }

    @Test
    public void removeItemTest() {
        estoque.addItem(item1);
        estoque.addItem(item2);
        estoque.removeItem(item1);
        assertEquals(1, estoque.qtdEmEstoque());
    }

    @Test
    public void qtdEmEstoqueTest() {
        assertEquals(0, estoque.qtdEmEstoque());
        estoque.addItem(item1);
        assertEquals(1, estoque.qtdEmEstoque());
        estoque.addItem(item2);
        assertEquals(2, estoque.qtdEmEstoque());
    }

    @Test
    public void getItensTest() {
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        estoque.setItens(items);
        assertEquals(items, estoque.getItens());
    }
}
