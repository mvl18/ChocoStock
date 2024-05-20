package chocostock.interfaces;

import chocostock.auxiliar.Verifica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Essa interface cria um método pardrão com diversas sobrecargas com diferentes
 * utilidades. Seu intuito é ser usada para facilitar pegar o input do usuário,
 * sendo que ele deve escolher algo de uma lista de opções.
 */
public interface Escolhivel extends Iteravel, ValidadorInput {
    /**
     * Escolhe um objeto da lista com base nas entradas fornecidas.
     *
     * @param scanner O scanner para entrada do usuário.
     * @param lista A lista de objetos disponíveis para escolha.
     * @param mensagemErro A mensagem que o usuário irá receber se o input não for válido.
     * @param mensagemSaida A mensagem que o usuário pode digitar para sair da seleção.
     * OBS.: colocar mensagemSaida = "obrigatorio" faz com que o usuário não consiga sair da seleção.
     * @param n_escolhas O número máximo de escolhas que o usuário pode fazer.
     * @return Uma lista de objetos escolhidos pelo usuário ou o objeto escolhido pelo usuario.
     */
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, ArrayList<T> lista, String mensagemErro, String mensagemSaida, int n_escolhas) { // posso criar uma String mensagemSaida para voce poder definir o que vc quiser
        ArrayList<T> escolhidos = new ArrayList<>();

        if (mensagemErro.isEmpty()) {
            mensagemErro = "Input inválido.";
        }

        int i = 0;
        while (i < n_escolhas) {
            String escolha = scanner.nextLine();
            if (!mensagemSaida.equals("obrigatorio")) {
                if (escolha.equals(mensagemSaida)) {
                    break;
                }
            }
            boolean input_valido = false;
            for (T objeto : lista) {
                if (escolha.matches("\\d+")) {  // verifica se a string escolha é um inteiro
                    if (objeto instanceof Identificavel) {
                        if (((Identificavel) objeto).getId() == Integer.parseInt(escolha)) {
                            input_valido = true;
                            if (escolhidos.contains(objeto)) {
                                System.out.println("Já escolhido. Escolha outra opção.");
                                break;
                            }
                            escolhidos.add(objeto);
                            i++;
                            break;
                        }
                    }
                } else {
                    if (objeto instanceof Nomeavel) {
                        if (((Nomeavel) objeto).getNome().equals(escolha)) {
                            input_valido = true;
                            if (escolhidos.contains(objeto)) {
                                System.out.println("Já escolhido. Escolha outra opção.");
                                break;
                            }
                            escolhidos.add(objeto);
                            i++;
                            break;
                        }
                    }
                    if (objeto instanceof Codificavel) {
                        if (((Codificavel) objeto).getCodigo().equals(escolha)) {
                            input_valido = true;
                            if (escolhidos.contains(objeto)) {
                                System.out.println("Já escolhido. Escolha outra opção.");
                                break;
                            }
                            escolhidos.add(objeto);
                            i++;
                            break;
                        }
                    }
                }
            }
            System.out.print(escolhidos.size() > 1 ? "Escolhidos: " : "Escolhido: ");
            System.out.println(listaHorizontal(escolhidos));

            if (!input_valido) {
                System.out.println(mensagemErro);
            }

        }

        return escolhidos;
    }
    /**
     * Sobrecarga do método escolheObjeto sem o parâmetro de número de escolhas.
     */
    default <T> T escolheObjeto(Scanner scanner, ArrayList<T> lista, String mensagemErro, String mensagemSaida) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, mensagemErro, mensagemSaida, 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }

    // Sobrecargas adicionais para arrays do C
    default <T> ArrayList<T> escolheObjeto(Scanner scanner, T[] lista, String mensagemErro, String mensagemSaida, int n_escolhas) {
        return escolheObjeto(scanner, new ArrayList<>(Arrays.asList(lista)), mensagemErro, mensagemSaida, n_escolhas);
    }

    /**
     * Sobrecarga do método escolheObjeto sem o parâmetro de número de escolhas.
     */
    default <T> T escolheObjeto(Scanner scanner, T[] lista, String mensagemErro, String mensagemSaida) {
        ArrayList<T> objeto = escolheObjeto(scanner, lista, mensagemErro, mensagemSaida, 1);
        if (objeto.isEmpty()) {
            return null;
        }

        return objeto.get(0);
    }

    default LocalDate escolheDataFutura(Scanner scanner, String prompt, String mensagemErro) { // colocar para outro lugar, pq aqui n faz sentido sendo q usa essa funcao até no ingrediente
        return LocalDate.parse((getInput(scanner, prompt, mensagemErro, Verifica::isDataFutura)), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    default LocalDate escolheData(Scanner scanner, String prompt, String mensagemErro) {
        return LocalDate.parse((getInput(scanner, prompt, mensagemErro, Verifica::isData)), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
