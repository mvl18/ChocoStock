package chocostock.colaboladores;

import chocostock.Endereco;
import chocostock.Pedido;

import java.util.ArrayList;

public class Cliente extends Colaborador {
    private static int id_clientes = 100000;
    private final int id; // final, pois nao pode ser modificado apos criado
    private ArrayList<Integer> pedidos;
    public Cliente(String nome, String telefone, String email, Endereco endereco) {
        super(nome, telefone, email, endereco);
        this.id = id_clientes++;
        this.pedidos = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Integer> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() { // teste de como fazer um toString() decente
        return "Cliente{" + super.toString() +
                ", id=" + id +
                ", pedidos=" + pedidos +
                '}';
    }
}
