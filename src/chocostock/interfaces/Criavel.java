package chocostock.interfaces;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Verifica;
import chocostock.enums.Estados;

import java.text.Normalizer;
import java.util.Scanner;

public interface Criavel extends ValidadorInput, Escolhivel {

    default Endereco criaEndereco(Scanner scanner) {
        Endereco endereco = new Endereco();
        // CEP
        endereco.setCep(getInput(scanner, "CEP: ", "Insira um CEP válido!",
                Verifica::isCep).replaceAll("\\D", ""));
        // ESTADO
        endereco.achaEstado(endereco.getCep());
        if((Normalizer.normalize(getInput(scanner, endereco.getEstado().getNome() + " é o estado do endereço? (Sim ou Não) ", "Por favor, insira uma resposta valida. ",
                                input -> input.matches("sim|nao|s|n")).toLowerCase().replaceAll("\\s", ""),
                        Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .equals("nao"))) {
            System.out.print("Estado: ");
            endereco.setEstado(escolheObjeto(scanner, Estados.values(),
                    "Estado inválido. Por favor, digite a sigla ou nome de um dos estados válidos.",
                    "obrigatório"));
        }
        // CIDADE
        endereco.setCidade(getInput(scanner, "Cidade: ", "Cidade invalida. Coloque um nome valido.", Verifica::isNome));
        // BAIRRO
        endereco.setBairro(getInput(scanner, "Bairro: ", "Bairro invalida. Coloque um nome valido.", Verifica::isNome));
        // RUA
        endereco.setRua(getInput(scanner, "Rua: ", "Rua invalida. Coloque um nome valido.", Verifica::alwaysTrue));
        // NUMERO
        endereco.setNumero(Integer.parseInt(getInput(scanner, "Número do endereço: ", "Número inválido. Coloque um inteiro.", Verifica::isNatural)));

        return endereco;
    }
}
