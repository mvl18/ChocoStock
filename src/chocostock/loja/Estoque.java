package chocostock.loja;

import chocostock.colaboradores.Fornecedor;
import chocostock.enums.*;
import chocostock.interfaces.*;
import chocostock.itens.Item;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.itens.produtos.Caixa;
import chocostock.itens.produtos.Chocolate;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.produtos.Produto;
import chocostock.itens.suprimentos.Equipamento;
import chocostock.itens.suprimentos.Ingrediente;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A classe Estoque gerencia os produtos, materiais, equipamentos
 * e embalagens disponíveis no estoque da loja. <br>
 * Implementa os métodos "addEmbalagem", "addProduto", "addMaterial" ...,
 * "novoFornecedor", "imprimirIngredientes", "imprimirProdutos",
 * "statusIngredientes", "retiraProdutoEstoque", "estocarCliente",
 * "estocarEmbalagem" e o método privado "meioseProduto".
 */
public class Estoque implements AddRemovivel, Escolhivel, Iteravel, Serializable {
    private ArrayList<Produto> produtos;
    private final ArrayList<Ingrediente> ingredientes;
    private final ArrayList<Item> equipamentos;
    private final ArrayList<Embalagem> embalagens;
    private final ArrayList<Fornecedor> fornecedores;


    public Estoque() {
        this.produtos = new ArrayList<>();
        this.ingredientes = new ArrayList<>();
        this.equipamentos = new ArrayList<>();
        this.embalagens = new ArrayList<>();
        this.fornecedores = new ArrayList<>();
    }

    public ArrayList<Produto> getProdutos() {
        return new ArrayList<>(this.produtos);
    }

    public ArrayList<Caixa> getCaixas() {
        ArrayList<Caixa> caixas = new ArrayList<>();
        for (Produto produto : getProdutos()) {
            if (produto instanceof Caixa)
                caixas.add((Caixa) produto);
        }
        return caixas;
    }

    public ArrayList<Chocolate> getChocolates() {
        ArrayList<Chocolate> chocolates = new ArrayList<>();
        for (Produto produto : getProdutos()) {
            if (produto instanceof Chocolate)
                chocolates.add((Chocolate) produto);
        }
        return chocolates;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }


    /**
     * Adiciona um ingrediente na lista de ingredientes do estoque.
     */
    public void addIngrediente(Ingrediente ingrediente) {
        addObjeto(ingredientes, ingrediente);
    }

    public ArrayList<Fornecedor> getFornecedores(){
        return fornecedores;
    }

    public void addFornecedor(Fornecedor fornecedor) {
        addObjeto(fornecedores, fornecedor);
    }

    public String listaFornecedores() {
        return listaHorizontalQuebraLinha(fornecedores);
    }

    public String[] getArrayFornecedores(){
        int num_fornecedores = getFornecedores().size();
        String[] arrFornecedores = new String[num_fornecedores];
        for(int i = 0; i < num_fornecedores; i++){
            arrFornecedores[i] = getFornecedores().get(i).getNome();
        }
        return arrFornecedores;
    }

    /**
     * Adiciona uma embalagem na lista de embalagens do estoque.
     */
    public void addEmbalagem(Embalagem embalagem) {
        addObjeto(embalagens, embalagem);
    }

    /**
     * Adiciona um produto na lista de produtos do estoque.
     */
    public void addProduto(int posicao, Produto produto) {
        addObjeto(posicao, produtos, produto);
    }

