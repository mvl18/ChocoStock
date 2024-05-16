package chocostock.auxiliar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Verifica {

    public static boolean isNumero(String numero) {
        return numero.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isTelefone(String telefone) {
        String testando_telefone = telefone.replaceAll("\\D", "");
        return testando_telefone.length() >= 10;
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