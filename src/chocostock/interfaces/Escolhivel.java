package chocostock.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
* Essa interface cria um método pardrão com diversas sobrecargas com diferentes utilidades.
* Seu intuito é ser usada para facilitar pegar o input do usuário, sendo que ele deve escolher algo de uma
* lista de opções.
* */
public interface Escolhivel {
    /**
     * Escolhe um objeto da lista com base nas entradas fornecidas.
     *
     * @param scanner O scanner para entrada do usuário.
     * @param lista A lista de objetos disponíveis para escolha.
     * @param mensagemSaida A mensagem que o usuário pode digitar para sair da seleção.
     * OBS.: colocar mensagemSaida = "obrigatorio" faz com que o usuário não consiga sair da seleção.
     * @param n_escolhas O número máximo de escolhas que o usuário pode fazer.
     * @return Uma lista de objetos escolhidos pelo usuário ou o objeto escolhido pelo usuario.
     */
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, ArrayList<T> lista, String mensagemSaida, int n_escolhas) { // posso criar uma String mensagemSaida para voce poder definir o que vc quiser
        ArrayList<T> escolhidos = new ArrayList<T>();
        int i = 0;
        while (i < n_escolhas) {
            String escolha = scanner.nextLine();
            if (!mensagemSaida.equals("obrigatorio")) {
                if (escolha.equals(mensagemSaida)) {
                    break;
                }
            }
            int tamanho = lista.size();
            for (int j = 0; j < tamanho; j++) {
                T objeto = lista.get(j);
                if (escolha.matches("\\d+")) {  // verifica se a string escolha é um inteiro
                    if (objeto instanceof Identificavel) {
                        if (((Identificavel) objeto).getId() == Integer.parseInt(escolha)) {
                            escolhidos.add(objeto);
                            i++;
                            break;
                        }
                    }
                } else {
                    if (objeto instanceof Nomeavel) {
                        if (((Nomeavel) objeto).getNome().equals(escolha)) {
                            escolhidos.add(objeto);
                            i++;
                            break;
                        }
                    }
                    if (objeto instanceof Codificavel) {
                        if (((Codificavel) objeto).getCodigo().equals(escolha)) {
                            escolhidos.add(objeto);
                            i++;
                            break;
                        }
                    }
                }
            }
        }

        return escolhidos;
    }
    /**
     * Sobrecarga do método escolheObjeto sem o parâmetro de número de escolhas.
     */
    default <T> T escolheObjeto(Scanner scanner, ArrayList<T> lista, String mensagemSaida) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, mensagemSaida, 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }

    /**
     * Sobrecarga do método escolheObjeto sem a mensagem de saída.
     */
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, ArrayList<T> lista, int n_escolhas) {
        return escolheObjeto(scanner, lista, "", n_escolhas);
    }

    /**
     * Sobrecarga do método escolheObjeto sem o número máximo de escolhas e sem a mensagem de saída.
     */
    default <T> T escolheObjeto(Scanner scanner, ArrayList<T> lista) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, "", 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }

    // Sobrecargas adicionais para arrays
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, T[] lista, String mensagemSaida, int n_escolhas) {
        return escolheObjeto(scanner, new ArrayList<>(Arrays.asList(lista)), mensagemSaida, n_escolhas);
    }

    /**
     * Sobrecarga do método escolheObjeto sem o parâmetro de número de escolhas.
     */
    default <T> T escolheObjeto(Scanner scanner, T[] lista, String mensagemSaida) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, mensagemSaida, 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }

    /**
     * Sobrecarga do método escolheObjeto sem a mensagem de saída.
     */
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, T[] lista, int n_escolhas) {
        return escolheObjeto(scanner, new ArrayList<>(Arrays.asList(lista)), "", n_escolhas);
    }

    /**
     * Sobrecarga do método escolheObjeto sem o número máximo de escolhas e sem a mensagem de saída.
     */
    default <T> T escolheObjeto(Scanner scanner, T[] lista) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, "", 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }
}
