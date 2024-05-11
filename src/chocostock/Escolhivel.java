package chocostock;

import java.util.ArrayList;
import java.util.Scanner;

// tentando fazer funcao generica para escolha de objeto em uma lista de objetos
public interface Escolhivel {
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, ArrayList<T> lista, int n_escolhas) {
        ArrayList<T> escolhidos = new ArrayList<T>();
        int i = 0;
        while (i < n_escolhas) {
            String escolha = scanner.nextLine();
            int tamanho = lista.size();
//            if (tamanho == 0) {
//                break;
//            }
            if (escolha.equals("sair")) {
                break;
            }
            for (int j = 0; j < lista.size(); j++) {
                T objeto = lista.get(j);
                if (escolha.replaceAll("\\d", "").equals("")) {  //verifica se a string escolha é um inteiro
                    if (objeto instanceof Identificavel) {
                        if(((Identificavel) objeto).getId() == Integer.parseInt(escolha)) {
                            escolhidos.add(objeto);
                            i++;
                        }
                    }
                } else if (objeto instanceof Nomeavel) {
                    if(((Nomeavel) objeto).getNome().equals(escolha)) {
                        escolhidos.add(objeto);
                        i++;

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
}
