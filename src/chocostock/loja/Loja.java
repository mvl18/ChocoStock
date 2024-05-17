package chocostock.loja;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Processa;
import chocostock.colaboradores.Fornecedor;
import chocostock.colaboradores.Funcionario;
import chocostock.interfaces.Iteravel;
import chocostock.interfaces.ValidadorInput;
import chocostock.colaboradores.Cliente;
import chocostock.auxiliar.Verifica;
import chocostock.enums.Status;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Escolhivel;
import chocostock.itens.Item;
import chocostock.itens.materiais.Ingrediente;
import chocostock.enums.TiposIngredientes;
import chocostock.itens.produtos.Produto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        this.estoque =  new Estoque();
        this.clientes = new ArrayList<Cliente>();
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

    public ArrayList<Produto> escolheProdutos(Scanner scanner) {
        ArrayList<Produto> produtosEscolhidos = new ArrayList<Produto>();
        switch (verificaOpcao(scanner, new String[]{"PRODUTOS DO PEDIDO", "Adicionar produto.", "Listar produtos adicionados.", "Finalizar escolhas."}, 0)) {
            case 1:
                // selecionaProduto();

            case 2:
                // imprime produtosEscolhidos
            default:
                return produtosEscolhidos;
        }
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
        ingrediente.setDataCompra(escolheData(input, "Digite a data da compra: (dd/mm/yyyy)",
                "Formato de data inválido. Por favor, insira a data no formato dd/mm/yyyy."));
        ingrediente.setValidade(escolheDataFutura(input, "Digite a data de validade: (dd/mm/yyyy)",
                "Formato de data inválido. Por favor, insira uma data futura no formato dd/mm/yyyy."));

        //Fornecedor
        System.out.println("Fornecedores atuais:");
        System.out.println("Nao Implementado.");
        return ingrediente;
    }

}