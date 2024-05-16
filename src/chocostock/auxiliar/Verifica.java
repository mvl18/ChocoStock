package chocostock.auxiliar;

public class Verifica {
    public static boolean Telefone(String telefone) {
        String testando_telefone = telefone.replaceAll("\\D", "");
        return testando_telefone.length() >= 10;
    }

}