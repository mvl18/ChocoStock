package chocostock.loja;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Processa;
import chocostock.colaboradores.Fornecedor;
import chocostock.colaboradores.Funcionario;
import chocostock.enums.*;
import chocostock.interfaces.*;
import chocostock.colaboradores.Cliente;
import chocostock.auxiliar.Verifica;
import chocostock.itens.materiais.Ingrediente;
import chocostock.itens.produtos.Pendente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Loja implements AddRemovivel, Escolhivel, Iteravel, ValidadorInput, Criavel {
    private String descricao;
    private Endereco endereco;
    private ArrayList<Pedido> pedidos;
    private Estoque estoque;
    private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Fornecedor> fornecedores;

    public Loja(String descricao, Endereco endereco) {
        this.descricao = descricao;
        this.endereco = endereco;
        this.pedidos = new ArrayList<Pedido>();
        this.estoque =  new Estoque();
        this.funcionarios = new ArrayList<Funcionario>();
        this.fornecedores = new ArrayList<Fornecedor>();
    }

    // DESCRIÇÃO
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // ENDEREÇO
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // PEDIDOS
    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    // ESTOQUE
    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    // CLIENTES
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    // FUNCIONÁRIOS
    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    // FORNECEDORES
    public ArrayList<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(ArrayList<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public boolean addPedido(Pedido pedido) {
        return addObjeto(pedidos, pedido);
    }

    public boolean removePedido(Pedido pedido) {
        return removeObjeto(pedidos, pedido);
    }

    public boolean addCliente(Cliente cliente) {
        return addObjeto(clientes, cliente);
    }

    public boolean removeCliente(Cliente cliente) {
        return removeObjeto(clientes, cliente);
    }

    public boolean addFornecedor(Fornecedor fornecedor) {
        return addObjeto(fornecedores, fornecedor);
    }

    public boolean removeFornecedor(Fornecedor fornecedor) {
        return removeObjeto(fornecedores, fornecedor);
    }

    public int getNumeroPedidos() {return this.pedidos.size();}

    public String listaClientes() {
        return listaObjetos(clientes);
    }

    public String listaFornecedores() {
        return listaObjetos(fornecedores);
    }

    public String listaPedidos() {
        return listaObjetos(pedidos);
    }

    public Pedido novoPedido(Scanner scanner, Loja loja)  {
        Pedido pedido = new Pedido();
        // CLIENTE
        switch (verificaOpcao(scanner, new String[]{"NOVO PEDIDO", "Mostrar lista de clientes já cadastrados.", "Adicionar novo cliente."}, 1)) {
            case 1:
                System.out.println(listaClientes());
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite 'novo'.");
                System.out.println("Insira o ID ou nome do seu cliente");
                Cliente cliente = escolheObjeto(scanner, loja.getClientes(), "Cliente inexistente. Digite o ID ou nome de algum usuário listado.", "novo");
                if (cliente == null) {
                    Cliente cliente2 = novoCliente(scanner);
                    loja.addCliente(cliente2);
                    pedido.setId_cliente(cliente2.getId());
                    break;
                }
                pedido.setId_cliente(cliente.getId());
                break;
            case 2:
                Cliente cliente2 = novoCliente(scanner);
                loja.addCliente(cliente2);
                pedido.setId_cliente(cliente2.getId());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        // PRODUTOS_PENDENTES
        System.out.println("Selecione qual produto precisa ser adicionado ao pedido. ");
        pedido.setProdutos_pendentes(escolheProdutos(scanner));
        // pega_produtos_do_estoque() para tirar de pendentes

        // DATA_ENTREGA
        System.out.println("Qual a data de entrega do pedido? ");
        pedido.setData_entrega(escolheDataFutura(scanner, "Digite a data futura no formato dd/MM/yyyy: "
        ));
        System.out.println("Data inserida: " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(pedido.getData_entrega()));

        // PAGO OU N
        pedido.setPago(Processa.normaliza(loja.getInput(scanner, "O pedido feito já foi pago? Sim OU Não", "Por favor, insira uma resposta valida. ",
                input -> input.matches("sim|nao|s|n"))).equals("sim|s"));
        System.out.println(pedido.isPago() ? "Pedido foi marcado como pago!" : "Pedido foi marcado como nao pago!");

        // STATUS
        for (Status status : Status.values()) {
            System.out.println(status.getId() + "-" + status.getNome());
        }
        System.out.println("Qual o status do pedido dentre os acima? ");
        pedido.setStatus(escolheObjeto(scanner, Status.values(), "Status inválido. Digite um número válido ou o nome do status.", "obrigatorio"));
        System.out.println("O status do seu pedido foi definido para " + pedido.getStatus().getNome() + ".");

        // PRECO TOTAL
        pedido.calculaPrecoTotal();
        System.out.println("Preco total do pedido ficou: R$" + pedido.getPreco_total() + ".");

        return pedido;
    }

    private ArrayList<Pendente> escolheProdutos(Scanner scanner) {
        ArrayList<Pendente> produtos_escolhidos = new ArrayList<Pendente>();
        while (true) {
            switch (verificaOpcao(scanner, new String[]{"PRODUTOS DO PEDIDO", "Adicionar produto ao pedido.", "Listar produtos adicionados.", "Finalizar escolhas."}, 0)) {
                case 1: produtos_escolhidos.add(selecionaProduto(scanner));
                    break;
                case 2:
                    System.out.println(produtos_escolhidos);
                    break;
                default:
                    produtos_escolhidos.removeIf(Objects::isNull);
                    return produtos_escolhidos;
            }
        }
    }

    private Pendente selecionaProduto(Scanner scanner) {
        Pendente produtoPendente = new Pendente();
        return switch (verificaOpcao(scanner, new String[]{"TIPOS DE PRODUTO", "Barra.", "Caixa.", "Voltar."}, 0)) {
            case 1 -> selecionaBarra(scanner, produtoPendente);
            case 2 -> selecionaCaixa(scanner, produtoPendente);
            default -> null; // se o usuario digitar 0
        };
    }

    private Pendente selecionaCaixa(Scanner scanner, Pendente produtoPendente) {
        for (TiposCaixas tipo : TiposCaixas.values()) {
            System.out.println(tipo.getId() + "-" + tipo.getNome());
        }
        produtoPendente.setNome(escolheObjeto(scanner, TiposCaixas.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());

        produtoPendente.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ":",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;
    }

    private Pendente selecionaBarra(Scanner scanner, Pendente produtoPendente) {
        for (TiposChocolates tipo : TiposChocolates.values()) {
            System.out.println(tipo.getId() + "-" + tipo.getNome());
        }
        produtoPendente.setNome(escolheObjeto(scanner, TiposChocolates.values(), "Por favor selecione um tipo válido.", "obrigatorio").getNome());

        for (TiposComplementos complemento : TiposComplementos.values()) {
            System.out.println(complemento.getId() + "-" + complemento.getNome());
        }
        System.out.println("0-Sair");
        System.out.println("Selecione até " + TiposComplementos.values().length + " complementos diferentes.");
        ArrayList<TiposComplementos> complementos = escolheObjeto(scanner, TiposComplementos.values(),
                "Por favor selecione um complemento válido.",
                "0", TiposComplementos.values().length);
        for (TiposComplementos complemento : complementos) {
            produtoPendente.addComplemento(complemento.getNome());
        }

        produtoPendente.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ":",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;

    }

    private LocalDate escolheDataFutura(Scanner scanner, String prompt) { // colocar para outro lugar, pq aqui n faz sentido sendo q usa essa funcao até no ingrediente
        return LocalDate.parse((getInput(scanner, prompt, "Formato de data inválido. Por favor, insira uma data futura no formato dd/mm/yyyy.", Verifica::isDataFutura)), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private LocalDate escolheData(Scanner scanner) {
        return LocalDate.parse((getInput(scanner, "Digite a data da compra: (dd/mm/yyyy)", "Formato de data inválido. Por favor, insira a data no formato dd/mm/yyyy.", Verifica::isData)), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    public Cliente novoCliente(Scanner scanner) {
        Cliente cliente = new Cliente();
        System.out.println("Cadastrando novo cliente: ");
        // NOME
        cliente.setNome(getInput(scanner, "Nome do cliente: ", "Nome inválido.", Verifica::isNome));
        // TELEFONE
        cliente.setTelefone(getInput(scanner, "Telefone do cliente: ", "Insira um número válido, não esqueça o DDD!",
                Verifica::isTelefone).replaceAll("\\D", ""));
        // EMAIL
        cliente.setEmail(getInput(scanner, "Email do cliente: ", "Insira um email válido!", Verifica::isEmail));
        // ENDERECO
        System.out.println("Criando endereço: ");
        cliente.setEndereco(cliente.criaEndereco(scanner));

        return cliente;
    }

    public Ingrediente estocarIngrediente(Scanner input) {
        Ingrediente ingrediente = new Ingrediente();
        int opcao;
        String texto;

        //Tipo
        System.out.println("Escolha um tipo de ingrediente para adicionar:");
        getEstoque().imprimirIngredientes();
        ingrediente.setTipo(escolheObjeto(input, TiposIngredientes.values(),
                "Numero ou nome invalido. Escolha um numero de (1-16) ou digite um nome valido.", "obrigatorio"));
        ingrediente.setNome(ingrediente.getTipo().getNome());

        //Quantidade
        ingrediente.setQuantidade(Integer.parseInt(getInput(input, "Quantas unidades foram compradas?", "Quantidade invalida", Verifica::isNatural)));

        //Unidade
        ingrediente.setUnidade(Float.parseFloat(getInput(input, "Quantos kg por unidade?", "Quantidade invalida, coloque um numero valido.", Verifica::isFloat)));

        //Preco
        ingrediente.setPreco(Float.parseFloat(getInput(input, "Digite o preco da compra:", "Preco invalido, coloque um preco valido.", Verifica::isFloat)));

        //Data Compra e Validade
        ingrediente.setDataCompra(escolheData(input
        ));
        ingrediente.setValidade(escolheDataFutura(input, "Digite a data de validade: (dd/mm/yyyy)"
        ));

        //Fornecedor
        System.out.println("Fornecedores atuais:");
        System.out.println("Nao Implementado.");
        return ingrediente;
    }

}