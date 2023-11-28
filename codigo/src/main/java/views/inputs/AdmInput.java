package main.java.views.inputs;

import main.java.models.Usuario;
import main.java.services.UsuarioService;
import main.java.utils.InputScannerUtil;

import java.util.Optional;

public class AdmInput {

    /**
     * Menu de pesquisa de usuario
     *
     * @param usuarioService servico de usuario
     * @return usuario encontrado
     */
    public static Optional<Usuario> dadosDePesquisaDoUsuario(UsuarioService usuarioService) {
        try {
            checkAdm(usuarioService);
            System.out.print("Digite o id do usuário desejado: ");
            int id = InputScannerUtil.scanner.nextInt();
            InputScannerUtil.scanner.nextLine();

            try {
                Usuario usuario = usuarioService.pesquisarUsuarioPorId(id);
                return Optional.ofNullable(usuario);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Deleta um usuario
     *
     * @param usuarioService servico de usuario
     */
    public static void deletarUsuarioPorId(UsuarioService usuarioService) {
        try {
            checkAdm(usuarioService);
            Optional<Usuario> usuarioOptional = dadosDePesquisaDoUsuario(usuarioService);
            usuarioOptional.ifPresent(usuarioService::deletar);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista os dados dos usuarios cadastrados
     *
     * @param usuarioService servico de usuario
     */
    public static void dadosDeTodosOsUsuarios(UsuarioService usuarioService) {
        try {
            checkAdm(usuarioService);
            usuarioService.listar();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Verifica se usuario e admin
     *
     * @param usuarioService servico de usuario
     * @throws Exception lanca excecao caso o usuario nao esteja cadastrado
     */
    public static void checkAdm(UsuarioService usuarioService) throws Exception {
        System.out.print("Informe sua senha de administrador: ");
        String senha = InputScannerUtil.scanner.nextLine();
        System.out.print("Informe seu email de administrador: ");
        String email = InputScannerUtil.scanner.nextLine();

        usuarioService.verificarAdm(senha, email);
    }

}
