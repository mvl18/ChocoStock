package chocostock.colaboradores;

import chocostock.auxiliar.Endereco;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Identificavel;
import chocostock.interfaces.Iteravel;

import java.util.ArrayList;

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

    public boolean addPedido(Integer idPedido) {
        return addObjeto(pedidos, idPedido);
    }

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
        out += listaHorizontal(pedidos);
        return out;
    }
}
