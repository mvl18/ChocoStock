package chocostock.loja;

import chocostock.interfaces.AddRemovivel;
import chocostock.itens.Equipamento;
import chocostock.itens.Item;
import chocostock.itens.materiais.Material;
import chocostock.itens.produtos.Produto;
import chocostock.itens.materiais.Ingrediente;
import chocostock.enums.TiposIngredientes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Estoque implements AddRemovivel {
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

    public void imprimirIngredientes(){
        for(TiposIngredientes tipo : TiposIngredientes.values()){
            System.out.println(tipo.getId() + " - " + tipo.getNome());
        }
    }

    public String statusIngredientes(){
        String msg = "";
        for(TiposIngredientes tipo : TiposIngredientes.values()){

            float quantidade = 0.0f;
            LocalDate validade = null;

            for(Item item : materiais){
                if(item instanceof Ingrediente && ((Ingrediente) item).getTipo() == tipo){
                    quantidade += item.getQuantidade() * ((Ingrediente)item).getUnidade();
                    if(validade == null){
                        validade = ((Ingrediente) item).getValidade();
                    }
                    else{
                        validade = (validade.isBefore(((Ingrediente) item).getValidade())) ? validade : ((Ingrediente) item).getValidade();
                    }
                }
            }

            if(quantidade == 0){
                msg += tipo.getId() + " - " + tipo.getNome() + ":\n\t" +
                    "Quantidade(kg): " + quantidade + "\n";
            }
            else{
                msg += tipo.getId() + " - " + tipo.getNome() + ":\n\t" +
                    "Quantidade(kg): " + quantidade + " Validade: " + validade + "\n";
            }
        }
        return msg;
    }


}
