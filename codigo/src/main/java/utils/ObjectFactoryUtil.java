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
                        "Livro sobre principes" },
                { "Aventuras em Marte", LocalDate.of(2010, 8, 15), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Maria", 150, "Editora Mundo", "n.05", "vol.01", "PT-BR",
                        "Ficção Científica", "Livro de aventuras em Marte" },
                { "O Último Romance", LocalDate.of(2015, 4, 10), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Antonio", 250, "Editora Romance", "n.03", "vol.01", "PT-BR",
                        "Romance", "Uma história emocionante de amor" },
                { "A Vida do Cientista", LocalDate.of(2018, 6, 20), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Lucia", 180, "Editora Ciência", "n.08", "vol.01", "PT-BR",
                        "Biografia de Cientista", "Biografia do cientista renomado" },
                { "O Universo em Nossas Mãos", LocalDate.of(2012, 3, 5), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Camila", 180, "Editora Astronomia", "n.07", "vol.01", "PT-BR",
                        "Divulgação Científica", "Exploração do universo e astronomia moderna" },

                { "A Revolução dos Sentidos", LocalDate.of(2019, 9, 10), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Pedro", 280, "Editora Sensações", "n.02", "vol.01", "PT-BR",
                        "Romance", "Uma jornada intensa pelos sentidos humanos" },

                { "Psicologia da Felicidade", LocalDate.of(2015, 6, 15), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Larissa", 200, "Editora Bem-Estar", "n.10", "vol.01", "PT-BR",
                        "Psicologia", "Descubra o caminho para uma vida mais feliz" },

                { "Matemática para Todos", LocalDate.of(2010, 12, 1), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Eduardo", 150, "Editora Números", "n.04", "vol.01", "PT-BR",
                        "Matemática e Lógica", "Desmistificando conceitos matemáticos" },

                { "A Arte da Política", LocalDate.of(2018, 5, 20), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Ana", 220, "Editora Política", "n.09", "vol.01", "PT-BR",
                        "Política", "Reflexões sobre a arte e ciência da política" },

                { "Saúde Integral", LocalDate.of(2016, 7, 8), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Rafael", 190, "Editora Saúde", "n.06", "vol.01", "PT-BR",
                        "Saúde", "Abordagem holística para o bem-estar" },
                { "Missão Perigosa", LocalDate.of(2014, 10, 5), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Mariana", 180, "Editora Ação", "n.12", "vol.01", "PT-BR",
                        "Ação", "Uma missão repleta de adrenalina e perigos" },

                { "O Tesouro do Explorador", LocalDate.of(2011, 6, 12), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Carlos", 210, "Editora Aventura", "n.14", "vol.01", "PT-BR",
                        "Aventura", "Uma jornada emocionante em busca de um tesouro misterioso" },

                { "Paixão Proibida", LocalDate.of(2017, 4, 8), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Isabela", 240, "Editora Romance Sensual", "n.04", "vol.02", "PT-BR",
                        "Erótico", "Uma história de paixão intensa e proibida" },

                { "A Mansão Assombrada", LocalDate.of(2013, 9, 15), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Gabriel", 190, "Editora Arrepio", "n.08", "vol.01", "PT-BR",
                        "Terror", "Um thriller arrepiante ambientado em uma mansão assombrada" },

                { "Segredos Obscuros", LocalDate.of(2016, 3, 20), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Larissa", 200, "Editora Mistério", "n.09", "vol.01", "PT-BR",
                        "Suspense", "Um mistério envolvente com segredos ocultos" },
                { "Cem Anos de Solidão", LocalDate.of(1967, 5, 30), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Gabriel García Márquez", 432, "Editora Sudamericana", "1ª edição", "Volume único", "PT-BR",
                        "Realismo Mágico", "Um épico que narra a história da família Buendía na fictícia Macondo." },

                { "O Código Da Vinci", LocalDate.of(2003, 3, 18), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Dan Brown", 489, "Editora Sextante", "1ª edição", "Volume único", "PT-BR",
                        "Mistério, Suspense", "Robert Langdon é chamado para desvendar um mistério envolvendo símbolos e segredos." },
                { "Orgulho e Preconceito", LocalDate.of(1813, 1, 28), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Jane Austen", 279, "Editora Thomas Egerton", "1ª edição", "Volume único", "EN",
                        "Romance Clássico", "A história do romance entre Elizabeth Bennet e o Sr. Darcy no século XIX na Inglaterra." },

                { "1984", LocalDate.of(1949, 6, 8), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "George Orwell", 336, "Editora Secker & Warburg", "1ª edição", "Volume único", "EN",
                        "Ficção Distópica", "Uma visão sombria de um futuro totalitário, onde o Estado controla tudo." },

                { "Dom Quixote", LocalDate.of(1605, 1, 16), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "Miguel de Cervantes", 863, "Editora Francisco de Robles", "1ª edição", "Volume único", "ES",
                        "Romance de Cavalaria", "As aventuras de um fidalgo enlouquecido que lê muitos romances de cavalaria." },
                { "O Iluminado", LocalDate.of(1977, 1, 28), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Stephen King", 447, "Editora Doubleday", "1ª edição", "Volume único", "EN",
                        "Horror, Suspense", "A história de uma família que se muda para um hotel isolado, onde o pai começa a enlouquecer." },

                { "Carrie, a Estranha", LocalDate.of(1974, 4, 5), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Stephen King", 199, "Editora Doubleday", "1ª edição", "Volume único", "EN",
                        "Horror, Sobrenatural", "A história de uma adolescente com poderes telecinéticos e sua vingança contra os colegas de escola." },

                { "It", LocalDate.of(1986, 9, 15), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Stephen King", 1104, "Editora Viking Penguin", "1ª edição", "Volume único", "EN",
                        "Horror, Fantasia", "A história de um grupo de crianças enfrentando uma entidade maligna que assume a forma de seus medos." },

                { "A Dança da Morte", LocalDate.of(1978, 10, 1), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Stephen King", 1424, "Editora Doubleday", "1ª edição", "Volume único", "EN",
                        "Horror, Pós-Apocalíptico", "Um épico pós-apocalíptico que segue os sobreviventes de uma praga mortal." },

                { "Cemitério Maldito", LocalDate.of(1983, 11, 14), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Stephen King", 424, "Editora Doubleday", "1ª edição", "Volume único", "EN",
                        "Horror Sobrenatural", "Um pai faz escolhas perturbadoras para lidar com a morte de sua filha de quatro anos." }
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
                        "Godjira", Duration.ofMinutes(45), new ArrayList<>(Arrays.asList("Faixa 01", "Faixa 02")) },
                { "Metallica", LocalDate.of(1981, 7, 25), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Metallica", Duration.ofMinutes(75),
                        new ArrayList<>(Arrays.asList("Enter Sandman", "Master of Puppets", "Nothing Else Matters")) },

                { "Black Sabbath", LocalDate.of(1968, 2, 13), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Black Sabbath", Duration.ofMinutes(60),
                        new ArrayList<>(Arrays.asList("Paranoid", "War Pigs", "Iron Man")) },

                { "Judas Priest", LocalDate.of(1969, 1, 1), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Judas Priest", Duration.ofMinutes(65),
                        new ArrayList<>(Arrays.asList("Breaking the Law", "Painkiller", "Electric Eye")) },

                { "Slayer", LocalDate.of(1981, 12, 28), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Slayer", Duration.ofMinutes(55),
                        new ArrayList<>(Arrays.asList("Angel of Death", "Raining Blood", "South of Heaven")) },

                { "Iron Maiden", LocalDate.of(1975, 12, 25), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Iron Maiden", Duration.ofMinutes(70),
                        new ArrayList<>(Arrays.asList("The Trooper", "Fear of the Dark", "Run to the Hills")) },
                // Álbuns adicionais de Heavy Metal
                { "Metallica - Master of Puppets", LocalDate.of(1986, 3, 3), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Metallica", Duration.ofMinutes(55),
                        new ArrayList<>(Arrays.asList("Battery", "Master of Puppets", "Welcome Home (Sanitarium)")) },

                { "Iron Maiden - The Number of the Beast", LocalDate.of(1982, 3, 22), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Iron Maiden", Duration.ofMinutes(44),
                        new ArrayList<>(Arrays.asList("Run to the Hills", "Hallowed Be Thy Name", "The Number of the Beast")) },

                { "Black Sabbath - Paranoid", LocalDate.of(1970, 9, 18), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Black Sabbath", Duration.ofMinutes(42),
                        new ArrayList<>(Arrays.asList("War Pigs", "Iron Man", "Paranoid")) },

                { "Ludwig van Beethoven - Sinfonia No. 5 em Dó Menor, Op. 67", LocalDate.of(1808, 12, 22),
                        StatusClassificacao.LIVRE, StatusEmprestimo.DISPONIVEL, "Ludwig van Beethoven", Duration.ofMinutes(32),
                        new ArrayList<>(Arrays.asList("Allegro con brio", "Andante con moto", "Scherzo", "Allegro")) },

                { "Wolfgang Amadeus Mozart - Eine kleine Nachtmusik, K. 525", LocalDate.of(1787, 8, 10),
                        StatusClassificacao.LIVRE, StatusEmprestimo.DISPONIVEL, "Wolfgang Amadeus Mozart", Duration.ofMinutes(22),
                        new ArrayList<>(Arrays.asList("Allegro", "Romanze", "Menuetto", "Rondo")) },

                { "Pyotr Ilyich Tchaikovsky - O Quebra-Nozes, Op. 71", LocalDate.of(1892, 12, 17),
                        StatusClassificacao.LIVRE, StatusEmprestimo.DISPONIVEL, "Pyotr Ilyich Tchaikovsky", Duration.ofMinutes(61),
                        new ArrayList<>(Arrays.asList("March", "Dance of the Sugar Plum Fairy", "Waltz of the Flowers")) }

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
                        "Baleia", Duration.ofMinutes(45), "PT-BR", "Filme de acao e aventura", "Acao e aventura" },
                { "Missão Impossível", LocalDate.of(2000, 8, 15), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Steven Spielberg", Duration.ofMinutes(120), "PT-BR",
                        "Uma missão impossível repleta de ação e suspense", "Ação e Suspense" },

                { "O Tesouro Perdido", LocalDate.of(2015, 5, 10), StatusClassificacao.LIVRE,
                        StatusEmprestimo.DISPONIVEL, "George Lucas", Duration.ofMinutes(90), "PT-BR",
                        "Uma aventura emocionante em busca de um tesouro perdido", "Aventura" },

                { "Romeu e Julieta", LocalDate.of(1996, 2, 14), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Baz Luhrmann", Duration.ofMinutes(120), "PT-BR",
                        "A clássica história de amor recriada de maneira moderna", "Romance" },

                { "50 Tons de Cinza", LocalDate.of(2014, 11, 20), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Sam Taylor-Johnson", Duration.ofMinutes(110), "PT-BR",
                        "Uma história erótica envolvente e provocante", "Erótico" },

                { "O Chamado", LocalDate.of(2002, 10, 18), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Gore Verbinski", Duration.ofMinutes(115), "PT-BR",
                        "Um filme de terror que te deixará arrepiado", "Terror" },

                { "Ilha do Medo", LocalDate.of(2010, 6, 24), StatusClassificacao.MAIOR_DE_16,
                        StatusEmprestimo.DISPONIVEL, "Martin Scorsese", Duration.ofMinutes(138), "PT-BR",
                        "Um thriller psicológico cheio de suspense", "Suspense" },

                { "Interestelar", LocalDate.of(2014, 11, 7), StatusClassificacao.MAIOR_DE_18,
                        StatusEmprestimo.DISPONIVEL, "Christopher Nolan", Duration.ofMinutes(169), "PT-BR",
                        "Uma emocionante jornada espacial e ficção científica", "Ficção Científica" },
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
