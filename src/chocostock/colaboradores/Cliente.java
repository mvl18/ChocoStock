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
    private final ArrayList<Integer> pedidos;

    public Cliente(String nome, String telefone, String email, Endereco endereco) {
        super(nome, telefone, email, endereco);
        this.id = id_clientes++;
        this.pedidos = new ArrayList<>();
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

    @Override
    public String toString() {
        String out = id + ". " + super.toString() + "Pedidos: ";
        out += listaHorizontalQuebraLinha(pedidos);
        return out;
    }
}
