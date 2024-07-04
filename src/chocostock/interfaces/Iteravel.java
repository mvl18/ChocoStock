package chocostock.interfaces;

import java.util.ArrayList;

/**
 * A interface Iteravel define métodos para criar representações de listas
 * em formato de texto. (listaVertical, listaHorizontal e listaHorizontalQuebraLinha)
 */
public interface Iteravel {
    /**
     * Cria uma representação vertical de uma lista, cada item em uma nova linha.
     *
     * @param lista A lista de objetos a ser iterada.
     * @param <T>   O tipo de objeto na lista.
     * @return Uma string contendo a representação vertical da lista.
     */
    static <T> String listaVertical(ArrayList<T> lista) {
        StringBuilder texto = new StringBuilder();
        for (T objeto : lista) {
            texto.append(objeto).append("\n");
        }
        return texto.toString();
    }

    /**
     * Cria uma representação horizontal de uma lista, cada item separado por vírgula.
     *
     * @param lista A lista de objetos a ser iterada.
     * @param <T>   O tipo de objeto na lista.
     * @return Uma string contendo a representação horizontal da lista.
     */
    static <T> String listaHorizontal(ArrayList<T> lista) {
        if (lista == null) {
            return "Lista vazia";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < lista.size(); i++)
            out.append(lista.get(i) instanceof Nomeavel ? ((Nomeavel) lista.get(i)).getNome() : lista.get(i)).append(i == lista.size() - 1 ? "" : ", ");
        return out.toString();
    }

    /**
     * Cria uma representação horizontal de uma lista, cada item separado por vírgula e finalizado com uma quebra de linha.
     *
     * @param lista A lista de objetos a ser iterada.
     * @param <T>   O tipo de objeto na lista.
     * @return Uma string contendo a representação horizontal da lista seguida por uma quebra de linha.
     */
    default <T> String listaHorizontalQuebraLinha(ArrayList<T> lista) {
        return listaHorizontal(lista) + "\n";
    }
}
