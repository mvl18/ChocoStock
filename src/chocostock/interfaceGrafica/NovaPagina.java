package chocostock.interfaceGrafica;

import chocostock.enums.*;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.loja.Loja;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Fornece métodos para criação da maioria dos tipos de página do sistema.
 */
public class NovaPagina {
    public static Loja loja;
    public static FormularioDeCadastro generico(String titulo, String tag, String[] atributos) {
        FormularioDeCadastro f = new FormularioDeCadastro(tag, loja);
        f.addTitulo(titulo);
        for(String atributo : atributos){
            switch (atributo) {
                case "Estado" -> f.addInputComponent(new JComboBox<>(Estados.getTipos()), atributo);
                case "Cargo" -> f.addInputComponent(new JComboBox<>(Cargos.getTipos()), atributo);
                case "Fornecedor" -> f.addInputComponent(new JComboBox<>(loja.getEstoque().getArrayFornecedores()), atributo);
                case "Tipo embalagem" -> f.addInputComponent(new JComboBox<>(TiposEmbalagens.getTipos()), atributo);
                case "Tipo ingrediente" -> f.addInputComponent(new JComboBox<>(TiposIngredientes.getTipos()), atributo);
                case "Status" -> f.addInputComponent(new JComboBox<>(Status.getTipos()), atributo);
                case "Pago" -> f.addInputComponent(new JComboBox<>(new String[]{"Não", "Sim"}), atributo);
                case "Cliente" -> f.addInputComponent(new JComboBox<>(loja.arrayClientes()), atributo);
                case "Produtos" -> f.addInputComponent(PainelProduto.novoEscolhaProdutos(), atributo);
                case "Data de fabricação" -> f.addInputComponent(new Placeholder("DD/MM/YYYY"), atributo);
                case "Data do pedido" -> f.addInputComponent(new Placeholder("DD/MM/YYYY"), atributo);
                case "Data de entrega" -> f.addInputComponent(new Placeholder("DD/MM/YYYY"), atributo);
                case "Data de validade" -> f.addInputComponent(new Placeholder("DD/MM/YYYY"), atributo);
                case "Data de compra" -> f.addInputComponent(new Placeholder("DD/MM/YYYY"), atributo);
                case "Nome produto" -> f.addInputComponent(new JComboBox<>(Stream.concat(Arrays.stream(TiposChocolates.getTipos()),
                        Arrays.stream(TiposCaixas.getTipos())
                        ).toArray()), atributo);
                default -> f.addInputComponent(new JTextField(), atributo);
            }
        }
        f.addBotoes();
        f.atualizarLayout();
        return f;
    }

    public static FormularioDeCadastro novoPedido(Loja loja) {
        NovaPagina.loja = loja;
        return generico("Novo Pedido", "Pedido",
                new String[]{"Cliente", "Data do pedido",
                "Data de entrega", "Pago", "Status", "Produtos", "Valor total"});
    }

    public static FormularioDeCadastro novoCliente(Loja loja) {
        NovaPagina.loja = loja;
        return generico("Novo Cliente", "Cliente",
                new String[]{"Nome", "Telefone", "Email",
                "CEP", "Estado", "Cidade", "Bairro", "Rua", "Número"});
    }

    public static FormularioDeCadastro novoFornecedor(Loja loja) {
        NovaPagina.loja = loja;
        return generico("Novo Fornecedor", "Fornecedor",
                new String[]{"Nome", "Telefone", "Email",
                "CEP", "Estado", "Cidade", "Bairro", "Rua", "Número", "CNPJ", "Site"});
    }

    public static FormularioDeCadastro novoFuncionario(Loja loja) {
        NovaPagina.loja = loja;
        return generico("Novo Funcionário", "Funcionario",
                new String[]{"Nome", "Telefone", "Email",
                "CEP", "Estado", "Cidade", "Bairro", "Rua", "Número", "Cargo", "Salario"});
    }

    public static FormularioDeCadastro novoProduto(Loja loja) {
        NovaPagina.loja = loja;
        return generico("Novo Produto", "Produto",
                new String[]{"Nome produto", "Quantidade", "Preço",
                        "Data de validade", "Peso", "Tipo embalagem", "Lote"});
    }

    public static FormularioDeCadastro novaEmbalagem(Loja loja) {
        NovaPagina.loja = loja;
        return generico("Nova Embalagem", "Embalagem",
                new String[]{"Tipo embalagem", "Quantidade",
                "Preço por pacote", "Quantidade por pacote", "Fornecedor"});
    }

    public static FormularioDeCadastro novoIngrediente(Loja loja) {
        NovaPagina.loja = loja;
        return generico("Novo Ingrediente", "Ingrediente",
                new String[]{"Tipo ingrediente", "Quantidade", "Quantos kg por Unidade",
                "Preço", "Data de compra", "Data de validade", "Fornecedor"});
    }
}
