package tests.java;

import main.java.enums.StatusClassificacao;
import main.java.enums.StatusEmprestimo;
import main.java.models.Usuario;
import main.java.models.itens.Emprestavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioTest {

    Usuario usuario = new Usuario();
    Emprestavel emprestavel = new Emprestavel();

    @BeforeEach
    public void criarTest() {
        usuario.setNome("Guilherme");
        usuario.setEmail("guilherme@email.com");
        usuario.setDataNascimento(LocalDate.of(1997, 4, 20));
        emprestavel.setTitulo("Don quixote");
        emprestavel.setDataPublicacao(LocalDate.of(1605, 1, 1));
        emprestavel.setStatusClassificacao(StatusClassificacao.MAIOR_DE_16);
        emprestavel.setStatusEmprestimo(StatusEmprestimo.EMPRESTADO);
    }

    @Test
    public void getNomeTest() {
        assertEquals("Guilherme", usuario.getNome());
    }

    @Test
    public void getEmailTest() {
        assertEquals("guilherme@email.com", usuario.getEmail());
    }

    @Test
    public void getDataNascimentoTest() {
        assertEquals(LocalDate.of(1997, 4, 20), usuario.getDataNascimento());
    }

    @Test
    public void setNomeTest() {
        usuario.setNome("Paulo");
        assertEquals("Paulo", usuario.getNome());
    }

    @Test
    public void setEmailTest() {
        usuario.setEmail("paulo@email.com");
        assertEquals("paulo@email.com", usuario.getEmail());
    }

    @Test
    public void setDataNascimentoTest() {
        usuario.setDataNascimento(LocalDate.of(1999, 5, 10));
        assertEquals(LocalDate.of(1999, 5, 10), usuario.getDataNascimento());
    }

    @Test
    public void addEmprestimoTest() {
        usuario.addEmprestimo(emprestavel);
        assertEquals(emprestavel, usuario.getEmprestimos().get(0));
    }

    @Test
    public void removerEmprestimoTest() {
        usuario.removerEmprestimo(emprestavel);
        assertEquals(0, usuario.getEmprestimos().size());
    }

    @Test
    public void verificarEmprestimosTest() {
        assertTrue(usuario.verificarEmprestimo());
    }
}
