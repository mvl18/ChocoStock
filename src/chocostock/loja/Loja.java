package chocostock.loja;

import chocostock.auxiliar.Endereco;
import chocostock.interfaces.ValidadorInput;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Colaborador;
import chocostock.auxiliar.Verifica;
import chocostock.enums.Status;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Escolhivel;
import chocostock.itens.Item;
import chocostock.itens.materiais.Ingrediente;
import chocostock.itens.materiais.TiposIngredientes;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loja implements AddRemovivel, Escolhivel, ValidadorInput {
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

    public int getNumeroPedidos() {return this.pedidos.size();}

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

     public Ingrediente novoIngrediente(Scanner input) {
        Ingrediente ingrediente = new Ingrediente();
        int opcao;
        String texto;
        //Tipo
        System.out.println("Escolha um tipo de ingrediente para adicionar:");
        estoque.imprimirIngredientes();
        opcao = input.nextInt();
        input.nextLine();
        ingrediente.setTipo(TiposIngredientes.getTipoPorId(opcao));
        ingrediente.setNome(ingrediente.getTipo().getNome());
        //Quantidade
        System.out.println("Quantas unidades foram compradas?");
        ingrediente.setQuantidade(input.nextInt());
        input.nextLine();
        //Unidade
        System.out.println("Quantos kg por unidade:");
        texto = input.nextLine();
        ingrediente.setUnidade(Float.parseFloat(texto));
        //Preco
        System.out.println("Digite o preco da compra:");
        texto = input.nextLine();
        ingrediente.setPreco(Float.parseFloat(texto));
        //Data Compra e Validade
        System.out.println("Digite a data da compra (dd/mm/yyyy)");
        ingrediente.setDataCompra(escolheData(input));
        System.out.println("Digite a data de validade: (dd/mm/yyyy)");
        ingrediente.setValidade(escolheData(input));
        //Fornecedor
        System.out.println("Fornecedores atuais:");
        System.out.println("Nao Implementado.");
        return ingrediente;
    }
}