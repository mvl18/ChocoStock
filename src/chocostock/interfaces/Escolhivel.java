package chocostock.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// tentando fazer funcao generica para escolha de objeto em uma lista de objetos
public interface Escolhivel {
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, ArrayList<T> lista, int n_escolhas) {
        ArrayList<T> escolhidos = new ArrayList<T>();
        int i = 0;
        while (i < n_escolhas) {
            String escolha = scanner.nextLine();
            if (escolha.equals("sair")) {
                break;
            }
            int tamanho = lista.size();
            for (int j = 0; j < tamanho; j++) {
                T objeto = lista.get(j);
                if (escolha.replaceAll("\\d", "").isEmpty()) {  //verifica se a string escolha é um inteiro
                    if (objeto instanceof Identificavel) {
                        if (((Identificavel) objeto).getId() == Integer.parseInt(escolha)) {
                            escolhidos.add(objeto);
                            i++;
                        }
                    }
                } else {
                    if (objeto instanceof Nomeavel) {
                        if (((Nomeavel) objeto).getNome().equals(escolha)) {
                            escolhidos.add(objeto);
                            i++;
                        }
                    }
                    if (objeto instanceof Codificavel) {
                        if (((Codificavel) objeto).getCodigo().equals(escolha)) {
                            escolhidos.add(objeto);
                            i++;
                        }
                    }
                }
            }
        }

        return escolhidos;
    }

    default <T> T escolheObjeto(Scanner scanner, ArrayList<T> lista) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }

    default <T> ArrayList<T> escolheObjeto(Scanner scanner, T[] lista, int n_escolhas) {
        return escolheObjeto(scanner, new ArrayList<>(Arrays.asList(lista)), n_escolhas);
    }

    default <T> T escolheObjeto(Scanner scanner, T[] lista) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }
}
