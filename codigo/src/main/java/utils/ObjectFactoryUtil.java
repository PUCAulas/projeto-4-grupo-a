package main.java.utils;

import main.java.enums.Perfil;
import main.java.enums.StatusClassificacao;
import main.java.enums.StatusEmprestimo;
import main.java.factoryMethods.FabricaCD;
import main.java.factoryMethods.FabricaDVD;
import main.java.factoryMethods.FabricaLivro;
import main.java.models.Biblioteca;
import main.java.models.Estoque;
import main.java.models.Usuario;
import main.java.models.itens.*;
import main.java.services.ItemEmprestavelService;
import main.java.services.ItemService;
import main.java.services.UsuarioService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static main.java.utils.DataUtil.fmt;

/**
 * todo: Coloque aqui, toda a instância para o start do programa
 * Essa classe também tem objetivo de evitar que qualquer mudança de atributo
 * que afete os construtores seja mudado
 * na classe principal (Main)
 */
public class ObjectFactoryUtil {

    /**
     * Inicia nova instancia de biblioteca
     *
     * @return nova biblioteca
     */
    public static Biblioteca construirBiblioteca() {
        Estoque estoque = new Estoque();
        Biblioteca biblioteca = Biblioteca.getINSTANCE();
        Biblioteca.getINSTANCE().setEstoque(estoque);
        return biblioteca;
    }

    /**
     * Inicia nova instancia de usuarioService
     *
     * @param biblioteca biblioteca de referencia
     * @return novo usuarioService
     */
    public static UsuarioService usuarioService(Biblioteca biblioteca) {
        UsuarioService usuarioService = new UsuarioService(biblioteca);
        return usuarioService;
    }

    /**
     * Inicia nova instancia de itemService
     *
     * @param biblioteca biblioteca de referencia
     * @return novo itemService
     */
    public static ItemService itemService(Biblioteca biblioteca) {
        ItemService itemService = new ItemService(biblioteca);
        return itemService;
    }

    /**
     * Inicia nova instancia de itemEmprestavelService
     *
     * @param biblioteca biblioteca de referencia
     * @return novo itemEmprestavelService
     */
    public static ItemEmprestavelService itemEmprestavelService(Biblioteca biblioteca) {
        ItemEmprestavelService itemEmprestavelService = new ItemEmprestavelService(biblioteca);
        return itemEmprestavelService;
    }

    /**
     * Inicia nova instancia de usuario
     *
     * @return novo usuario
     */
    public static Usuario newUsuario() {
        return new Usuario();
    }

    /**
     * Inicia nova instancia de revista
     *
     * @return nova revista
     */
    public static Revista newRevista() {
        return new Revista();
    }

    /**
     * Inicia nova instancia de tese
     *
     * @return nova tese
     */
    public static Tese newTese() {
        return new Tese();
    }

    /**
     * Inicia nova instancia de DVD
     *
     * @return novo DVD
     */
    public static DVD newDVD() {
        return new DVD();
    }

    /**
     * Inicia nova instancia de CD
     *
     * @return novo CD
     */
    public static CD newCD() {
        return new CD();
    }

    /**
     * Inicia nova instancia de livro
     *
     * @return novo livro
     */
    public static Livro newLivro() {
        return new Livro();
    }

