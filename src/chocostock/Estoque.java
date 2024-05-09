package chocostock;

import java.util.ArrayList;

public class Estoque {
    private ArrayList<Item> produtos;
    private ArrayList<Item> materiais;
    private ArrayList<Item> equipamentos;

    public Estoque(ArrayList<Item> produtos, ArrayList<Item> materiais, ArrayList<Item> equipamentos) {
        this.produtos = produtos;
        this.materiais = materiais;
        this.equipamentos = equipamentos;
    }

    public ArrayList<Item> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Item> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<Item> getMateriais() {
        return materiais;
    }

    public void setMateriais(ArrayList<Item> materiais) {
        this.materiais = materiais;
    }

    public ArrayList<Item> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(ArrayList<Item> equipamentos) {
        this.equipamentos = equipamentos;
    }
}
