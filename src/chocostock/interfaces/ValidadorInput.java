package chocostock.interfaces;

import chocostock.auxiliar.Processa;

import java.text.Normalizer;
import java.util.Scanner;

/**
 * Interface que define um método padrão para obter entrada do usuário com validação.
 * Também define uma interface interna para validadores de entrada.
 */
public interface ValidadorInput {

    /**
     * Obtém a entrada do usuário com validação.
     *
     * @param scanner O objeto Scanner para entrada do usuário.
     * @param prompt A mensagem exibida para solicitar a entrada do usuário.
     * @param mensagemErro A mensagem exibida quando a entrada do usuário é inválida.
     * @param validador O validador de entrada para verificar se a entrada do usuário é válida.
     * @return A entrada do usuário válida.
     */
    default String getInput(Scanner scanner, String prompt, String mensagemErro, Validador validador) {
        String input, inputnorm;
        boolean entradaValida = false;

        do {
            System.out.println(prompt);
            input = scanner.nextLine();
            inputnorm = Processa.normaliza(input);

            if (!validador.isValid(inputnorm)) {
                System.out.println(mensagemErro);
            } else {
                entradaValida = true;
            }
        } while (!entradaValida);

        return input;
    }

    /**
     * Interface interna que define um método para validar entrada do usuário.
     */
    interface Validador {
        /**
         * Verifica se a entrada do usuário é válida.
         *
         * @param input A entrada do usuário a ser validada.
         * @return true se a entrada do usuário for válida, caso contrário false.
         */
        boolean isValid(String input);
    }
}