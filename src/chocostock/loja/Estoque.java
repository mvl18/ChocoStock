package chocostock.loja;

import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Iteravel;
import chocostock.itens.Equipamento;
import chocostock.itens.Item;
import chocostock.itens.materiais.Embalagem;
import chocostock.itens.materiais.Suprimento;
import chocostock.itens.produtos.Produto;
import chocostock.itens.materiais.Ingrediente;
import chocostock.enums.TiposIngredientes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Estoque implements AddRemovivel, Iteravel {
    private ArrayList<Item> produtos;
    private ArrayList<Item> ingredientes;
    private ArrayList<Item> equipamentos;
    private ArrayList<Embalagem> embalagens;

    public Estoque() {
        this.produtos = new ArrayList<Item>();
        this.ingredientes = new ArrayList<Item>();
        this.equipamentos = new ArrayList<Item>();
        this.embalagens = new ArrayList<Embalagem>();
    }

    public ArrayList<Item> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Item> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<Item> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Item> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ArrayList<Item> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(ArrayList<Item> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public boolean addEmbalagem(Embalagem embalagem) {
        return addObjeto(embalagens, embalagem);
    }

    public String listaEmbalagens() {
        return listaObjetos(embalagens);
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

    public boolean addIngrediente(Suprimento suprimento) {
        return addObjeto(ingredientes, suprimento);
    }

    public boolean removeMaterial(Suprimento suprimento) {
            return removeObjeto(ingredientes, suprimento);
    }

    public void imprimirTiposIngredientes(){
        for(TiposIngredientes tipo : TiposIngredientes.values()){
            System.out.println(tipo.getId() + " - " + tipo.getNome());
        }
    }

    public void imprimirTiposEmbalagens(){
        for(TiposEmbalagens tipo : TiposEmbalagens.values()){
            System.out.println(tipo.getId() + " - " + tipo.getNome() + " - " + tipo.getTamanho());
        }
    }

    public String statusIngredientes(){
        String msg = "";
        for(TiposIngredientes tipo : TiposIngredientes.values()){

            float quantidade = 0.0f;
            LocalDate validade = null;

            for(Item item : ingredientes){
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

    public String statusEmbalagens(){
        String msg = "";
        for(TiposEmbalagens tipo : TiposEmbalagens.values()){
            msg += tipo.toString() + "\n";
            int quantidadeEmEstoque = 0, numeroDePacotes = 0;
            for(Embalagem embalagem : embalagens){
                if(embalagem.getTipo_embalagem().equals(tipo)){
                    numeroDePacotes++;
                    quantidadeEmEstoque += embalagem.getQuantidade_em_estoque();
                }
            }
            msg += "\t-Pacotes: " + numeroDePacotes + "\n\t-Unidades em estoque: " + quantidadeEmEstoque + "\n";
        }
        return msg;
    }
}
