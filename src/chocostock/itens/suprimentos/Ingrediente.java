package chocostock.itens.suprimentos;

import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposIngredientes;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.Iteravel;
import chocostock.interfaces.ValidadorInput;
import chocostock.loja.Estoque;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * A classe Ingrediente representa um tipo específico de suprimento
 * que é utilizado na produção de produtos. No caso, os chocolates.
 */
public class Ingrediente extends Suprimento implements ValidadorInput, Escolhivel {

    private TiposIngredientes tipo;
    private float unidade; // Em kg
    private LocalDate dataCompra;
    private LocalDate validade;
    private String cnpj_fornecedor;

    public Ingrediente(String nome, int quantidade, float unidade, float preco,
                       LocalDate dataCompra, LocalDate validade, Fornecedor fornecedor) {
        super(nome, quantidade, preco, fornecedor);
        this.unidade = unidade;
        this.dataCompra = dataCompra;
        this.validade = validade;
    }

    public Ingrediente(String nome, int quantidade, float preco, Fornecedor fornecedor) {
        super(nome, quantidade, preco, fornecedor);
    }

    public Ingrediente() {
        this("", 0, 0.0f, new Fornecedor());
    }

    public TiposIngredientes getTipo() {
        return tipo;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public float getUnidade() {
        return unidade;
    }

    public void setTipo(TiposIngredientes tipo) {
        this.tipo = tipo;
    }

    public void setUnidade(float unidade) {
        this.unidade = unidade;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public String getCnpj_fornecedor() {
        return cnpj_fornecedor;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public void setCnpj_fornecedor(String cnpj_fornecedor) {
        this.cnpj_fornecedor = cnpj_fornecedor;
    }

    /**
     * Método para adicionar um novo ingrediente ao estoque com dados fornecidos pelo usuário.
     * Solicita ao usuário que insira as informações do ingrediente via console.
     */
    public static Ingrediente estocarIngrediente(Scanner input, Estoque estoque) {
        Ingrediente ingrediente = new Ingrediente();
        //TIPO
        System.out.println("Escolha um tipo de ingrediente para adicionar:");
        estoque.imprimirIngredientes();
        ingrediente.setTipo(Escolhivel.escolheObjeto(input, TiposIngredientes.values(),
                "Numero ou nome invalido. Escolha um numero de (1-16) ou digite um nome valido. ", "obrigatorio"));
        ingrediente.setNome(ingrediente.getTipo().getNome());

        //QUANTIDADE
        ingrediente.setQuantidade(Integer.parseInt(ValidadorInput.getInput(input, "Quantas unidades foram compradas? ", "Quantidade invalida", Verifica::isNatural)));

        //UNIDADE
        ingrediente.setUnidade(Float.parseFloat(ValidadorInput.getInput(input, "Quantos kg por unidade? ", "Quantidade invalida, coloque um numero valido.", Verifica::isFloat)));

        //PRECO
        ingrediente.setPreco(Float.parseFloat(ValidadorInput.getInput(input, "Digite o preco da compra: ", "Preco invalido, coloque um preco valido.", Verifica::isFloat)));

        //DATA DE COMPRA E VALIDADE
        ingrediente.setDataCompra(Escolhivel.escolheData(input, "Digite a data de compra: (dd/mm/yyyy) ", "Digite uma data válida."));
        ingrediente.setDataCompra(ingrediente.getDataCompra());
        ingrediente.setValidade(Escolhivel.escolheDataFutura(input, "Digite a data de validade: (dd/mm/yyyy) ", "Digite uma data futura válida."));

        //FORNECEDOR
        Fornecedor fornecedor;
        switch (ValidadorInput.verificaOpcao(input, new String[]{"FORNECEDORES", "Mostrar lista de fornecedores já cadastrados.", "Adicionar novo fornecedor."}, 1)) {
            case 1:
                Iteravel.listaVertical(estoque.getFornecedores());
                System.out.println("Seu Fornecedor não está na lista? Para adicionar um novo fornecedor digite 'novo'.");
                System.out.println("Insira o CNPJ ou nome do seu fornecedor");
                fornecedor = Escolhivel.escolheObjeto(input, estoque.getFornecedores(), "Fornecedor inexistente. Digite o CNPJ ou nome de algum fornecedor listado.", "novo");
                if (fornecedor == null) {
                    new Fornecedor();
                    fornecedor = Fornecedor.novoFornecedor(input);
                    estoque.addFornecedor(fornecedor);
                    ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                    ingrediente.setCnpj_fornecedor(ingrediente.getCnpj_fornecedor());
                    break;
                }
                ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            case 2:
                new Fornecedor();
                fornecedor = Fornecedor.novoFornecedor(input);
                estoque.addFornecedor(fornecedor);
                ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        return ingrediente;
    }

}


