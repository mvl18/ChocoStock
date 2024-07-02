package chocostock.loja;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Processa;
import chocostock.colaboradores.Funcionario;
import chocostock.enums.*;
import chocostock.interfaces.*;
import chocostock.colaboradores.Cliente;
import chocostock.auxiliar.Verifica;
import chocostock.itens.produtos.Pendente;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * A classe Loja representa uma loja que gerencia clientes,
 * funcionários,fornecedores, pedidos e estoque de produtos. <br>
 * Implementa os métodos "escolheProdutos", "selecionaProduto",
 * "selecionaCaixa", "selecionaBarra", "estocarIngrediente",
 * "novoCliente", "novoPedido".
 */
public class Loja implements AddRemovivel, Criavel, Escolhivel, Iteravel, ValidadorInput {
    private final String descricao;
    private final Endereco endereco;
    private final ArrayList<Pedido> pedidos;
    private final Estoque estoque;
    private static final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Funcionario> funcionarios;

    public Loja(String descricao, Endereco endereco) {
        this.descricao = descricao;
        this.endereco = endereco;
        this.pedidos = new ArrayList<>();
        this.estoque =  new Estoque();
        this.funcionarios = new ArrayList<>();
    }

    // DESCRIÇÃO
    public String getDescricao() {
        return descricao;
    }

    // ENDEREÇO
    public Endereco getEndereco() {
        return endereco;
    }

    // ESTOQUE
    public Estoque getEstoque() {
        return estoque;
    }

    // CLIENTES
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void addPedido(Pedido pedido) {
        addObjeto(pedidos, pedido);
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void addCliente(Cliente cliente) {
        addObjeto(clientes, cliente);
    }

    public String listaClientes() {
        return listaVertical(clientes);
    }

    public String listaPedidos() {
        return listaVertical(pedidos);
    }

    /**
     *  Método privado para escolher produtos para um pedido, usando um scanner para entrada do usuário.
     */
    public ArrayList<Pendente> escolheProdutos(Scanner scanner) {
        ArrayList<Pendente> produtos_escolhidos = new ArrayList<>();
        while (true) {
            switch (verificaOpcao(scanner, new String[]{"PRODUTOS DO PEDIDO", "Adicionar produto ao pedido.", "Listar produtos adicionados.", "Finalizar escolhas."}, 0)) {
                case 1: produtos_escolhidos.add(selecionaProduto(scanner));
                    break;
                case 2:
                    System.out.print(listaVertical(produtos_escolhidos));
                    break;
                default:
                    produtos_escolhidos.removeIf(Objects::isNull);
                    return produtos_escolhidos;
            }
        }
    }

    /**
     * Método privado para selecionar um tipo de produto, usando um scanner para entrada do usuário.
     */
    private Pendente selecionaProduto(Scanner scanner) {
        Pendente produtoPendente = new Pendente();
        return switch (verificaOpcao(scanner, new String[]{"TIPOS DE PRODUTO", "Barra.", "Caixa.", "Voltar."}, 0)) {
            case 1 -> selecionaBarra(scanner, produtoPendente);
            case 2 -> selecionaCaixa(scanner, produtoPendente);
            default -> null; // Se o usuario digitar 0
        };
    }

    /**
     * Permite ao usuário selecionar um tipo de caixa e a quantidade desejada.
     */
    private Pendente selecionaCaixa(Scanner scanner, Pendente produtoPendente) {
        // Lista todos os tipos de caixas disponíveis
        for (TiposCaixas tipo : TiposCaixas.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // Solicita ao usuário que selecione um tipo de caixa
        produtoPendente.setNome(escolheObjeto(scanner, TiposCaixas.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());
        // Solicita ao usuário a quantidade desejada da caixa selecionada
        produtoPendente.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;
    }

    /**
     * Permite ao usuário selecionar um tipo de barra de chocolate e complementos,
     * além da quantidade desejada.
     */
    private Pendente selecionaBarra(Scanner scanner, Pendente produtoPendente) {
        // Lista todos os tipos de chocolates disponíveis
        for (TiposChocolates tipo : TiposChocolates.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // Solicita ao usuário que selecione um tipo de chocolate
        produtoPendente.setNome(escolheObjeto(scanner, TiposChocolates.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());

        // Lista todos os tipos de complementos disponíveis
        for (TiposComplementos complemento : TiposComplementos.values()) {
            System.out.println("(" + complemento.getId() + ") - " + complemento.getNome());
        }
        // Opção para sair da seleção de complementos
        System.out.println("(0) - Sair");
        System.out.println("Selecione até " + TiposComplementos.values().length + " complementos diferentes.");
        // Solicita ao usuário que selecione complementos para a barra de chocolate
        produtoPendente.setComplementos(escolheObjeto(scanner, TiposComplementos.values(),
                "Por favor selecione um complemento válido.",
                "0", TiposComplementos.values().length));
        produtoPendente.getComplementos().removeIf(Objects::isNull);
        // Solicita ao usuário a quantidade desejada da barra de chocolate selecionada
        produtoPendente.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;
    }
}
