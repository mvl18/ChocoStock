package chocostock.loja;

import chocostock.interfaces.AddRemovivel;
import chocostock.interfaces.Complementavel;
import chocostock.interfaces.Iteravel;
import chocostock.itens.suprimentos.Equipamento;
import chocostock.itens.Item;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.itens.suprimentos.Suprimento;
import chocostock.itens.produtos.Caixa;
import chocostock.itens.produtos.Chocolate;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.produtos.Produto;
import chocostock.itens.suprimentos.Ingrediente;
import chocostock.enums.TiposIngredientes;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A classe Estoque gerencia os produtos, materiais, equipamentos
 * e embalagens disponíveis no estoque da loja. <br>
 * Implementa os métodos "addEmbalagem", "addProduto", "addMaterial" ...,
 * "imprimirIngredientes", "imprimirProdutos", "statusIngredientes",
 * "retiraProdutoEstoque" e o método privado "meioseProduto".
 */
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

    /**
     * Adiciona uma embalagem ao estoque.
     */
    public boolean addEmbalagem(Embalagem embalagem) {
        return addObjeto(embalagens, embalagem);
    }

    /**
     * Retorna uma string com a lista de embalagens do estoque.
     */
    public String listaEmbalagens() {
        return listaHorizontalQuebraLinha(embalagens);
    }

    /**
     * Adiciona um produto na lista de produtos do estoque.
     */
    public boolean addProduto(int posicao, Produto produto) {
        return addObjeto(posicao, produtos, produto);
    }

    public boolean addProduto(Produto produto) {
        return addObjeto(produtos, produto);
    }

    /**
     * Remove um produto da lista de produtos.
     */
    public boolean removeProduto(Produto produto) {
        return removeObjeto(produtos, produto);
    }

    /**
     * Adiciona um equipamento à lista de equipamentos.
     */
    public boolean addEquipamento(Equipamento equipamento) {
        return addObjeto(equipamentos, equipamento);
    }

    /**
     * Remove um equipamento da lista de equipamentos.
     */
    public boolean removeEquipamento(Equipamento equipamento) {
        return removeObjeto(equipamentos, equipamento);
    }

    /**
     * Adiciona um material à lista de materiais.
     */
    public boolean addMaterial(Suprimento suprimento) {
        return addObjeto(materiais, suprimento);
    }

    /**
     * Remove um material da lista de materiais.
     */
    public boolean removeMaterial(Suprimento suprimento) {
            return removeObjeto(materiais, suprimento);
    }

    /**
     * Imprime na saída padrão os tipos de ingredientes disponíveis no estoque.
     */
    public void imprimirIngredientes(){
        for(TiposIngredientes tipo : TiposIngredientes.values()){
            System.out.println(tipo.getId() + " - " + tipo.getNome());
        }
    }

    /**
     * Imprime na saída padrão os produtos disponíveis no estoque.
     */
    public void imprimirProdutos(){
        for(Produto produto : produtos){
            System.out.println(produto.getId() + " - " + produto.getNome());
        }
    }

    @Override
    public String toString() {
        return "--- ESTOQUE ---" +
                "\nProdutos: " + listaHorizontalQuebraLinha(produtos) +
                "\nMateriais: " + listaHorizontalQuebraLinha(materiais) +
                "\nEquipamentos: " + listaHorizontalQuebraLinha(equipamentos) +
                "\nEmbalagens: " + listaHorizontalQuebraLinha(embalagens);
    }

    /**
     * Retorna uma mensagem com o status dos ingredientes disponíveis no estoque,
     * incluindo a quantidade e a validade de cada tipo de ingrediente.
     */
    public String statusIngredientes(){
        String msg = "";

        // Itera sobre os tipos de ingredientes definidos no enum TiposIngredientes
        for(TiposIngredientes tipo : TiposIngredientes.values()){
            // Inicializa a quantidade total e a validade do ingrediente como nulo
            float quantidade = 0.0f;
            LocalDate validade = null;

            // Itera sobre os materiais no estoque
            for(Item item : materiais){
                // Verifica se o item é um ingrediente do tipo atual
                if(item instanceof Ingrediente && ((Ingrediente) item).getTipo() == tipo){
                    // Atualiza a quantidade total do ingrediente
                    quantidade += item.getQuantidade() * ((Ingrediente)item).getUnidade();
                    // Atualiza a validade do ingrediente, considerando a data de validade mais próxima
                    if(validade == null){
                        validade = ((Ingrediente) item).getValidade();
                    }
                    else{
                        validade = (validade.isBefore(((Ingrediente) item).getValidade())) ? validade : ((Ingrediente) item).getValidade();
                    }
                }
            }

            // Adiciona as informações do ingrediente à mensagem
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

    /**
     * Processa a retirada de produtos do estoque para atender a um pedido.
     */
    public Pedido retiraProdutosEstoque(Pedido pedido) {
        ArrayList<Pendente> produtos_concluidos= new ArrayList<Pendente>();

        // Itera sobre os produtos pendentes do pedido
        for (Pendente produto_pendente : pedido.getProdutos_pendentes()) {
            int quantidade = 0;  // Armazena a quantidade acumulada encontrada no estoque
            ArrayList<Integer> posicoes = new ArrayList<Integer>();  // Armazena as posições dos produtos correspondentes no estoque

            // Itera sobre os produtos disponíveis no estoque
            for (int i = 0; i < getProdutos().size(); i++) {
                // Verifica se o produto ainda não foi reservado para um pedido
                if (getProdutos().get(i).getId_pedido() == -1) {
                    // Verifica se o nome do produto no estoque corresponde ao nome do produto pendente
                    if (produto_pendente.getNome().equals(getProdutos().get(i).getNome())) {
                        // Verifica se o produto é complementável (Chocolate)
                        if (getProdutos().get(i) instanceof Complementavel) {
                            // Verifica se os complementos do produto no estoque correspondem aos complementos do produto pendente
                            if (produto_pendente.getComplementos().equals(((Complementavel) getProdutos().get(i)).getComplementos())) {
                                posicoes.add(i);
                                quantidade += getProdutos().get(i).getQuantidade();
                                // Verifica se a quantidade acumulada é suficiente para atender ao produto pendente
                                if (quantidade >= produto_pendente.getQuantidade()) {
                                    // Itera sobre as posições dos produtos encontrados no estoque
                                    for (int j = 0; j < posicoes.size(); j++) {
                                        boolean ultimo = j == posicoes.size() - 1;
                                        if (ultimo && quantidade != produto_pendente.getQuantidade()) {
                                            // Se é o último produto e a quantidade acumulada é diferente da quantidade pendente, divide o produto
                                            Chocolate produto_pedido = (Chocolate) meioseProduto(getProdutos().get(posicoes.get(j)),
                                                            getProdutos().get(posicoes.get(j)).getQuantidade() + produto_pendente.getQuantidade() - quantidade,  i+1);
                                            produto_pedido.setId_pedido(pedido.getId()); // Reserva o produto dividido para o pedido
                                            pedido.addProduto(((Produto) getProdutos().get(i+1)).getId());
                                        } else {
                                            // Reserva o produto inteiro para o pedido
                                            getProdutos().get(posicoes.get(j)).setId_pedido(pedido.getId());  //Pega tudo
                                            pedido.addProduto(((Produto) getProdutos().get(i)).getId());
                                        }
                                        // Adiciona o produto pendente à lista de produtos concluídos
                                        produtos_concluidos.add(produto_pendente);
                                    }
                                }
                            }
                        } else { // Produto não é complementavel (Caixa)
                            // Verifica se o nome do produto pendente contém "Caixa"
                            if (produto_pendente.getNome().contains("Caixa")) {
                                posicoes.add(i);
                                quantidade += getProdutos().get(i).getQuantidade();
                                // Passos semlehantes aos do Chocolate
                                if (quantidade >= produto_pendente.getQuantidade()) {
                                    for (int j = 0; j < posicoes.size(); j++) {
                                        boolean ultimo = j == posicoes.size() - 1;
                                        if (ultimo && quantidade != produto_pendente.getQuantidade()) {
                                            Caixa produto_pedido = (Caixa) meioseProduto(getProdutos().get(posicoes.get(j)),
                                                    getProdutos().get(posicoes.get(j)).getQuantidade() + produto_pendente.getQuantidade() - quantidade,  i+1); // quantidade - produto_pendente.getQuantidade() que sobra
                                            produto_pedido.setId_pedido(pedido.getId());
                                            pedido.addProduto(((Produto) getProdutos().get(i+1)).getId());
                                        } else {
                                            getProdutos().get(posicoes.get(j)).setId_pedido(pedido.getId());
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
        // Remove os produtos pendentes que foram concluídos da lista de produtos pendentes do pedido
        for (Pendente concluido : produtos_concluidos) {
            pedido.getProdutos_pendentes().remove(concluido);
        }
        // Retorna o pedido atualizado
        return pedido;
    }

    /**
     * Cria uma nova instância de um produto a partir de um produto existente,
     * ajustando a quantidade do produto original e adicionando o novo produto
     * (que fará parte do pedido) à lista de produtos em uma posição à direita
     * do anterior.
     */
    private Produto meioseProduto(Produto produto, int quantidade, int posicao) {
        Produto produto_pedido;

        // CHOCOLATE
        if (produto.getClass().equals(Chocolate.class)) {
            Chocolate chocolate_pedido = new Chocolate();
            chocolate_pedido.setTipo(((Chocolate) produto).getTipo());
            chocolate_pedido.setNome(produto.getNome());
            chocolate_pedido.setPreco(produto.getPreco());
            chocolate_pedido.setEmbalagem(produto.getEmbalagem());
            chocolate_pedido.setPeso(produto.getPeso());
            chocolate_pedido.setValidade(produto.getValidade());
            chocolate_pedido.setQuantidade(quantidade);
            chocolate_pedido.setLote(((Chocolate) produto).getLote());
            chocolate_pedido.setOrigem_cacau(((Chocolate) produto).getOrigem_cacau());
            produto_pedido = chocolate_pedido;

        // CAIXA
        } else if (produto.getClass().equals(Caixa.class)) {
            Caixa caixa_pedido = new Caixa();
            caixa_pedido.setTipo(((Caixa) produto).getTipo());
            caixa_pedido.setNome(produto.getNome());
            caixa_pedido.setPreco(produto.getPreco());
            caixa_pedido.setEmbalagem(produto.getEmbalagem());
            caixa_pedido.setPeso(produto.getPeso());
            caixa_pedido.setValidade(produto.getValidade());
            caixa_pedido.setQuantidade(quantidade);
            produto_pedido = caixa_pedido;

        // Caso contrário, trata como Produto genérico (nao usado em tese)
        } else {
            produto_pedido = new Produto();
            produto_pedido.setNome(produto.getNome());
            produto_pedido.setEmbalagem(produto.getEmbalagem());
            produto_pedido.setPeso(produto.getPeso());
            produto_pedido.setValidade(produto.getValidade());
            produto_pedido.setQuantidade(quantidade);
        }

        // Ajusta a quantidade do produto original
        produto.setQuantidade(produto.getQuantidade() - quantidade);

        // Adiciona o produto pedido à lista de produtos na posição especificada
        addProduto(posicao, produto_pedido);

        return produto_pedido;
    }

}
