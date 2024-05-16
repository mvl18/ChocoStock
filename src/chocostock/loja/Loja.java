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
import chocostock.enums.TiposIngredientes;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.chrono.MinguoEra;
import java.time.format.DateTimeFormatter;
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
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite 'novo'");
                Cliente cliente = escolheObjeto(scanner, loja.getClientes(), "Cliente inexistente. Digite o ID ou nome de algum usuario listado.", "novo");
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
        System.out.println("Data inserida: " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(pedido.getData_entrega()));

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
        pedido.setStatus(escolheObjeto(scanner, Status.values(), "Status invalido. Digite um numero valido ou o nome do status.", "obrigatorio"));
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

    private LocalDate escolheData(Scanner scanner) { // colocar para outro lugar, pq aqui n faz sentido sendo q usa essa funcao até no ingrediente
        return LocalDate.parse((getInput(scanner, "Digite a data futura no formato dd/MM/yyyy: ",
                "Formato de data inválido. Por favor, insira a data futura no formato dd/MM/yyyy.",
                Verifica::isDataFutura)), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    public Cliente novoCliente(Scanner scanner) {
        Cliente cliente = new Cliente();
        System.out.println("Cadastrando novo cliente: ");
        // NOME
        cliente.setNome(getInput(scanner, "Nome do cliente: ", "Nome invalido.", Verifica::isNome));
        // TELEFONE
        cliente.setTelefone(getInput(scanner, "Telefone do cliente: ", "Insira um número válido, não esqueça o DDD!",
                Verifica::isTelefone).replaceAll("\\D", ""));
        // EMAIL
        cliente.setEmail(getInput(scanner, "Email do cliente: ", "Insira um email válido!", Verifica::isEmail));
        // ENDERECO
        System.out.println("Criando endereco: ");
        cliente.setEndereco(cliente.criaEndereco(scanner));

        return cliente;
    }

    /*
    BUGS:
    -Digita uma string quando pede INT ou FLOAT
    -Digita ID de ingrediente invalido (1-17)
     */
     public Ingrediente novoIngrediente(Scanner input) {
        Ingrediente ingrediente = new Ingrediente();
        int opcao;
        String texto;
        //Tipo
        System.out.println("Escolha um tipo de ingrediente para adicionar:");
        estoque.imprimirIngredientes();
        ingrediente.setTipo(escolheObjeto(input, TiposIngredientes.values(),
                "Numero ou nome invalido. Escolha um numero de (1-16) ou digite um nome valido.",
                "obrigatorio"));
//        opcao = input.nextInt();
//        input.nextLine();
        // ingrediente.setTipo(TiposIngredientes.getTipoPorId(opcao));
        ingrediente.setNome(ingrediente.getTipo().getNome());
        //Quantidade
//        System.out.println("Quantas unidades foram compradas?");
//        ingrediente.setQuantidade(input.nextInt());
//        input.nextLine();
        ingrediente.setQuantidade(Integer.parseInt(getInput(input, "Quantas unidades foram compradas?", "Quantidade invalida", Verifica::isNatural)));
        //Unidade
//        System.out.println("Quantos kg por unidade:");
//        texto = input.nextLine();
//        ingrediente.setUnidade(Float.parseFloat(texto));
        ingrediente.setUnidade(Float.parseFloat(getInput(input, "Quantos kg por unidade?", "Quantidade invalida, coloque um numero valido.", Verifica::isFloat)));
        //Preco
//        System.out.println("Digite o preco da compra:");
//        texto = input.nextLine();
//        ingrediente.setPreco(Float.parseFloat(texto));
        ingrediente.setPreco(Float.parseFloat(getInput(input, "Digite o preco da compra:", "Preco invalido, coloque um preco valido.", Verifica::isFloat)));
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