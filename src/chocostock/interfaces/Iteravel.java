package chocostock.interfaces;

import java.util.ArrayList;

public interface Iteravel {
    default <T> String listaVertical(ArrayList<T> lista) {
        String texto = "";
        for (T objeto : lista) {
            texto += objeto + "\n";
        }
        return texto;
    }

    default <T> String listaHorizontal(ArrayList<T> lista) {
        String out = "";
        for (int i = 0; i < lista.size(); i++)
            out += (lista.get(i) instanceof Nomeavel ? ((Nomeavel) lista.get(i)).getNome() : lista.get(i)) + (i == lista.size()-1 ? "" : ", ");
        return out;
    }

    default <T> String listaHorizontalQuebraLinha(ArrayList<T> lista) {
        return listaHorizontal(lista) + "\n";
    }
}
