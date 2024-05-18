package chocostock.auxiliar;

import java.text.Normalizer;

public class Processa {
    public static String normalizaString(String entrada) {
        String entradaNormalizada = Normalizer.normalize(entrada, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("\\s", "");
        return entradaNormalizada;
    }

    public static String normalizaNumero(String numero) {
        return numero.replaceAll("[^0-9]", "");
    }
}
