package chocostock;

public class Main {
    public static void main(String[] args) {

        // Exemplo de instanciação de endereço e de mudança de estado
        Endereco endereco = new Endereco(34,"1403128", "Pitágoras", "Limoeiro", "Gotham", Estados.SP);
        System.out.println(endereco);
        endereco.setEstado(Estados.AC);
        System.out.println(endereco);
    }
}