    public void addProduto(Produto produto) {
        addObjeto(produtos, produto);
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
    public void imprimirProdutos() {
        for (Produto produto : produtos) {
            System.out.print(produto.getId() + " - " + produto.getNome());
            if (produto instanceof Complementavel) {
                ArrayList<?> complementos = ((Complementavel) produto).getComplementos();
                String complementosStr = complementos != null ? Iteravel.listaHorizontal(complementos) : "sem complementos";
                System.out.println(" com " + complementosStr + " (" + produto.getQuantidade() + (produto.getQuantidade() > 1 ? " unidades)" : " unidade)") + (produto.getId_pedido() != -1 ? " Tem dono" : ""));
            } else {
                System.out.println(" (" + produto.getQuantidade() + (produto.getQuantidade() > 1 ? " unidades)" : " unidade)"));
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "--- ESTOQUE ---" +
                "\nProdutos: " + listaHorizontalQuebraLinha(produtos) +
                "\nIngredientes: " + listaHorizontalQuebraLinha(ingredientes) +
                "\nEquipamentos: " + listaHorizontalQuebraLinha(equipamentos) +
                "\nEmbalagens: " + listaHorizontalQuebraLinha(embalagens);
    }

    /**
     * Retorna uma mensagem com o status dos ingredientes disponíveis no estoque,
     * incluindo a quantidade e a validade de cada tipo de ingrediente.
     */
    public String statusIngredientes(){
        StringBuilder msg = new StringBuilder();

        // Itera sobre os tipos de ingredientes definidos no enum TiposIngredientes
        for(TiposIngredientes tipo : TiposIngredientes.values()){
            // Inicializa a quantidade total e a validade do ingrediente como nulo
            float quantidade = 0.0f;
            LocalDate validade = null;

            // Itera sobre os materiais no estoque
            for(Ingrediente ingrediente : ingredientes){
                // Verifica se o item é um ingrediente do tipo atual
                if(ingrediente.getTipo() == tipo){
                    // Atualiza a quantidade total do ingrediente
                    quantidade += ingrediente.getQuantidade() * ingrediente.getUnidade();
                    // Atualiza a validade do ingrediente, considerando a data de validade mais próxima
                    if(validade == null){
                        validade = ingrediente.getValidade();
                    }
                    else{
                        validade = (validade.isBefore((ingrediente.getValidade())) ? validade : ingrediente.getValidade());
                    }
                }
            }

            // Adiciona as informações do ingrediente à mensagem
            if(quantidade == 0){
                msg.append(tipo.getId()).append(" - ").append(tipo.getNome()).append(":\n\t").append("Quantidade(kg): ").append(quantidade).append("\n");
            }
            else{
                msg.append(tipo.getId()).append(" - ").append(tipo.getNome()).append(":\n\t").append("Quantidade(kg): ").append(quantidade).append(" Validade: ").append(validade).append("\n");
            }
        }
        return msg.toString();
    }

    /**
     * Retorna uma mensagem com o status das embalagens disponíveis no estoque,
     * incluindo a quantidade de cada tipo de embalagem.
     */
    public String statusEmbalagens(){
        StringBuilder msg = new StringBuilder();
        //Itera sobre os tipos de embalagens
        for(TiposEmbalagens tipo : TiposEmbalagens.values()){
            //Contador de unidades para o tipo atual
            int unidades = 0;
            //Itera sobre as embalagens no estoque
            for(Embalagem embalagem : embalagens){
                //Se a embalagem for do tipo atual
                if(embalagem.getTipo_embalagem() == tipo){
                    unidades += embalagem.getQuantidade() * embalagem.getQuantidade_por_pacote();
                }
            }
            //Adicionar as informações na string msg
            msg.append(tipo.toString()).append("\n\tUnidades: ").append(unidades).append("\n");
        }
        return msg.toString();
    }

    /**
     * Processa a retirada de produtos do estoque para atender a um pedido.
     */
    public Pedido retiraProdutosEstoque(Pedido pedido) {
        ArrayList<Pendente> produtos_concluidos= new ArrayList<>();

        // Itera sobre os produtos pendentes do pedido
        for (Pendente produto_pendente : pedido.getProdutos_pendentes()) {
            int quantidade = 0;  // Armazena a quantidade acumulada encontrada no estoque
            ArrayList<Integer> posicoes = new ArrayList<>();  // Armazena as posições dos produtos correspondentes no estoque

            // Itera sobre os produtos disponíveis no estoque
            for (int i = 0; i < getProdutos().size(); i++) {
                // Verifica se o produto ainda não foi reservado para um pedido
                if (getProdutos().get(i).getId_pedido() == -1) {
                    // Verifica se o nome do produto no estoque corresponde ao nome do produto pendente
                    if (produto_pendente.getNome().equals(getProdutos().get(i).getNome())) {
                        // Verifica se o produto é complementável (Chocolate)
                        if (getProdutos().get(i) instanceof Complementavel) {
                            // Verifica se os complementos do produto no estoque correspondem aos complementos do produto pendente
                            if ((produto_pendente.getComplementos().isEmpty() && ((Complementavel) getProdutos().get(i)).getComplementos() == null) ||
                                (produto_pendente.getComplementos().containsAll(((Complementavel) getProdutos().get(i)).getComplementos()) &&
                                ((Complementavel) getProdutos().get(i)).getComplementos().containsAll(produto_pendente.getComplementos()))) {
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
                                            pedido.addProduto(getProdutos().get(i+1).getId());
                                        } else {
                                            // Reserva o produto inteiro para o pedido
                                            getProdutos().get(posicoes.get(j)).setId_pedido(pedido.getId());  //Pega tudo
                                            pedido.addProduto(getProdutos().get(posicoes.get(j)).getId());
                                        }
                                    }
                                    // Adiciona o produto pendente à lista de produtos concluídos
                                    produtos_concluidos.add(produto_pendente);
                                    break;
                                }
                            }
                        }
                        else { // Produto não é complementavel (Caixa)
                            // Verifica se o nome do produto pendente contém "Caixa"
                            if (produto_pendente.getNome().contains("Caixa")) {
                                posicoes.add(i);
                                quantidade += getProdutos().get(i).getQuantidade();
                                // Passos semelhantes aos do Chocolate
                                if (quantidade >= produto_pendente.getQuantidade()) {
                                    for (int j = 0; j < posicoes.size(); j++) {
                                        boolean ultimo = j == posicoes.size() - 1;
                                        if (ultimo && quantidade != produto_pendente.getQuantidade()) {
                                            Caixa produto_pedido = (Caixa) meioseProduto(getProdutos().get(posicoes.get(j)),
                                                    getProdutos().get(posicoes.get(j)).getQuantidade() + produto_pendente.getQuantidade() - quantidade,  i+1); // quantidade - produto_pendente.getQuantidade() que sobra
                                            produto_pedido.setId_pedido(pedido.getId());
                                            pedido.addProduto(getProdutos().get(i+1).getId());
                                        } else {
                                            getProdutos().get(posicoes.get(j)).setId_pedido(pedido.getId());
                                            pedido.addProduto(getProdutos().get(i).getId());
                                        }
                                    }
                                    produtos_concluidos.add(produto_pendente);
                                    break;
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
     * Processa o cancelamento de um pedido e devolve os produtos ao estoque.
     */
    public void devolveProdutosEstoque(Pedido pedido_cancelado) {
        // Itera sobre os produtos do pedido cancelado
        for (int idProduto_cancelado : pedido_cancelado.getProdutos()) {
            // Itera sobre os produtos do estoque pedido
            Produto produto_cancelado = getObjetoPorId(idProduto_cancelado, produtos);
            produto_cancelado.setId_pedido(-1);

            // Itera sobre os produtos do estoque para encontrar produtos semelhantes
            for (Produto produto_estoque : produtos) {
                // Checa se são do mesmo tipo
                if (produto_estoque.getClass().equals(produto_cancelado.getClass()) && produto_estoque.getId() != produto_cancelado.getId()) {
                    boolean iguais = false;

                    if (produto_cancelado instanceof Chocolate chocolate_cancelado) {
                        Chocolate chocolate_estoque = (Chocolate) produto_estoque;
                        iguais = chocolate_cancelado.getNome().equals(chocolate_estoque.getNome()) &&
                                chocolate_cancelado.getPreco() == chocolate_estoque.getPreco() &&
                                chocolate_cancelado.getEmbalagem().equals(chocolate_estoque.getEmbalagem()) &&
                                chocolate_cancelado.getPeso() == chocolate_estoque.getPeso() &&
                                chocolate_cancelado.getValidade().equals(chocolate_estoque.getValidade()) &&
                                chocolate_cancelado.getTipo().equals(chocolate_estoque.getTipo()) &&
                                chocolate_cancelado.getOrigem_cacau().equals(chocolate_estoque.getOrigem_cacau()) &&
                                chocolate_cancelado.getLote() == chocolate_estoque.getLote();
                    }

                    else if (produto_cancelado instanceof Caixa caixa_cancelada) {
                        Caixa caixa_estoque = (Caixa) produto_estoque;
                        iguais = caixa_cancelada.getNome().equals(caixa_estoque.getNome()) &&
                                caixa_cancelada.getPreco() == caixa_estoque.getPreco() &&
                                caixa_cancelada.getEmbalagem().equals(caixa_estoque.getEmbalagem()) &&
                                caixa_cancelada.getPeso() == caixa_estoque.getPeso() &&
                                caixa_cancelada.getValidade().equals(caixa_estoque.getValidade()) &&
                                caixa_cancelada.getTipo().equals(caixa_estoque.getTipo()) &&
                                caixa_cancelada.getLote() == caixa_estoque.getLote();
                    }

                    // Se os produtos são iguais, soma as quantidades e remove o duplicado
                    if (iguais) {
                        produto_estoque.setQuantidade(produto_estoque.getQuantidade() + produto_cancelado.getQuantidade());
                        produtos.remove(produto_cancelado);
                        break;
                    }
                }
            }
        }
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
            produto_pedido = duplicaChocolate(produto, quantidade);

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
    public ArrayList<Embalagem> getEmbalagens() {
        return embalagens;
    }

    private static Chocolate duplicaChocolate(Produto produto, int quantidade) {
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
        return chocolate_pedido;
    }

    public Produto estocarProduto(Scanner scanner){
        System.out.println("Escolha um tipo de produto para adicionar:");
        return switch (ValidadorInput.verificaOpcao(scanner, new String[]{"TIPOS DE PRODUTO", "Barra.", "Caixa."}, 1)) {
            case 1 -> new Chocolate().selecionaBarra(scanner);
            case 2 -> new Caixa().selecionaCaixa(scanner);
            default -> null;
        };
    }

    public void addEquipamento(Equipamento equipamento) {
        addObjeto(equipamentos, equipamento);
    }
}
