package chocostock.itens.suprimentos;

import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.Escolhivel;
import chocostock.interfaces.ValidadorInput;
import chocostock.loja.Estoque;

import java.util.Scanner;

/**
 * A classe Embalagem é uma subclasse de Suprimento
 * que representa um tipo específico de suprimento
 * usado para embalar produtos e formar as caixas.
 */
public class Embalagem extends Suprimento implements ValidadorInput, Escolhivel {
    private TiposEmbalagens tipo_embalagem;
    private float preco_pacote;
    private int quantidade_por_pacote;
    private String cnpj_fornecedor;

    public Embalagem(String nome, Fornecedor fornecedor, TiposEmbalagens tipo_embalagem, float preco_pacote, int quantidade_por_pacote) {
        super(nome, 0, preco_pacote / quantidade_por_pacote, fornecedor);
        this.tipo_embalagem = tipo_embalagem;
        this.preco_pacote = preco_pacote;
        this.quantidade_por_pacote = quantidade_por_pacote;
    }

    public Embalagem(String nome, int quantidade, Fornecedor fornecedor, TiposEmbalagens tipo_embalagem, float preco_pacote, int quantidade_por_pacote) {
        this(nome, fornecedor, tipo_embalagem, preco_pacote, quantidade_por_pacote);
        super.setQuantidade(quantidade);
    }


    public Embalagem() {
        this("", 0, null, null, 0, 10);
    }

    public TiposEmbalagens getTipo_embalagem() {
        return tipo_embalagem;
    }

    public void setTipo_embalagem(TiposEmbalagens tipo_embalagem) {
        this.tipo_embalagem = tipo_embalagem;
    }


    public void setPreco_pacote(float preco_pacote){
        this.preco_pacote = preco_pacote;
    }

    public void setQuantidade_por_pacote(int quantidade_por_pacote){
        this.quantidade_por_pacote = quantidade_por_pacote;
    }

    public float getPreco_pacote() {
        return preco_pacote;
    }

    public int getQuantidade_por_pacote() {
        return quantidade_por_pacote;
    }


    public void setCnpj_fornecedor(String cnpj_fornecedor) {
        this.cnpj_fornecedor = cnpj_fornecedor;
    }

    /**
     * Método para adicionar uma nova embalagem ao estoque com dados fornecidos pelo usuário.
     * Solicita ao usuário que insira as informações da embalagem via console.
     */
    public static Embalagem estocarEmbalagem(Scanner input, Estoque estoque){
        Embalagem embalagem = new Embalagem();
        //TIPO
        System.out.println("Escolha um tipo de embalagem para adicionar:");
        System.out.println(TiposEmbalagens.imprimirTiposEmbalagens());
        embalagem.setTipo_embalagem(Escolhivel.escolheObjeto(input, TiposEmbalagens.values(),
                "Numero ou nome invalido. Escolha um numero de (1-14) ou digite um nome valido.", "obrigatorio"));
        //NOME
        embalagem.setNome(embalagem.getTipo_embalagem().getNome());
        //QUANTIDADE
        embalagem.setQuantidade(Integer.parseInt(ValidadorInput.getInput(input, "Quantas pacotes foram comprados? ", "Quantidade invalida", Verifica::isNatural)));
        //PRECO_PACOTE
        embalagem.setPreco_pacote(Float.parseFloat(ValidadorInput.getInput(input, "Qual o preco de 1 pacote? ", "Preco invalido, coloque um numero valido.", Verifica::isFloat)));
        //QUANTIDADE_POR_PACOTE
        embalagem.setQuantidade_por_pacote(Integer.parseInt(ValidadorInput.getInput(input, "Quantas unidades por pacote? ", "Quantidade invalida", Verifica::isNatural)));
        //PRECO
        embalagem.setPreco(embalagem.getPreco_pacote() / embalagem.getQuantidade_por_pacote());
        //FORNECEDOR
        Fornecedor fornecedor;
        switch (ValidadorInput.verificaOpcao(input, new String[]{"FORNECEDORES", "Mostrar lista de fornecedores já cadastrados.", "Adicionar novo fornecedor."}, 1)) {
            case 1:
                System.out.println(estoque.listaFornecedores());
                System.out.println("Seu Fornecedor não está na lista? Para adicionar um novo fornecedor digite 'novo'.");
                System.out.println("Insira o CNPJ ou nome do seu fornecedor");
                fornecedor = Escolhivel.escolheObjeto(input, estoque.getFornecedores(), "Fornecedor inexistente. Digite o CNPJ ou nome de algum fornecedor listado.", "novo");
                if (fornecedor == null) {
                    fornecedor = new Fornecedor().novoFornecedor(input);
                    estoque.addFornecedor(fornecedor);
                    embalagem.setCnpj_fornecedor(fornecedor.getCnpj());
                    break;
                }
                embalagem.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            case 2:
                fornecedor = new Fornecedor().novoFornecedor(input);
                estoque.addFornecedor(fornecedor);
                embalagem.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }
        return embalagem;
    }
}



