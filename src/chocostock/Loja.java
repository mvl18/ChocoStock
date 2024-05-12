package chocostock;

import chocostock.colaboladores.Cliente;
import chocostock.colaboladores.Colaborador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Loja implements AddRemove, Escolhivel {
    private String descricao;
    private Endereco endereco;
    private ArrayList<Pedido> pedidos;
    private Estoque estoque;
    private ArrayList<Cliente> clientes;
    private ArrayList<Colaborador> funcionarios;

    public Loja(String descricao, Endereco endereco) {
        this.descricao = descricao;
        this.endereco = endereco;
        this.pedidos = new ArrayList<Pedido>();
        this.estoque =  new Estoque(new ArrayList<Item>(), new ArrayList<Item>(), new ArrayList<Item>());
        this.clientes = new ArrayList<Cliente>();
        this.funcionarios = new ArrayList<Colaborador>();
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

    public ArrayList<Colaborador> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Colaborador> funcionarios) {
        this.funcionarios = funcionarios;
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

    public String listaClientes() {
        String texto = "";
        for (Cliente cliente : clientes) {
            texto += cliente.toString() + "\n";
        }

        return texto;
    }

    public String listaPedidos() {
        String texto = "";
        for (Pedido pedido : pedidos) {
            texto += pedido.toString() + "\n";
        }

        return texto;
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
                // .out.println(loja.getClientes());
                System.out.println(listaClientes());
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
        // escolheData();
        System.out.println("Ainda nao implementado");
        // PAGO OU N
        System.out.println("O pedido feito ja foi pago? Sim OU Nao");
        boolean pago = scanner.nextBoolean();
        scanner.nextLine();
        pedido.setPago(pago);
        if (pago) {
            System.out.println("Pedido foi marcado como pago!");
        } else {
            System.out.println("Pedido foi marcado como nao pago!");
        }

        // STATUS
        for (Status status : Status.values()) {
            System.out.println(status.getId() + "-" + status.getNome());
        }
        System.out.println("Qual o status do pedido dentre os acima? ");
        ArrayList<Status> listaStatus = new ArrayList<>(Arrays.asList(Status.values())); // tranforma Array de C em ArrayLista<> do Java
        pedido.setStatus(escolheObjeto(scanner, listaStatus));

        System.out.println("O status do seu pedido foi definido para " + pedido.getStatus().getNome() + ".");
        // PRODUTOS_PENDENTES
        System.out.println("Selecione qual produto precisa ser adicionado ao pedido. ");
        // PRECO TOTAL

        return pedido;
    }

    public Cliente novoCliente(Scanner scanner) {
        Cliente cliente = new Cliente();
        System.out.println("Cadastrando novo cliente: ");
        // Cliente(String nome, String telefone, String email, Endereco endereco)
        // NOME
        System.out.println("Nome do cliente: ");
        cliente.setNome(scanner.nextLine());
        // TELEFONE
        System.out.println("Telefone do cliente: ");
        cliente.setTelefone(scanner.nextLine()); // MATHEUS regex
        // EMAIL
        System.out.println("Email do cliente: ");
        cliente.setEmail(scanner.nextLine());
        // ENDERECO
        System.out.println("Criando endereco: ");
        cliente.setEndereco(cliente.criaEndereco(scanner));

        return cliente;
    }
}