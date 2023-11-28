package main.java.views.inputs;

import main.java.enums.StatusClassificacao;
import main.java.enums.StatusEmprestimo;
import main.java.utils.InputScannerUtil;

import java.util.List;

public class ItemInput {


    /*
     * escolha da classificacao etaria
     */
    protected static StatusClassificacao escolherStatusClassificacao() {

        for (StatusClassificacao status : StatusClassificacao.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status.name());
        }

        int escolha = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();

        if (escolha >= 1 && escolha <= StatusClassificacao.values().length) {
            return StatusClassificacao.values()[escolha - 1];
        } else {
            System.out.println("Opção inválida.");
            return null;
        }

    }

    /*
     * escolha do status do emprestimo
     */
    protected static StatusEmprestimo escolherStatusEmprestimo() {

        for (StatusEmprestimo status : StatusEmprestimo.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status.name());
        }

        int escolha = InputScannerUtil.scanner.nextInt();
        InputScannerUtil.scanner.nextLine();

        if (escolha >= 1 && escolha <= StatusEmprestimo.values().length) {
            return StatusEmprestimo.values()[escolha - 1];
        } else {
            System.out.println("Opção inválida.");
            return null;
        }
    }

    /*
     * insere um conteudo em uma lista
     */
    protected static void inserirConteudo(List<String> conteudo) {
        while (true) {
            String linha = InputScannerUtil.scanner.nextLine();
            if ("FIM".equalsIgnoreCase(linha)) {
                break;
            }
            conteudo.add(linha);
        }
    }

}
