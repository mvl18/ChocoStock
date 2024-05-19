package chocostock.loja;

import chocostock.auxiliar.Processa;
import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.TiposEmbalagens;
import chocostock.interfaces.*;
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
import java.util.Scanner;

/**
 * A classe Estoque gerencia os produtos, materiais, equipamentos
 * e embalagens disponíveis no estoque da loja. <br>
 * Implementa os métodos "addEmbalagem", "addProduto", "addMaterial",
 * "imprimirIngredientes", "imprimirProdutos", "statusIngredientes",
 * "retiraProdutoEstoque" e o método privado "meioseProduto".
 */
public class Estoque implements AddRemovivel, Criavel, Escolhivel, Iteravel{
    private ArrayList<Produto> produtos;
    private ArrayList<Item> ingredientes;
    private ArrayList<Item> equipamentos;
    private ArrayList<Embalagem> embalagens;
    private ArrayList<Fornecedor> fornecedores;


    public Estoque() {
        this.produtos = new ArrayList<Produto>();
        this.ingredientes = new ArrayList<Item>();
        this.equipamentos = new ArrayList<Item>();
        this.embalagens = new ArrayList<Embalagem>();
        this.fornecedores = new ArrayList<Fornecedor>();
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<Item> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(ArrayList<Item> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public ArrayList<Fornecedor> getFornecedores(){
        return fornecedores;
    }

    public boolean addFornecedor(Fornecedor fornecedor) {
        return addObjeto(fornecedores, fornecedor);
    }

    public String listaFornecedores() {
        return listaHorizontalQuebraLinha(fornecedores);
    }

    public Fornecedor novoFornecedor(Scanner scanner) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(getInput(scanner, "Nome do fornecedor: ", "Nome invalido. Insira novamente.", Verifica::isNome));
        fornecedor.setTelefone(getInput(scanner, "Telefone do fornecedor: ","Telefone inválido. Insira novamente.", Verifica::isTelefone));
        fornecedor.setEmail(getInput(scanner, "Email do fornecedor:", "Email inválido. Insira novamente.", Verifica::isEmail));
        fornecedor.setEndereco(criaEndereco(scanner));
        fornecedor.setCnpj(Processa.normalizaNumero(getInput(scanner, "CNPJ do fornecedor:", "CNPJ inválido. Insira novamente.", Verifica::isCnpj)));
        fornecedor.setSite(getInput(scanner, "Site do fornecedor:", "Site inválido. Insira novamente.", Verifica::isSite));
        return fornecedor;
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
    public boolean addIngrediente(Suprimento suprimento) {
        return addObjeto(ingredientes, suprimento);
    }

    /**
     * Remove um material da lista de materiais.
     */
    public boolean removeIngrediente(Suprimento suprimento) {
            return removeObjeto(ingredientes, suprimento);
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
                "\nMateriais: " + listaHorizontalQuebraLinha(ingredientes) +
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
            for(Item item : ingredientes){
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


    public Pedido retiraProdutosEstoque(Pedido pedido) {

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
                                            Chocolate produto_pedido = (Chocolate) meioseProduto(getProdutos().get(posicoes.get(j)),
                                                            getProdutos().get(posicoes.get(j)).getQuantidade() + produto_pendente.getQuantidade() - quantidade,  i+1);
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
                        } else { // eh uma caixa
                            // se existe uma caixa ja pronta adicionada no estoque
                            if (produto_pendente.getNome().contains("Caixa")) { // talvez redundante
                                posicoes.add(i);
                                quantidade += getProdutos().get(i).getQuantidade();
                                if (quantidade >= produto_pendente.getQuantidade()) {
                                    for (int j = 0; j < posicoes.size(); j++) {
                                        boolean ultimo = j == posicoes.size() - 1;
                                        if (ultimo && quantidade != produto_pendente.getQuantidade()) {
                                            Caixa produto_pedido = (Caixa) meioseProduto(getProdutos().get(posicoes.get(j)),
                                                    getProdutos().get(posicoes.get(j)).getQuantidade() + produto_pendente.getQuantidade() - quantidade,  i+1); // quantidade - produto_pendente.getQuantidade() que sobra
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
        Produto produto_pedido;

        // CHOCOLATE
        if (produto.getClass().equals(Chocolate.class)) {
            Chocolate chocolate_pedido = new Chocolate();
            chocolate_pedido.setNome(produto.getNome());
            chocolate_pedido.setEmbalagem(produto.getEmbalagem());
            chocolate_pedido.setPeso(produto.getPeso());
            chocolate_pedido.setValidade(produto.getValidade());
            chocolate_pedido.setQuantidade(quantidade);
            chocolate_pedido.setLote(((Chocolate) produto).getLote());
            chocolate_pedido.setTipo(((Chocolate) produto).getTipo());
            chocolate_pedido.setOrigem_cacau(((Chocolate) produto).getOrigem_cacau());
            produto_pedido = chocolate_pedido;

            // CAIXA
        } else if (produto.getClass().equals(Caixa.class)) {
            Caixa caixa_pedido = new Caixa();
            caixa_pedido.setTipo(((Caixa) produto).getTipo());
            caixa_pedido.setNome(produto.getNome());
            caixa_pedido.setEmbalagem(produto.getEmbalagem());
            caixa_pedido.setPeso(produto.getPeso());
            caixa_pedido.setValidade(produto.getValidade());
            caixa_pedido.setQuantidade(quantidade);
            produto_pedido = caixa_pedido;

        } else {
            // caso contrário, trata como Produto genérico (nao usado em tese)
            produto_pedido = new Produto();
            produto_pedido.setNome(produto.getNome());
            produto_pedido.setEmbalagem(produto.getEmbalagem());
            produto_pedido.setPeso(produto.getPeso());
            produto_pedido.setValidade(produto.getValidade());
            produto_pedido.setQuantidade(quantidade);
        }

        // ajusta a quantidade do produto original
        produto.setQuantidade(produto.getQuantidade() - quantidade);

        // adiciona o produto pedido à lista de produtos na posição especificada
        addProduto(posicao, produto_pedido);

        return produto_pedido;
    }

    public Ingrediente estocarIngrediente(Scanner input) {
        Ingrediente ingrediente = new Ingrediente();
        //TIPO
        System.out.println("Escolha um tipo de ingrediente para adicionar:");
        imprimirIngredientes();
        ingrediente.setTipo(escolheObjeto(input, TiposIngredientes.values(),
                "Numero ou nome invalido. Escolha um numero de (1-16) ou digite um nome valido.", "obrigatorio"));
        ingrediente.setNome(ingrediente.getTipo().getNome());

        //QUANTIDADE
        ingrediente.setQuantidade(Integer.parseInt(getInput(input, "Quantas unidades foram compradas?", "Quantidade invalida", Verifica::isNatural)));

        //UNIDADE
        ingrediente.setUnidade(Float.parseFloat(getInput(input, "Quantos kg por unidade?", "Quantidade invalida, coloque um numero valido.", Verifica::isFloat)));

        //PRECO
        ingrediente.setPreco(Float.parseFloat(getInput(input, "Digite o preco da compra:", "Preco invalido, coloque um preco valido.", Verifica::isFloat)));

        //DATA DE COMPRA E VALIDADE
        ingrediente.setDataCompra(escolheData(input, "Digite a data de compra: (dd/mm/yyyy)", "Digite uma data válida."));
        ingrediente.setValidade(escolheDataFutura(input, "Digite a data de validade: (dd/mm/yyyy)", "Digite uma data futura válida."));

        //FORNECEDOR
        Fornecedor fornecedor;
        switch (verificaOpcao(input, new String[]{"FORNECEDORES", "Mostrar lista de fornecedores já cadastrados.", "Adicionar novo fornecedor."}, 1)) {
            case 1:
                System.out.println(listaFornecedores());
                System.out.println("Seu Fornecedor não está na lista? Para adicionar um novo fornecedor digite 'novo'.");
                System.out.println("Insira o CNPJ ou nome do seu fornecedor");
                fornecedor = escolheObjeto(input, fornecedores, "Fornecedor inexistente. Digite o CNPJ ou nome de algum fornecedor listado.", "novo");
                if (fornecedor == null) {
                    fornecedor = novoFornecedor(input);
                    addFornecedor(fornecedor);
                    ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                    break;
                }
                ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            case 2:
                fornecedor = novoFornecedor(input);
                addFornecedor(fornecedor);
                ingrediente.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        return ingrediente;
    }

    public Embalagem estocarEmbalagem(Scanner input){
        Embalagem embalagem = new Embalagem();
        //TIPO
        System.out.println("Escolha um tipo de embalagem para adicionar:");
        System.out.println(TiposEmbalagens.imprimirTiposEmbalagens());
        embalagem.setTipo_embalagem(escolheObjeto(input, TiposEmbalagens.values(),
                "Numero ou nome invalido. Escolha um numero de (1-14) ou digite um nome valido.", "obrigatorio"));
        //NOME
        embalagem.setNome(embalagem.getTipo_embalagem().getNome());
        //QUANTIDADE
        embalagem.setQuantidade(Integer.parseInt(getInput(input, "Quantas pacotes foram compradas?", "Quantidade invalida", Verifica::isNatural)));
        //PRECO_PACOTE
        embalagem.setPreco_pacote(Float.parseFloat(getInput(input, "Qual o preco de 1 pacote?", "Preco invalido, coloque um numero valido.", Verifica::isFloat)));
        //QUANTIDADE_POR_PACOTE
        embalagem.setQuantidade_por_pacote(Integer.parseInt(getInput(input, "Quantas unidades por pacote?", "Quantidade invalida", Verifica::isNatural)));
        //PRECO
        embalagem.setPreco(embalagem.getPreco_pacote() / embalagem.getQuantidade_por_pacote());
        //FORNECEDOR
        Fornecedor fornecedor;
        switch (verificaOpcao(input, new String[]{"FORNECEDORES", "Mostrar lista de fornecedores já cadastrados.", "Adicionar novo fornecedor."}, 1)) {
            case 1:
                System.out.println(listaFornecedores());
                System.out.println("Seu Fornecedor não está na lista? Para adicionar um novo fornecedor digite 'novo'.");
                System.out.println("Insira o CNPJ ou nome do seu fornecedor");
                fornecedor = escolheObjeto(input, fornecedores, "Fornecedor inexistente. Digite o CNPJ ou nome de algum fornecedor listado.", "novo");
                if (fornecedor == null) {
                    fornecedor = novoFornecedor(input);
                    addFornecedor(fornecedor);
                    embalagem.setCnpj_fornecedor(fornecedor.getCnpj());
                    break;
                }
                embalagem.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            case 2:
                fornecedor = novoFornecedor(input);
                addFornecedor(fornecedor);
                embalagem.setCnpj_fornecedor(fornecedor.getCnpj());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }
       return embalagem;
    }
}
