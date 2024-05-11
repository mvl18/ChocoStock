package chocostock;

import chocostock.itens.Equipamento;
import chocostock.itens.Material;
import chocostock.itens.Produto;

import java.util.ArrayList;

public class Estoque implements AddRemove {
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
        return addObjeto(produtos, produto);
    }

    public boolean removeProduto(Produto produto) {
        return removeObjeto(produtos, produto);
    }

    public boolean addEquipamento(Equipamento equipamento) {
        return addObjeto(equipamentos, equipamento);
    }

    public boolean removeEquipamento(Equipamento equipamento) {
        return removeObjeto(equipamentos, equipamento);
    }

    public boolean addMaterial(Material material) {
        return addObjeto(materiais, material);
    }

    public boolean removeMaterial(Material material) {
            return removeObjeto(materiais, material);
        }
}
