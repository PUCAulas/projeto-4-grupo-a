package main.java.models.itens;


import main.java.interfaces.AutorFiltro;
import main.java.utils.DataUtil;

import java.time.LocalDate;
import java.util.List;

public final class Tese extends Item implements AutorFiltro {

    private String autor;
    private String orientador;
    private LocalDate dataDefesa;
    private List<String> capitulos;

    /**
     * Construtor padrao da Tese
     */
    public Tese() {
        super();
    }

    /**
     * Lista artigos da teste
     * @return lista de artigos
     */
    public String listarCapitulos() {
        StringBuilder capitulos = new StringBuilder();
        for (String capitulo : this.getCapitulos()) {
            capitulos.append(capitulo);
            capitulos.append("\n");
        }
        return capitulos.toString();
    }

    /**
     * Imprime informacos da Tese
     *
     * @return informacoes da Tese
     */
    @Override
    public String toString() {
        return super.toString()
                + "\nAutor: "
                + this.getAutor()
                + "\nOrientador: "
                + this.getOrientador()
                + "\nData da defesa de tese: "
                + this.getDataDefesa().format(DataUtil.fmt)
                + "\nCapitulos: \n"
                + this.listarCapitulos();

    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getOrientador() {
        return orientador;
    }

    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }

    public LocalDate getDataDefesa() {
        return dataDefesa;
    }

    public void setDataDefesa(LocalDate dataDefesa) {
        this.dataDefesa = dataDefesa;
    }

    public List<String> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<String> capitulos) {
        this.capitulos = capitulos;
    }
}
