package chocostock.interfaces;

import java.util.ArrayList;

public interface Iteravel {
    /**
     * Imprime uma lista com um item em cada linha.
     * @param lista A lista de objetos que serão impressos.*/
    default <T> String listaVertical(ArrayList<T> lista) {
        String texto = "";
        for (T objeto : lista) {
            texto += objeto + "\n";
        }
        return texto;
    }

    /**
     * Imprime uma lista no formato: item1, item2, item3
     * @param lista A lista de objetos que serão impressos.*/
    default <T> String listaHorizontal(ArrayList<T> lista) {
        String out = "";
        for (int i = 0; i < lista.size(); i++)
            out += (lista.get(i) instanceof Nomeavel ? ((Nomeavel) lista.get(i)).getNome() : lista.get(i)) + (i == lista.size()-1 ? "" : ", ");
        return out;
    }

    /**
     *Sobrecarga do método listHorizontal com quebra de linha ao final.*/
    default <T> String listaHorizontalQuebraLinha(ArrayList<T> lista) {
        return listaHorizontal(lista) + "\n";
    }
}
