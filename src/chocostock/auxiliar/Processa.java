package chocostock.auxiliar;

import java.text.Normalizer;

/**
 * A classe Processa fornece métodos utilitários para normalizar strings e números. <br>
 * Implementa os métodos "normalizaString" e "normalizaNumero".
 */
public class Processa {
    /**
     * Normaliza uma string removendo acentuação, convertendo todos os caracteres
     * para minúsculas e removendo todos os espaços em branco.
     */
    public static String normalizaString(String entrada) {
        String entradaNormalizada = Normalizer.normalize(entrada, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("\\s", "");
        return entradaNormalizada;
    }

    /**
     * Normaliza uma string contendo números, removendo todos os caracteres
     * não numéricos.
     */
    public static String normalizaNumero(String numero) {
        return numero.replaceAll("[^0-9]", "");
    }
}
