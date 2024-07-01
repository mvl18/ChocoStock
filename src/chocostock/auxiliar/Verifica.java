package chocostock.auxiliar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * A classe Verifica fornece métodos estáticos para validar diferentes tipos de entradas
 * de dados comuns, como números de telefone, endereços de email, CEPs, CNPJs, URLs de sites,
 * números, nomes e datas. <br>
 * Implementa os métodos "isTelefone", "isEmail", "isCep", "isCnpj", "isSite"...
 */
public class Verifica {
    /**
     * Um número de telefone é considerado válido se tiver pelo menos 10
     * dígitos após remover todos os caracteres não numéricos.
     */
    public static boolean isTelefone(String telefone) {
        String teste_telefone = telefone.replaceAll("\\D", "");
        return teste_telefone.length() >= 10;
    }

    /**
     * Verifica se a string fornecida é um endereço de email válido
     * com base em uma expressão regular.
     */
    public static boolean isEmail(String email) {
        String teste_email = "^[\\w+._]{3,}+@\\w+\\.\\w{2,}(?:\\.\\w{2,})?(?:\\.\\w{2,})?$";
        return email.matches(teste_email);
    }

    /**
     * Um CEP é considerado válido se tiver entre 7 e 8 dígitos após remover
     * todos os caracteres não numéricos e o primeiro dígito não for zero.
     */
    public static boolean isCep(String cep) {
        String teste_cep = cep.replaceAll("\\D", "");
        return teste_cep.length() >= 7 && teste_cep.length() <= 8 && teste_cep.charAt(0) != '0';
    }

    /**
     * A validação do CNPJ inclui a remoção de caracteres não numéricos,
     * verificação do comprimento e cálculo dos dígitos verificadores.
     */
    public static boolean isCnpj(String cnpj) {
        int soma = 0, peso = 5;
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ tem exatamente 14 dígitos e não é uma sequência de dígitos repetidos
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        for (int i = 0; i < cnpj.length()-2; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }

        int digito = 11 - (soma % 11);
        int digito1 = (digito >= 10) ? 0 : digito;

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cnpj.charAt(12)) != digito1) {
            return false;
        }

        // Reinicia a soma e o peso para calcular o segundo dígito verificador
        soma = 0;
        peso = 6;
        for (int i = 0; i < cnpj.length()-1; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }

        digito = 11 - (soma % 11);
        int digito2 = (digito >= 10) ? 0 : digito;

        // Verifica o segundo dígito verificador
        return (Character.getNumericValue(cnpj.charAt(13)) == digito2);
    }

    /**
     * Verifica se a string fornecida é uma URL de site válida
     * com base em uma expressão regular.
     */
    public static boolean isSite(String site) {
        String regex = "^(http(s)?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/[a-zA-Z0-9-._?%&=]*)?$";
        return Pattern.matches(regex, site);
    }

    /**
     * Verifica se a string fornecida é um número natural (inteiro positivo).
     */
    public static boolean isNatural(String numero) {
        return numero.replaceAll(" ", "").matches("[1-9]\\d*");
    }

    /**
     * Verifica se a string fornecida é um número de ponto flutuante.
     */
    public static boolean isFloat(String numero) {
        return numero.matches("[1-9]\\d*(\\.\\d+)?|0([.,])\\d+");
    }

    /**
     * Verifica se a string fornecida é um nome válido, composto por
     * letras, incluindo caracteres acentuados, apóstrofos e hifens,
     * com possíveis espaços entre nomes.
     */
    public static boolean isNome(String nome) {
        return nome.matches("[A-Za-zÀ-ÿ'-]+( [A-Za-zÀ-ÿ'-]+)*");
    }

    /**
     * Verifica se a string fornecida é uma data válida no formato "dd/MM/yyyy".
     */
    public static boolean isData(String dataString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate data = LocalDate.parse(dataString, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Verifica se a string fornecida é uma data no formato "dd/MM/yyyy"
     * e se a data é futura ou não.
     */
    public static boolean isDataFutura(String dataString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate data = LocalDate.parse(dataString, dateFormatter);
            return (!data.isBefore(LocalDate.now()));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}