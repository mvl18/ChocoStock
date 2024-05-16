package chocostock.interfaces;

import java.util.ArrayList;

public interface Iteravel {
    default <T> String listaObjetos(ArrayList<T> lista) {
        String texto = "";
        for (T objeto : lista) {
            texto += objeto + "\n";
        }
        return texto;
    }
}
