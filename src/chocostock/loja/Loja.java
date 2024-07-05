package chocostock.loja;

import chocostock.auxiliar.Endereco;
import chocostock.colaboradores.Funcionario;
import chocostock.enums.*;
import chocostock.interfaces.*;
import chocostock.colaboradores.Cliente;
import chocostock.auxiliar.Verifica;
import chocostock.itens.produtos.Pendente;

import java.io.Serializable;
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
public class Loja implements AddRemovivel, Criavel, Escolhivel, Iteravel, ValidadorInput, Serializable {
    private static final long serialVersionUID = 1l;
    private String nome;
    private String telefone;
    private String descricao;
    private Endereco endereco;
    private final ArrayList<Pedido> pedidos;
    private final Estoque estoque;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Funcionario> funcionarios;

    public Loja(String nome, String descricao, String telefone, Endereco endereco) {
        this.nome = nome;
        this.descricao = descricao;
        this.telefone = telefone;
        this.endereco = endereco;
        this.pedidos = new ArrayList<>();
        this.estoque =  new Estoque();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
    }

    public Loja() {
        this.pedidos = new ArrayList<>();
        this.estoque =  new Estoque();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
    }

    // NOME
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // DESCRIÇÃO
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // TELEFONE
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    // ENDEREÇO
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // ESTOQUE
    public Estoque getEstoque() {
        return estoque;
    }

    // CLIENTES
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void addPedido(Pedido pedido) {
        addObjeto(pedidos, pedido);
    }

    //CLIENTE
    public void addCliente(Cliente cliente) {
        addObjeto(clientes, cliente);
    }

    public void removeCliente(Cliente cliente) { removeObjeto(clientes, cliente);}

    public void removeClientePorId(int id) {
        clientes.removeIf(cliente -> cliente.getId() == id);
    }

    public Cliente getClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public String listaClientes() {
        return Iteravel.listaVertical(clientes);
    }

    public String listaPedidos() {
        return Iteravel.listaVertical(pedidos);
    }

    public String[] arrayClientes() {
        String[] array = new String[clientes.size()];
        if (clientes.isEmpty())
            return new String[]{""};
        for (int i = 0; i < clientes.size(); i++)
            array[i] = clientes.get(i).getNome();
        return array;
    }

    public Cliente getCliente(String nome) {
        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                if (cliente.getNome().equals(nome))
                    return cliente;
            }
        }
        return new Cliente();
    }

    //FUNCIONARIO
    public void addFuncionario(Funcionario funcionario) {
        addObjeto(funcionarios, funcionario);
    }

    public String listaFuncionario() {
        return Iteravel.listaVertical(funcionarios);
    }

    /**
     *  Método privado para escolher produtos para um pedido, usando um scanner para entrada do usuário.
     */
    public ArrayList<Pendente> escolheProdutos(Scanner scanner) {
        ArrayList<Pendente> produtos_escolhidos = new ArrayList<>();
        while (true) {
            switch (ValidadorInput.verificaOpcao(scanner, new String[]{"PRODUTOS DO PEDIDO", "Adicionar produto ao pedido.", "Listar produtos adicionados.", "Finalizar escolhas."}, 0)) {
                case 1: produtos_escolhidos.add(selecionaProduto(scanner));
                    break;
                case 2:
                    System.out.print(Iteravel.listaVertical(produtos_escolhidos));
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
        return switch (ValidadorInput.verificaOpcao(scanner, new String[]{"TIPOS DE PRODUTO", "Barra.", "Caixa.", "Voltar."}, 0)) {
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
        produtoPendente.setNome(Escolhivel.escolheObjeto(scanner, TiposCaixas.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());
        // Solicita ao usuário a quantidade desejada da caixa selecionada
        produtoPendente.setQuantidade(Integer.parseInt(ValidadorInput.getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ": ",
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
        produtoPendente.setNome(Escolhivel.escolheObjeto(scanner, TiposChocolates.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());

        // Lista todos os tipos de complementos disponíveis
        for (TiposComplementos complemento : TiposComplementos.values()) {
            System.out.println("(" + complemento.getId() + ") - " + complemento.getNome());
        }
        // Opção para sair da seleção de complementos
        System.out.println("(0) - Sair");
        System.out.println("Selecione até " + TiposComplementos.values().length + " complementos diferentes.");
        // Solicita ao usuário que selecione complementos para a barra de chocolate
        produtoPendente.setComplementos(Escolhivel.escolheObjeto(scanner, TiposComplementos.values(),
                "Por favor selecione um complemento válido.",
                "0", TiposComplementos.values().length));
        produtoPendente.getComplementos().removeIf(Objects::isNull);
        // Solicita ao usuário a quantidade desejada da barra de chocolate selecionada
        produtoPendente.setQuantidade(Integer.parseInt(ValidadorInput.getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;
    }

    public Loja criarNovaLoja(Scanner scanner) {
        Loja novaLoja = new Loja();
        // Solicitação do nome da loja
        novaLoja.setNome(ValidadorInput.getInput(scanner, "Nome do loja: ", "Nome inválido.", Verifica::isNome));
        // Solicitação da descrição da loja
        novaLoja.setDescricao(ValidadorInput.getInput(scanner, "Descrição da loja: ", "Nome inválido.", input -> true));
        // Solicitação do telefone da loja
        novaLoja.setTelefone(ValidadorInput.getInput(scanner, "Telefone da loja: ", "Insira um número válido, não esqueça o DDD!",
                Verifica::isTelefone).replaceAll("\\D", ""));
        // Solicitação do endereço da loja
        Endereco endereco = new Endereco();
        novaLoja.setEndereco(endereco.criaEndereco(scanner));
        System.out.println("Loja " + novaLoja.getNome() + " criada com sucesso!");
        System.out.println("\n");

        return novaLoja;
    }
}
