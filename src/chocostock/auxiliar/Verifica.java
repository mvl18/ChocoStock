package chocostock.auxiliar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static boolean isNumero(String numero) {
        return numero.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isData(String dataString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate data = LocalDate.parse(dataString, dateFormatter);
            if (!data.isBefore(LocalDate.now())) {
                return true;
            } else {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}