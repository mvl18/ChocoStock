package chocostock.interfaces;

import chocostock.auxiliar.Processa;
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
     * @return A entrada do usuário válida na forma de String().
     */
    default String getInput(Scanner scanner, String prompt, String mensagemErro, Validador validador) {
        String input, inputnorm;
        boolean entradaValida = false;

        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            inputnorm = Processa.normalizaString(input);

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


    /**
     * Obtém a entrada do usuário com validação.
     *
     * @param scanner O objeto Scanner para entrada do usuário.
     * @param mensagemOpcoes A mensagem exibida para solicitar a entrada do usuário.
     *                       Exemplo:
     *                msg = """
     *                 --- MENU COLABORADORES ---
     *                 (1) - Adicionar Cliente
     *                 (x) - Adicionar Fornecedor
     *                 (x) - Adicionar Funcionário
     *                 (4) - Listar Clientes
     *                 (5) - Listar Fornecedores
     *                 (x) - Listar Funcionario
     *                 (0) - Voltar para o menu inicial.""";
     * @param opcaoMin O menor número que o usuário pode digitar (nesse caso seria o 0).
     * @param opcaoMax  O maior número que o usuário pode digitar (nesse caso seria o "6").
     * @return O int digitado pelo usuário.
     * Exemplo de input: verificaOpcao(scanner, msg, 0, 6)
     */
    default int verificaOpcao(Scanner scanner, String mensagemOpcoes, int opcaoMin, int opcaoMax) {
        int resposta = 0;
        boolean opcaoValida = false;

        while (!opcaoValida) {
            System.out.println(mensagemOpcoes);
            if (scanner.hasNextInt()) {
                resposta = scanner.nextInt();
                scanner.nextLine();
                if (resposta >= opcaoMin && resposta <= opcaoMax) {
                    opcaoValida = true;
                } else {
                    System.out.println("Seleção inválida. Por favor, escolha uma opção de " + opcaoMin + " a " + opcaoMax + ".");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
            }
        }

        return resposta;
    }

    /**
     * Obtém a entrada do usuário com validação.
     *
     * @param scanner O objeto Scanner para entrada do usuário.
     * @param opcoes É uma lista de String() que tem como primeira String um título e o resto das Strings são as opções.
     * @param opcaoMin O menor número que o usuário pode digitar (nesse caso seria o 0).
     *                 OBS.: Se o número mínimo for 0, a opção 0 será a última passada.
     * @return O int digitado pelo usuário.
     * Exemplo de input: verificaOpcao(scanner, new String[]{"PRODUTOS DO PEDIDO", "Adicionar produto.", "Listar produtos adicionados", "Finalizar escolhas."}, 0)
     */
    default int verificaOpcao(Scanner scanner, String[] opcoes, int opcaoMin) {
        StringBuilder mensagemOpcoes = new StringBuilder();
        boolean inicioZero = opcaoMin == 0;
        int inicial = inicioZero ? 1 : opcaoMin;
        int fim = inicioZero ? (opcoes.length-1) : (opcoes.length);

        mensagemOpcoes.append("--- ").append(opcoes[0]).append(" ---\nSelecione uma das opções:\n");
        for (int i = inicial; i < fim; i++) {
            mensagemOpcoes.append("(").append(i).append(") - ").append(opcoes[i - inicial + 1]).append("\n");
        }
        if (inicioZero) {
            mensagemOpcoes.append("(0) - ").append(opcoes[opcoes.length - 1]);
        }

        return verificaOpcao(scanner, mensagemOpcoes.toString(), opcaoMin, opcaoMin + opcoes.length - 1);
    }
}