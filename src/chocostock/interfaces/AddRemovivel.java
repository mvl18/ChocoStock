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
     * @param lista A lista na qual o objeto será adicionado.
     * @param objeto O objeto a ser adicionado.
     * @param <T> O tipo de objeto na lista.
     * @return true se o objeto foi adicionado com sucesso, false caso contrário.
     */
    default <T> boolean addObjeto(int posicao, ArrayList<T> lista, T objeto) {
        // Verifica se o objeto já está presente na lista
        if(!lista.contains(objeto)) {
            lista.add(posicao, objeto);
            return true;
        }
        return false;
    }

    /**
     * Adiciona um objeto ao final de uma lista.
     *
     * @param lista A lista na qual o objeto será adicionado.
     * @param objeto O objeto a ser adicionado.
     * @param <T> O tipo de objeto na lista.
     * @return true se o objeto foi adicionado com sucesso, false caso contrário.
     */
    default <T> boolean addObjeto(ArrayList<T> lista, T objeto) {
        return addObjeto(lista.size(), lista, objeto);
    }

    /**
     * Remove um objeto de uma lista.
     *
     * @param lista A lista da qual o objeto será removido.
     * @param objeto O objeto a ser removido.
     * @param <T> O tipo de objeto na lista.
     * @return true se o objeto foi removido com sucesso, false caso contrário.
     */
    default <T> boolean removeObjeto(ArrayList<T> lista, T objeto) {
        if(lista.contains(objeto)) {
            lista.remove(objeto);
            return true;
        }
        return false;
    }
}
