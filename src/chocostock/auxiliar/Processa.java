package chocostock.auxiliar;

import java.text.Normalizer;

public class Processa {
    public static String normaliza(String entrada) {
        String entradaNormalizada = Normalizer.normalize(entrada, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("\\s", "");
        return entradaNormalizada;
    }
}
