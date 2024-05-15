package chocostock.auxiliar;

import java.text.Normalizer;
import java.util.Scanner;

public interface ValidadorInput {
    default String getInput(Scanner scanner, String prompt, String mensagemErro, Validador validador) {
        String input, inputnorm;
        boolean entradaValida = false;

        do {
            System.out.println(prompt);
            input = scanner.nextLine();
            inputnorm = Normalizer.normalize(input.toLowerCase().replaceAll("\\s", ""), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

            if (!validador.isValid(inputnorm)) {
                System.out.println(mensagemErro);
            } else {
                entradaValida = true;
            }
        } while (!entradaValida);

        return input;
    }

    interface Validador {
        boolean isValid(String input);
    }


}