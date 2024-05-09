package chocostock;

import chocostock.itens.Equipamento;
import chocostock.itens.Material;
import chocostock.itens.Produto;
import chocostock.itens.produtos.Chocolate;

import javax.print.attribute.standard.OrientationRequested;
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

    public boolean addProduto(Produto produto) {
        if (!this.produtos.contains(produto)) {
            this.getProdutos().add(produto);
            return true;
        }

        return false;

    }

    public boolean removeProduto(Produto produto) {
        if (this.produtos.contains(produto)) {
            this.getProdutos().remove(produto);
            return true;
        }

        return false;
    }

    public boolean addEquipamento(Equipamento equipamento) {
        if (!this.equipamentos.contains(equipamento)) {
            this.getEquipamentos().add(equipamento);
            return true;
        }

        return false;

    }

    public boolean removeEquipamento(Equipamento equipamento) {
        if (this.equipamentos.contains(equipamento)) {
            this.getEquipamentos().remove(equipamento);
            return true;
        }

        return false;
    }

    public boolean addMaterial(Material material) {
        if (!this.materiais.contains(material)) {
            this.getMateriais().add(material);
            return true;
        }

        return false;

    }

    public boolean removeMaterial(Equipamento material) {
        if (this.materiais.contains(material)) {
            this.getMateriais().remove(material);
            return true;
        }

        return false;
    }
}
