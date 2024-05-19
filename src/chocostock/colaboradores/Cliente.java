package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Iteravel;

import java.util.ArrayList;

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
        this.pedidos = new ArrayList<Integer>();
    }

    public Cliente() {
        this("", "", "", new Endereco());
    }

    public ArrayList<Integer> getPedidos() {
        return pedidos;
    }

    /**
     * Adiciona um ID de pedido à lista de pedidos do cliente.
     */
    public boolean addPedido(Integer idPedido) {
        return addObjeto(pedidos, idPedido);
    }

    /**
     * Retorna uma string com a lista de pedidos do cliente.
     */
    public String listaPedidos() {
        return listaVertical(pedidos);
    }

    public int getId() {
        return id;
    }

    public void setPedidos(ArrayList<Integer> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        String out = id + ". " + super.toString() + "Pedidos: ";
        out += listaHorizontalQuebraLinha(pedidos);
        return out;
    }
}
