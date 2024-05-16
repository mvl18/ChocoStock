package chocostock.loja;

import chocostock.auxiliar.Endereco;
import chocostock.colaboradores.Fornecedor;
import chocostock.colaboradores.Funcionario;
import chocostock.interfaces.Iteravel;
import chocostock.interfaces.ValidadorInput;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Colaborador;
import chocostock.auxiliar.Verifica;
import chocostock.enums.Status;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Escolhivel;
import chocostock.itens.Item;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loja implements AddRemovivel, Escolhivel, Iteravel, ValidadorInput {
    private String descricao;
    private Endereco endereco;
    private ArrayList<Pedido> pedidos;
    private Estoque estoque;
    private ArrayList<Cliente> clientes;
    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Fornecedor> fornecedores;

    public Loja(String descricao, Endereco endereco) {
        this.descricao = descricao;
        this.endereco = endereco;
        this.pedidos = new ArrayList<Pedido>();
        this.estoque =  new Estoque(new ArrayList<Item>(), new ArrayList<Item>(), new ArrayList<Item>());
        this.clientes = new ArrayList<Cliente>();
        this.funcionarios = new ArrayList<Funcionario>();
        this.fornecedores = new ArrayList<Fornecedor>();
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

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
        System.out.println("Novo pedido com id " + pedido.getId() + " criado com sucesso.\nQual cliente fez esse pedido? ");
        // CLIENTE
        System.out.println("1-Mostrar lista de clientes ja cadastrados.\n2-Adicionar novo cliente.");
        int resposta = scanner.nextInt();
        scanner.nextLine();
        switch (resposta) {
            case 1:
                System.out.println(listaClientes());
                System.out.println("Insira o ID ou nome do seu cliente");
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite 'sair'");
                Cliente cliente = escolheObjeto(scanner, loja.getClientes());
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
                System.out.println("Da proxima selecione uma resposta valida! Finalizando programa!");
                break;
        }
        // DATA_ENTREGA
        System.out.println("Qual a data de entrega do pedido? ");
        pedido.setData_entrega(escolheData(scanner));
        // PAGO OU N
        pedido.setPago(Normalizer.normalize(loja.getInput(scanner, "O pedido feito ja foi pago? Sim OU Nao", "Por favor, insira uma resposta valida. ",
                input -> input.matches("sim|nao")).toLowerCase().replaceAll("\\s", ""),
                Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").equals("sim"));
        System.out.println(pedido.isPago() ? "Pedido foi marcado como pago!" : "Pedido foi marcado como nao pago!");
        // STATUS
        for (Status status : Status.values()) {
            System.out.println(status.getId() + "-" + status.getNome());
        }
        System.out.println("Qual o status do pedido dentre os acima? ");
        pedido.setStatus(escolheObjeto(scanner, Status.values()));
        System.out.println("O status do seu pedido foi definido para " + pedido.getStatus().getNome() + ".");
        // PRODUTOS_PENDENTES
        System.out.println("Selecione qual produto precisa ser adicionado ao pedido. ");
        // pedido.setProdutos(escolheObjeto(scanner, )); // precisa ver com todos como fazer ALERT
        // pega_produtos_do_estoque() para tirar de pendentes
        // PRECO TOTAL
        pedido.calculaPrecoTotal();
        System.out.println("Preco total do pedido ficou: R$" + pedido.getPreco_total() + ".");


        return pedido;
    }

    private LocalDate escolheData(Scanner scanner) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = null;
        boolean dataValida = false;

        while (!dataValida) {
            System.out.println("Digite a data no formato dd/MM/yyyy:");
            String inputData = scanner.nextLine();

            try {
                data = LocalDate.parse(inputData, dateFormatter);
                if (!data.isBefore(LocalDate.now())) {
                    System.out.println("Data inserida: " + dateFormatter.format(data));
                    dataValida = true;
                } else {
                    System.out.println("Por favor, insira uma data futura.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Por favor, insira a data no formato dd/MM/yyyy.");
            }
        }

        return data;
    }


    public Cliente novoCliente(Scanner scanner) {
        Cliente cliente = new Cliente();
        System.out.println("Cadastrando novo cliente: ");
        // NOME
        cliente.setNome(getInput(scanner, "Nome do cliente: ", "Nome invalido.",
                input -> !input.matches(".*\\d.*")));
        // TELEFONE
        cliente.setTelefone(getInput(scanner, "Telefone do cliente: ", "Insira um número válido, não esqueça o DDD!",
                                    Verifica::Telefone).replaceAll("\\D", ""));
        // EMAIL
        System.out.println("Email do cliente: ");
        cliente.setEmail(scanner.nextLine());
        // ENDERECO
        System.out.println("Criando endereco: ");
        cliente.setEndereco(cliente.criaEndereco(scanner));

        return cliente;
    }
}