    /**
     * Inicia nova instancia de usuario
     *
     * @param usuarioService usuarioService de referencia
     */
    public static void cadastrarUsuario(UsuarioService usuarioService) {
        try {
            Usuario usuario = new Usuario();
            usuarioService.setUsuario(usuario);
            usuarioService.criar("Usuario", "usuario", "123", LocalDate.now(), Perfil.USUARIO);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Simula um empréstimo a um usuário cadastrado
     *
     * @param itemEmprestavelService
     * @param usuario
     */
    public static void emprestimoFake(ItemEmprestavelService itemEmprestavelService, Usuario usuario) {
        try {
           Optional<Item> emprestavel = itemEmprestavelService.getBiblioteca().getEstoque().getItens()
           .stream()
           .filter(x -> x instanceof Emprestavel)
           .findFirst();

           emprestavel.ifPresent(item -> {

                if(item instanceof Emprestavel) {
                    Emprestavel emprestavelConvert = (Emprestavel) item;
                
                    itemEmprestavelService.setEmprestimo(emprestavelConvert);
                    itemEmprestavelService.getEmprestimo().setStatusEmprestimo(StatusEmprestimo.EMPRESTADO);
                    itemEmprestavelService.getEmprestimo().setDataEmprestimo(LocalDate.of(2023, 10, 30));
                    itemEmprestavelService.getEmprestimo().setNumEmprestimos(emprestavelConvert.getNumEmprestimos() + 1);

                    usuario.setQtdItensEmprestadosAtualmente(usuario.getQtdItensEmprestadosAtualmente() + 1);
                    usuario.addEmprestimo(itemEmprestavelService.getEmprestimo());
                    itemEmprestavelService.setEmprestimo(null);
                }
            });
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Inicia nova instancia de administrador
     *
     * @param usuarioService usuarioService de referencia
     */
    public static void construirAdm(UsuarioService usuarioService) {
        Usuario usuario = new Usuario();
        usuarioService.setUsuario(usuario);
        try {
            usuarioService.criar("Administrador", "admin", "123", LocalDate.parse("21/10/1988", fmt), Perfil.ADM);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Cria base de dados mock para testes
     *
     * @param biblioteca biblioteca de referencia
     */
    public static void generateDataBase(Biblioteca biblioteca) {

        Estoque estoque = Biblioteca.getINSTANCE().getEstoque();
        List<Revista> revistas = generateRevistas();
        List<Tese> teses = generateTeses();
        List<CD> cds = generateCDs();
        List<DVD> dvds = generateDVDs();
        List<Livro> livros = generateLivros();

        revistas.forEach(estoque::addItem);
        teses.forEach(estoque::addItem);
        cds.forEach(estoque::addItem);
        dvds.forEach(estoque::addItem);
        livros.forEach(estoque::addItem);

        System.out.println();
    }

    /**
     * Gera base de dados de livros
     *
     * @return lista de livros
     */
    private static List<Livro> generateLivros() {

        List<Livro> livros = new ArrayList<>();
        Object[][] livrosInfo = {
                { "Nossas incriveis serpentes", LocalDate.of(2002, 5, 1), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Joaozinho", 100, "Editora Brasil", "n.01", "vol.01", "PT-BR",
                        "Ciencia", "Livro sobre serpentes" },
                { "Aracnideos do cerrado", LocalDate.of(2004, 2, 2), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Paulito", 200, "Editora Colombia", "n.11", "vol.01", "PT-BR",
                        "Ciencia", "Livro sobre aracnideos" },
                { "Little principe", LocalDate.of(2002, 2, 2), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Jorge", 300, "Editora EUA", "n.15", "vol.01", "PT-BR", "Ciencia",
                        "Livro sobre principes" }
        };

        FabricaLivro fabricaLivro = new FabricaLivro();

        Arrays.stream(livrosInfo).toList().forEach(livro -> {
            Livro l = (Livro) fabricaLivro.criarEmprestavel();
            l.setTitulo((String) livro[0]);
            l.setDataPublicacao((LocalDate) livro[1]);
            l.setStatusClassificacao((StatusClassificacao) livro[2]);
            l.setStatusEmprestimo((StatusEmprestimo) livro[3]);
            l.setAutor((String) livro[4]);
            l.setNumeroPaginas((Integer) livro[5]);
            l.setEditora((String) livro[6]);
            l.setEdicao((String) livro[7]);
            l.setVolume((String) livro[8]);
            l.setIdioma((String) livro[9]);
            l.setGenero((String) livro[10]);
            l.setSinopse((String) livro[11]);
            livros.add(l);
        });

        return livros;
    }

    /**
     * Gera base de dados de CDs
     *
     * @return lista de CDs
     */
    private static List<CD> generateCDs() {

        List<CD> cds = new ArrayList<>();
        Object[][] dvdInfo = {
                { "Barons of the little step", LocalDate.of(2002, 5, 1), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Barons of the little step", Duration.ofMinutes(60),
                        new ArrayList<>(Arrays.asList("Faixa 01", "Faixa 02")) },
                { "Lincoln parque", LocalDate.of(1983, 2, 2), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Lincoln parque", Duration.ofMinutes(30),
                        new ArrayList<>(Arrays.asList("Faixa 01", "Faixa 02")) },
                { "Godjira", LocalDate.of(1997, 2, 2), StatusClassificacao.MAIOR_DE_18, StatusEmprestimo.DISPONIVEL,
                        "Godjira", Duration.ofMinutes(45), new ArrayList<>(Arrays.asList("Faixa 01", "Faixa 02")) }
        };

        FabricaCD fabricaCD = new FabricaCD();

        Arrays.stream(dvdInfo).toList().forEach(cd -> {
            CD c = (CD) fabricaCD.criarEmprestavel();
            c.setTitulo((String) cd[0]);
            c.setDataPublicacao((LocalDate) cd[1]);
            c.setStatusClassificacao((StatusClassificacao) cd[2]);
            c.setStatusEmprestimo((StatusEmprestimo) cd[3]);
            c.setArtista((String) cd[4]);
            c.setDuracao((Duration) cd[5]);
            c.setFaixas((List<String>) cd[6]);
            cds.add(c);
        });

        return cds;
    }

    /**
     * Gera base de dados de DVDs
     *
     * @return lista de DVDs
     */
    private static List<DVD> generateDVDs() {

        List<DVD> dvds = new ArrayList<>();
        Object[][] dvdInfo = {
                { "A freira", LocalDate.of(1998, 5, 1), StatusClassificacao.LIVRE, StatusEmprestimo.DISPONIVEL,
                        "Pinoquio", Duration.ofMinutes(60), "PT-BR", "Filme de terror", "Terror" },
                { "Ace ventura", LocalDate.of(1997, 2, 2), StatusClassificacao.MAIOR_DE_16, StatusEmprestimo.DISPONIVEL,
                        "Gepeto", Duration.ofMinutes(30), "PT-BR", "Filme de comedia", "Comedia" },
                { "007", LocalDate.of(2008, 2, 2), StatusClassificacao.MAIOR_DE_18, StatusEmprestimo.DISPONIVEL,
                        "Baleia", Duration.ofMinutes(45), "PT-BR", "Filme de acao e aventura", "Acao e aventura" }
        };

        FabricaDVD fabricaDVD = new FabricaDVD();

        Arrays.stream(dvdInfo).toList().forEach(dvd -> {
            DVD d = (DVD) fabricaDVD.criarEmprestavel();
            d.setTitulo((String) dvd[0]);
            d.setDataPublicacao((LocalDate) dvd[1]);
            d.setStatusClassificacao((StatusClassificacao) dvd[2]);
            d.setStatusEmprestimo((StatusEmprestimo) dvd[3]);
            d.setDiretor((String) dvd[4]);
            d.setDuracao((Duration) dvd[5]);
            d.setIdioma((String) dvd[6]);
            d.setSinopse((String) dvd[7]);
            d.setGenero((String) dvd[8]);
            dvds.add(d);
        });

        return dvds;
    }

    /**
     * Gera base de dados de Teses
     *
     * @return lista de Teses
     */
    private static List<Tese> generateTeses() {

        List<Tese> teses = new ArrayList<>();
        Object[][] teseInfo = {
                { "Tese de mestrado", LocalDate.of(2022, 5, 1), StatusClassificacao.LIVRE, "Juanito Jones", "Pantoro",
                        LocalDate.of(2002, 10, 1),
                        new ArrayList<>(Arrays.asList("Introducao", "Metodologia", "Conclusao")) },
                { "Tese de doutorado", LocalDate.of(2010, 2, 2), StatusClassificacao.MAIOR_DE_16, "Franco columbu",
                        "Treinador misterioso", LocalDate.of(2000, 7, 2),
                        new ArrayList<>(Arrays.asList("Introducao", "Resultados", "Constatacoes")) },
                { "Tese de pos-doutorado", LocalDate.of(2013, 2, 2), StatusClassificacao.MAIOR_DE_18, "Mickey mouse",
                        "Walter", LocalDate.of(2000, 11, 3),
                        new ArrayList<>(Arrays.asList("Introducao", "Resultados", "Constatacoes")) }
        };

        Arrays.stream(teseInfo).toList().forEach(tese -> {
            Tese t = new Tese();
            t.setTitulo((String) tese[0]);
            t.setDataPublicacao((LocalDate) tese[1]);
            t.setStatusClassificacao((StatusClassificacao) tese[2]);
            t.setAutor((String) tese[3]);
            t.setOrientador((String) tese[4]);
            t.setDataDefesa((LocalDate) tese[5]);
            t.setCapitulos((List<String>) tese[6]);
            teses.add(t);
        });

        return teses;
    }

    /**
     * Gera base de dados de Revistas
     *
     * @return lista de Revistas
     */
    private static List<Revista> generateRevistas() {

        List<Revista> revistas = new ArrayList<>();
        Object[][] revistaInfo = {
                { "Turma da monica", LocalDate.of(1988, 5, 1), StatusClassificacao.LIVRE, "Editora Abril", "n.01",
                        new ArrayList<>(Arrays.asList("Monica vs Cebolinha", "Cascao contra ataca!", "Chico bento")) },
                { "Jornal", LocalDate.of(2023, 2, 2), StatusClassificacao.MAIOR_DE_16, "Editora Brasil", "n.99",
                        new ArrayList<>(Arrays.asList("Tiroteio no rio", "Transito em sao paulo", "Queijo em minas")) },
                { "Revista 18+", LocalDate.of(2007, 3, 3), StatusClassificacao.MAIOR_DE_18, "Editora Colombia", "n.666",
                        new ArrayList<>(Arrays.asList("Gays", "Mulheres", "Homens", "Outros")) }
        };

        Arrays.stream(revistaInfo).toList().forEach(revista -> {
            Revista r = new Revista();
            r.setTitulo((String) revista[0]);
            r.setDataPublicacao((LocalDate) revista[1]);
            r.setStatusClassificacao((StatusClassificacao) revista[2]);
            r.setEditora((String) revista[3]);
            r.setEdicao((String) revista[4]);
            r.setArtigos((List<String>) revista[5]);
            revistas.add(r);
        });

        return revistas;
    }

}
