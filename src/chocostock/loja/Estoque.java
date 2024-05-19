package chocostock.loja;

import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Complementavel;
import chocostock.interfaces.Iteravel;
import chocostock.itens.Equipamento;
import chocostock.itens.Item;
import chocostock.itens.materiais.Embalagem;
import chocostock.itens.materiais.Suprimento;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.produtos.Produto;
import chocostock.itens.materiais.Ingrediente;
import chocostock.enums.TiposIngredientes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Estoque implements AddRemovivel, Iteravel {
    private ArrayList<Produto> produtos;
    private ArrayList<Item> materiais;
    private ArrayList<Item> equipamentos;
    private ArrayList<Embalagem> embalagens;

    public Estoque() {
        this.produtos = new ArrayList<Produto>();
        this.materiais = new ArrayList<Item>();
        this.equipamentos = new ArrayList<Item>();
        this.embalagens = new ArrayList<Embalagem>();
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
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

    public boolean addEmbalagem(Embalagem embalagem) {
        return addObjeto(embalagens, embalagem);
    }

    public String listaEmbalagens() {
        return listaHorizontal(embalagens);
    }

    public boolean addProduto(int posicao, Produto produto) {
        return addObjeto(posicao, produtos, produto);
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

    public boolean addMaterial(Suprimento suprimento) {
        return addObjeto(materiais, suprimento);
    }

    public boolean removeMaterial(Suprimento suprimento) {
            return removeObjeto(materiais, suprimento);
    }

    public void imprimirIngredientes(){
        for(TiposIngredientes tipo : TiposIngredientes.values()){
            System.out.println(tipo.getId() + " - " + tipo.getNome());
        }
    }

    public void imprimirProdutos(){
        for(Produto produto : produtos){
            System.out.println(produto.getId() + " - " + produto.getNome());
        }
    }

    @Override
    public String toString() {
        return "--- ESTOQUE ---" +
                "\nProdutos: " + listaHorizontal(produtos) +
                "\nMateriais: " + listaHorizontal(materiais) +
                "\nEquipamentos: " + listaHorizontal(equipamentos) +
                "\nEmbalagens: " + listaHorizontal(embalagens);
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


    public Pedido retiraProdutosEstoque(Pedido pedido) { // nao funciona, podem ter 2 ou mais lotes do msm produto e a soma da quantidade deles pode ser o suficiente

        ArrayList<Pendente> produtos_concluidos= new ArrayList<Pendente>();
        for (Pendente produto_pendente : pedido.getProdutos_pendentes()) {
            int quantidade = 0;
            ArrayList<Integer> posicoes = new ArrayList<Integer>();
            for (int i = 0; i < getProdutos().size(); i++) {
                if (getProdutos().get(i).getId_pedido() == -1) { //Se o produto ainda não tem dono (reservado)
                    if (produto_pendente.getNome().equals(getProdutos().get(i).getNome())) {
                        if (getProdutos().get(i) instanceof Complementavel) { // caixa n tem complemento ent nunca vai passar
                            if (produto_pendente.getComplementos().equals(((Complementavel) getProdutos().get(i)).getComplementos())) {
                                posicoes.add(i);
                                quantidade += getProdutos().get(i).getQuantidade();
                                if (quantidade >= produto_pendente.getQuantidade()) {
                                    for (int j = 0; j < posicoes.size(); j++) {
                                        boolean ultimo = j == posicoes.size() - 1;
                                        if (ultimo && quantidade != produto_pendente.getQuantidade()) {
                                            Produto produto_pedido = meioseProduto(getProdutos().get(posicoes.get(j)),
                                                                    quantidade - produto_pendente.getQuantidade(),  i+1);
                                            produto_pedido.setId_pedido(pedido.getId());
                                            pedido.addProduto(((Produto) getProdutos().get(i+1)).getId());
                                        } else {
                                            getProdutos().get(posicoes.get(j)).setId_pedido(pedido.getId());  //Pega tudo
                                            pedido.addProduto(((Produto) getProdutos().get(i)).getId());
                                        }
                                        produtos_concluidos.add(produto_pendente);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Pendente concluido : produtos_concluidos) {
            pedido.getProdutos_pendentes().remove(concluido);
        }

        return pedido;
    }

    private Produto meioseProduto(Produto produto, int quantidade, int posicao) {
        Produto produto_pedido = new Produto();
        produto_pedido.setNome(produto.getNome());
        produto_pedido.setEmbalagem(produto.getEmbalagem());
        produto_pedido.setPeso(produto.getPeso());
        produto_pedido.setValidade(produto.getValidade());

        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produto_pedido.setQuantidade(quantidade);
        addProduto(posicao, produto_pedido);
        return produto_pedido;
    }

}
