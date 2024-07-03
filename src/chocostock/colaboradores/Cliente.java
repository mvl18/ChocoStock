package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.auxiliar.Verifica;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Iteravel;
import chocostock.interfaces.ValidadorInput;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A classe Cliente representa um cliente que herda de Colaborador.
 * Esta classe gerencia informações específicas do cliente,
 * como um identificador único e uma lista de pedidos. <br>
 * Implementa os métodos "addPedido" e "listaPedidos"
 */
public class Cliente extends Colaborador implements AddRemovivel, Identificavel, Iteravel {
    private static int id_clientes = 100000;
    private final int id; // final, pois nao pode ser modificado apos criado
    private ArrayList<Integer> pedidos;

    public Cliente(String nome, String telefone, String email, Endereco endereco) {
        super(nome, telefone, email, endereco);
        this.id = id_clientes++;
        this.pedidos = new ArrayList<>();
    }

    public static int getId_clientes() {
        return id_clientes;
    }

    public ArrayList<Integer> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Integer> pedidos) {
        this.pedidos = pedidos;
    }

    public Cliente() {
        this("", "", "", new Endereco());
    }

    /**
     * Adiciona um ID de pedido à lista de pedidos do cliente.
     */
    public void addPedido(Integer idPedido) {
        addObjeto(pedidos, idPedido);
    }

    /**
     * Retorna uma string com a lista de pedidos do cliente.
     */

    public int getId() {
        return id;
    }

    public static int getIdCliente() {
        return id_clientes;
    }

    public static void setIdCliente(int id) {
        id_clientes = id;
    }

    @Override
    public String toString() {
        String out = id + ". " + super.toString() + "Pedidos: ";
        out += listaHorizontalQuebraLinha(pedidos);
        return out;
    }

    /**
     * Permite ao usuário cadastrar um novo cliente.
     */
    public static Cliente novoCliente(Scanner scanner) {
        Cliente cliente = new Cliente();
        System.out.println("Cadastrando novo cliente: ");
        // Solicitação do nome do cliente
        cliente.setNome(ValidadorInput.getInput(scanner, "Nome do cliente: ", "Nome inválido.", Verifica::isNome));
        // Solicitação do telefone do cliente
        cliente.setTelefone(ValidadorInput.getInput(scanner, "Telefone do cliente: ", "Insira um número válido, não esqueça o DDD!",
                Verifica::isTelefone).replaceAll("\\D", ""));
        // Solicitação do email do cliente
        cliente.setEmail(ValidadorInput.getInput(scanner, "Email do cliente: ", "Insira um email válido!", Verifica::isEmail));
        // Solicitação do endereço do cliente
        System.out.println("Criando endereço: ");
        Endereco endereco = new Endereco();
        cliente.setEndereco(endereco.criaEndereco(scanner));
        System.out.println("Novo cliente adicionado: " + cliente);

        return cliente;
    }
}
