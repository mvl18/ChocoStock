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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Loja implements AddRemovivel, Criavel, Escolhivel, Iteravel, ValidadorInput {
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
        produtoPendente.setComplementos(escolheObjeto(scanner, TiposComplementos.values(),
                "Por favor selecione um complemento válido.",
                "0", TiposComplementos.values().length));


        produtoPendente.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produtoPendente.getNome() + ":",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return produtoPendente;

    }

    public Ingrediente estocarIngrediente(Scanner input) {
        Ingrediente ingrediente = new Ingrediente();
        //TIPO
        System.out.println("Escolha um tipo de ingrediente para adicionar:");
        getEstoque().imprimirIngredientes();
        ingrediente.setTipo(escolheObjeto(input, TiposIngredientes.values(),
                "Numero ou nome invalido. Escolha um numero de (1-16) ou digite um nome valido.", "obrigatorio"));
        ingrediente.setNome(ingrediente.getTipo().getNome());

        //QUANTIDADE
        ingrediente.setQuantidade(Integer.parseInt(getInput(input, "Quantas unidades foram compradas?", "Quantidade invalida", Verifica::isNatural)));

        //UNIDADE
        ingrediente.setUnidade(Float.parseFloat(getInput(input, "Quantos kg por unidade?", "Quantidade invalida, coloque um numero valido.", Verifica::isFloat)));

        //PRECO
        ingrediente.setPreco(Float.parseFloat(getInput(input, "Digite o preco da compra:", "Preco invalido, coloque um preco valido.", Verifica::isFloat)));

        //DATA DE COMPRA E VALIDADE
        ingrediente.setDataCompra(escolheData(input, "Digite a data de compra: (dd/mm/yyyy)", "Digite uma data válida."));
        ingrediente.setValidade(escolheDataFutura(input, "Digite a data de validade: (dd/mm/yyyy)", "Digite uma data futura válida."));

        //FORNECEDOR
        Fornecedor fornecedor;
        switch (verificaOpcao(input, new String[]{"FORNECEDORES", "Mostrar lista de fornecedores já cadastrados.", "Adicionar novo fornecedor."}, 1)) {
            case 1:
                System.out.println(listaFornecedores());
                System.out.println("Seu Fornecedor não está na lista? Para adicionar um novo fornecedor digite 'novo'.");
                System.out.println("Insira o CNPJ ou nome do seu fornecedor");
                fornecedor = escolheObjeto(input, getFornecedores(), "Fornecedor inexistente. Digite o CNPJ ou nome de algum fornecedor listado.", "novo");
                if (fornecedor == null) {
                    fornecedor = novoFornecedor(input);
                    addFornecedor(fornecedor);
                    ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                    break;
                }
                ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            case 2:
                fornecedor = novoFornecedor(input);
                addFornecedor(fornecedor);
                ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        return ingrediente;
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
        cliente.setEndereco(criaEndereco(scanner));
        System.out.println("Novo cliente adicionado: " + cliente.toString());

        return cliente;
    }

    public Fornecedor novoFornecedor(Scanner scanner) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(getInput(scanner, "Nome do fornecedor: ", "Nome invalido. Insira novamente.", Verifica::isNome));
        fornecedor.setTelefone(getInput(scanner, "Telefone do fornecedor: ","Telefone inválido. Insira novamente.", Verifica::isTelefone));
        fornecedor.setEmail(getInput(scanner, "Email do fornecedor:", "Email inválido. Insira novamente.", Verifica::isEmail));
        fornecedor.setEndereco(criaEndereco(scanner));
        fornecedor.setCnpj(Processa.normalizaNumero(getInput(scanner, "CNPJ do fornecedor:", "CNPJ inválido. Insira novamente.", Verifica::isCnpj)));
        fornecedor.setSite(getInput(scanner, "Site do fornecedor:", "Site inválido. Insira novamente.", Verifica::isSite));
        return fornecedor;
    }

    public Pedido novoPedido(Scanner scanner)  {
        Pedido pedido = new Pedido();

        // CLIENTE
        Cliente cliente;
        switch (verificaOpcao(scanner, new String[]{"NOVO PEDIDO", "Mostrar lista de clientes já cadastrados.", "Adicionar novo cliente."}, 1)) {
            case 1:
                System.out.println(listaClientes());
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite 'novo'.");
                System.out.println("Insira o ID ou nome do seu cliente");
                cliente = escolheObjeto(scanner, getClientes(), "Cliente inexistente. Digite o ID ou nome de algum usuário listado.", "novo");
                if (cliente == null) {
                    cliente = novoCliente(scanner);
                    addCliente(cliente);
                    pedido.setId_cliente(cliente.getId());
                    break;
                }
                pedido.setId_cliente(cliente.getId());
                cliente.addPedido(pedido.getId());
                break;
            case 2:
                cliente = novoCliente(scanner);
                addCliente(cliente);
                pedido.setId_cliente(cliente.getId());
                cliente.addPedido(pedido.getId());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        // DATA_ENTREGA
        pedido.setData_entrega(escolheDataFutura(scanner, "Qual a data de entrega do pedido? Digite uma data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        System.out.println("Data inserida: " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(pedido.getData_entrega()));

        // PRODUTOS_PENDENTES
        System.out.println("Selecione qual produto precisa ser adicionado ao pedido. ");
        pedido.setProdutos_pendentes(escolheProdutos(scanner));

        // PRODUTOS
        pedido = estoque.retiraProdutosEstoque(pedido);
        System.out.println(estoque);
        System.out.println(pedido);

        // STATUS
        for (Status status : Status.values()) {
            System.out.println(status.getId() + "-" + status.getNome());
        }
        System.out.println("Qual o status do pedido dentre os acima? ");
        pedido.setStatus(escolheObjeto(scanner, Status.values(), "Status inválido. Digite um número válido ou o nome do status.", "obrigatorio"));
        System.out.println("O status do seu pedido foi definido para " + pedido.getStatus().getNome() + ".");

        // PAGO OU N
        pedido.setPago(Processa.normalizaString(getInput(scanner, "O pedido feito já foi pago? Sim OU Não", "Por favor, insira uma resposta valida. ",
                input -> input.matches("sim|nao|s|n"))).equals("sim|s"));
        System.out.println(pedido.isPago() ? "Pedido foi marcado como pago!" : "Pedido foi marcado como nao pago!");

        // PRECO TOTAL
        pedido.calculaPrecoTotal();
        System.out.println("Preco total do pedido ficou: R$" + pedido.getPreco_total() + "."); // implementar

        return pedido;
    }




}
