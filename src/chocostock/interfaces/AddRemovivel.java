package chocostock.interfaces;

import java.util.ArrayList;

public interface AddRemovivel {
    default <T> boolean addObjeto(int posicao, ArrayList<T> lista, T objeto) {
        if(!lista.contains(objeto)) {
            lista.add(posicao, objeto);
            return true;
        }
        return false;
    }

    default <T> boolean addObjeto(ArrayList<T> lista, T objeto) {
        return addObjeto(lista.size(), lista, objeto);
    }

    default <T> boolean removeObjeto(ArrayList<T> lista, T objeto) {
        if(lista.contains(objeto)) {
            lista.remove(objeto);
            return true;
        }
        return false;
    }
}
