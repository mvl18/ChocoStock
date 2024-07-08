package chocostock.interfaces;

import java.util.ArrayList;

/**
 * A interface AddRemovivel define métodos padrão para adicionar
 * e remover objetos de uma lista.
 */
public interface AddRemovivel {
    /**
     * Adiciona um objeto em uma lista na posição especificada.
     *
     * @param posicao A posição onde o objeto será adicionado.
     * @param lista   A lista na qual o objeto será adicionado.
     * @param objeto  O objeto a ser adicionado.
     * @param <T>     O tipo de objeto na lista.
     */
    default <T> void addObjeto(int posicao, ArrayList<T> lista, T objeto) {
        // Verifica se o objeto já está presente na lista
        if(!lista.contains(objeto)) {
            lista.add(posicao, objeto);
        }
    }


    default <T extends Identificavel> void removeObjetoPorId(int id, ArrayList<T> lista) {
        lista.removeIf(objeto -> objeto.getId() == id);
    }


    default <T extends Identificavel> T getObjetoPorId(int id, ArrayList<T> lista) {
        for (T objeto : lista) {
            if (objeto.getId() == id) {
                return objeto;
            }
        }
        return null;
    }

    /**
     * Adiciona um objeto ao final de uma lista.
     *
     * @param lista  A lista na qual o objeto será adicionado.
     * @param objeto O objeto a ser adicionado.
     * @param <T>    O tipo de objeto na lista.
     */
    default <T> void addObjeto(ArrayList<T> lista, T objeto) {
        addObjeto(lista.size(), lista, objeto);
    }

    /**
     * Remove um objeto de uma lista.
     *
     * @param lista  A lista da qual o objeto será removido.
     * @param objeto O objeto a ser removido.
     * @param <T>    O tipo de objeto na lista.
     */
    static <T> void removeObjeto(ArrayList<T> lista, T objeto) {
        lista.remove(objeto);
    }
}