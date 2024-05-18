package chocostock.interfaces;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Escolhe;
import chocostock.auxiliar.Processa;
import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.Estados;
import chocostock.enums.Status;
import chocostock.loja.Loja;
import chocostock.loja.Pedido;

import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public interface Criavel extends ValidadorInput, Escolhivel {

    default Endereco criaEndereco(Scanner scanner) {
        //Endereco(int numero, String cep, String rua, String bairro, String cidade, Estados estado)
        Endereco endereco = new Endereco();
        // CEP
        endereco.setCep(getInput(scanner, "CEP: ", "Insira um CEP válido!",
                Verifica::isCep).replaceAll("\\D", ""));
        // ESTADO
        endereco.achaEstado(endereco.getCep());
        if((Normalizer.normalize(getInput(scanner, endereco.getEstado().getNome() + " é o estado do endereço? (Sim ou Não)", "Por favor, insira uma resposta valida. ",
                                input -> input.matches("sim|nao")).toLowerCase().replaceAll("\\s", ""),
                        Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .equals("nao"))) {
            System.out.println("Estado: ");
            endereco.setEstado(escolheObjeto(scanner, Estados.values(),
                    "Estado inválido. Por favor, digite a sigla ou nome de um dos estados válidos.",
                    "obrigatório"));
        }
        // CIDADE
        endereco.setCidade(getInput(scanner, "Cidade: ", "Cidade invalida. Coloque um nome valido.", Verifica::isNome));
        // BAIRRO
        endereco.setCidade(getInput(scanner, "Bairro: ", "Bairro invalida. Coloque um nome valido.", Verifica::isNome));
        // RUA
        endereco.setCidade(getInput(scanner, "Rua: ", "Rua invalida. Coloque um nome valido.", Verifica::isNome));
        // NUMERO
        endereco.setNumero(Integer.parseInt(getInput(scanner, "Número do endereço", "Número inválido. Coloque um inteiro.", Verifica::isNatural)));

        return endereco;
    }
}
