package main.java.enums;

public enum StatusClassificacao {
    MAIOR_DE_18("Maior de 18 anos"),
    MAIOR_DE_16("Maior de 16 anos"),
    LIVRE("Livre");

    private String classificacao;

    StatusClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getClassificacao() {
        return classificacao;
    }
}
