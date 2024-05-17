package chocostock.auxiliar;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Verifica {

    public static boolean isTelefone(String telefone) {
        String teste_telefone = telefone.replaceAll("\\D", "");
        return teste_telefone.length() >= 10;
    }

    public static boolean isEmail(String email) {
        String teste_email = "^[\\w+._]{3,}+@\\w+\\.\\w{2,}(?:\\.\\w{2,})?(?:\\.\\w{2,})?$";
        return email.matches(teste_email);
    }

    public static boolean isCep(String cep) {
        String teste_cep = cep.replaceAll("\\D", "");
        return teste_cep.length() >= 7 && teste_cep.length() <= 8;
    }

    public static boolean isCnpj(String cnpj) {
        int soma = 0, peso = 2, digito1, digito2, tamanho = cnpj.length() - 2;
        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        for (int i = tamanho - 1; i >= 0; i--) {
            soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }

        int digito = 11 - (soma % 11);
        digito1 = (digito >= 10) ? 0 : digito;

        soma = 0;
        peso = 2;
        for (int i = tamanho; i >= 0; i--) {
            soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }

        digito = 11 - (soma % 11);
        digito2 = (digito >= 10) ? 0 : digito;

        return (digito1 == Integer.parseInt(cnpj.substring(tamanho, tamanho + 1)) &&
                digito2 == Integer.parseInt(cnpj.substring(tamanho + 1)));
    }

    public static boolean isSite(String site) {
        String regex = "^(http(s)?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/[a-zA-Z0-9-._?%&=]*)?$";
        return Pattern.matches(regex, site);
    }

    public static boolean isNumero(String numero) {
        return numero.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isNatural(String numero) {
        return numero.matches("[1-9]\\d*");
    }

    public static boolean isFloat(String numero) {
        return numero.matches("[1-9]\\d*(\\.\\d+)?|0([.,])\\d+");
    }

    public static boolean isNome(String nome) {
        return nome.matches("[A-Za-zÀ-ÿ'-]+( [A-Za-zÀ-ÿ'-]+)*");
    }


    public static boolean isData(String dataString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate data = LocalDate.parse(dataString, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

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