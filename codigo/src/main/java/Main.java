package main.java;

import java.util.NoSuchElementException;
import main.java.models.Biblioteca;
import main.java.models.Usuario;
import main.java.services.ItemEmprestavelService;
import main.java.services.ItemService;
import main.java.services.UsuarioService;
import main.java.utils.InputScannerUtil;
import main.java.utils.ObjectFactoryUtil;
import main.java.views.menus.AdmMenu;
import main.java.views.menus.UsuarioMenu;

public class Main {

    public static void main(String[] args) {

        Biblioteca biblioteca = ObjectFactoryUtil.construirBiblioteca();
        UsuarioService usuarioService = ObjectFactoryUtil.usuarioService(biblioteca);
        ItemService itemService = ObjectFactoryUtil.itemService(biblioteca);
        ItemEmprestavelService itemEmprestavelService = ObjectFactoryUtil.itemEmprestavelService(biblioteca);
        ObjectFactoryUtil.construirAdm(usuarioService);
        ObjectFactoryUtil.cadastrarUsuario(usuarioService);
        Usuario usuario = biblioteca.getUsuarios().get(0);
        ObjectFactoryUtil.generateDataBase(biblioteca);
        //simulando um emprestimo atrasado do usuario: email - admin / senha - 123
        ObjectFactoryUtil.emprestimoFake(itemEmprestavelService, usuario);

        while (true) {
            System.out.println("Você é usuário ou administrador? (escolha o número abaixo)");
            System.out.println("1 - Usuário");
            System.out.println("2 - Administrador");
            try {
                System.out.print("\nOpção: ");
                int escolha = InputScannerUtil.scanner.nextInt();
                switch (escolha) {
                    case 1:
                        UsuarioMenu.menuPrincipal(usuarioService);
                        break;
                    case 2:
                        AdmMenu.menuPrincipal(usuarioService, itemService, itemEmprestavelService, biblioteca);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        InputScannerUtil.close();
                }
            } catch (NoSuchElementException e) {
                System.out.println("Entrada inválida.");
                InputScannerUtil.scanner.nextLine();
            }
        }

    }

}
