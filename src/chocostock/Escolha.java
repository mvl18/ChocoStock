package chocostock;

import java.util.ArrayList;
import java.util.Scanner;

// tentando fazer funcao generica para escolha de objeto em uma lista de objetos
public interface Escolha {
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, ArrayList<T> lista, int n_escolhas) {
        ArrayList<T> escolhidos = new ArrayList<T>();
        int i = 0;
        while (i < n_escolhas) {
            String escolha = scanner.nextLine();
            if (escolha.equals(lista.get(i))); // problema aqui, pois nao posso dar .getId() nem .getNome pq nem toda classe tem nome
        }


        return escolhidos;
    }

    default <T> T escolheObjeto(Scanner scanner, ArrayList<T> lista) {
        return escolheObjeto(scanner, lista, 1).get(0);
    }
}
