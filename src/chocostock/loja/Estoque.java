package chocostock.loja;

import chocostock.auxiliar.Processa;
import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.*;
import chocostock.interfaces.*;
import chocostock.itens.suprimentos.Equipamento;
import chocostock.itens.Item;
import chocostock.itens.suprimentos.Embalagem;
import chocostock.itens.produtos.Caixa;
import chocostock.itens.produtos.Chocolate;
import chocostock.itens.produtos.Pendente;
import chocostock.itens.produtos.Produto;
import chocostock.itens.suprimentos.Ingrediente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * A classe Estoque gerencia os produtos, materiais, equipamentos
 * e embalagens disponíveis no estoque da loja. <br>
 * Implementa os métodos "addEmbalagem", "addProduto", "addMaterial" ...,
 * "novoFornecedor", "imprimirIngredientes", "imprimirProdutos",
 * "statusIngredientes", "retiraProdutoEstoque", "estocarCliente",
 * "estocarEmbalagem" e o método privado "meioseProduto".
 */
public class Estoque implements AddRemovivel, Criavel, Escolhivel, Iteravel{
    private ArrayList<Produto> produtos;
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<Item> equipamentos;
    private ArrayList<Embalagem> embalagens;
    private ArrayList<Fornecedor> fornecedores;


    public Estoque() {
        this.produtos = new ArrayList<Produto>();
        this.ingredientes = new ArrayList<Ingrediente>();
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

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Adiciona um ingrediente na lista de ingredientes do estoque.
     */
    public boolean addIngrediente(Ingrediente ingrediente) {
        return addObjeto(ingredientes, ingrediente);
    }

    /**
     * Remove um ingrediente da lista de ingredientes.
     */
    public boolean removeIngrediente(Ingrediente ingrediente) {
        return removeObjeto(ingredientes, ingrediente);
    }

    public ArrayList<Embalagem> getEmbalagens() {
        return embalagens;
    }

    public void setEmbalagens(ArrayList<Embalagem> embalagens) {
        this.embalagens = embalagens;
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

    /**
     * Método para criar um novo fornecedor com dados fornecidos pelo usuário.
     * Solicita ao usuário que insira as informações do fornecedor via console.
     */
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
     * Adiciona uma embalagem na lista de embalagens do estoque.
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
            System.out.print(produto.getId() + " - " + produto.getNome());
            if (produto instanceof Complementavel) {
                System.out.println(" com " + listaHorizontal(((Complementavel) produto).getComplementos()) +
                        " (" + produto.getQuantidade() + (produto.getQuantidade() > 1 ? " unidades)" : " unidade)"));
            } else {
                System.out.println(" (" + produto.getQuantidade() + (produto.getQuantidade() > 1 ? " unidades)" : " unidade)"));
            }
        }
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
        String msg = "";

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
     * Retorna uma mensagem com o status das embalagens disponíveis no estoque,
     * incluindo a quantidade de cada tipo de embalagem.
     */
    public String statusEmbalagens(){
        String msg = "";
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
            msg += tipo.toString() + "\n\tUnidades: " + unidades + "\n";
        }
        return msg;
    }



    /**
     * Processa a retirada de produtos do estoque para atender a um pedido.
     */
    public Pedido retiraProdpoutosEstoque(Pedido pedido) {
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
                                // Passos semelhantes aos do Chocolate
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

    /**
     * Método para adicionar um novo ingrediente ao estoque com dados fornecidos pelo usuário.
     * Solicita ao usuário que insira as informações do ingrediente via console.
     */
    public Ingrediente estocarIngrediente(Scanner input) {
        Ingrediente ingrediente = new Ingrediente();
        //TIPO
        System.out.println("Escolha um tipo de ingrediente para adicionar:");
        imprimirIngredientes();
        ingrediente.setTipo(escolheObjeto(input, TiposIngredientes.values(),
                "Numero ou nome invalido. Escolha um numero de (1-16) ou digite um nome valido. ", "obrigatorio"));
        ingrediente.setNome(ingrediente.getTipo().getNome());

        //QUANTIDADE
        ingrediente.setQuantidade(Integer.parseInt(getInput(input, "Quantas unidades foram compradas? ", "Quantidade invalida", Verifica::isNatural)));

        //UNIDADE
        ingrediente.setUnidade(Float.parseFloat(getInput(input, "Quantos kg por unidade? ", "Quantidade invalida, coloque um numero valido.", Verifica::isFloat)));

        //PRECO
        ingrediente.setPreco(Float.parseFloat(getInput(input, "Digite o preco da compra: ", "Preco invalido, coloque um preco valido.", Verifica::isFloat)));

        //DATA DE COMPRA E VALIDADE
        ingrediente.setDataCompra(escolheData(input, "Digite a data de compra: (dd/mm/yyyy) ", "Digite uma data válida."));
        ingrediente.setValidade(escolheDataFutura(input, "Digite a data de validade: (dd/mm/yyyy) ", "Digite uma data futura válida."));

        //FORNECEDOR
        Fornecedor fornecedor;
        switch (verificaOpcao(input, new String[]{"FORNECEDORES", "Mostrar lista de fornecedores já cadastrados.", "Adicionar novo fornecedor."}, 1)) {
            case 1:
                System.out.println(getFornecedores());
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

    /**
     * Método para adicionar uma nova embalagem ao estoque com dados fornecidos pelo usuário.
     * Solicita ao usuário que insira as informações da embalagem via console.
     */
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
        embalagem.setQuantidade(Integer.parseInt(getInput(input, "Quantas pacotes foram comprados?", "Quantidade invalida", Verifica::isNatural)));
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

    public Produto estocarProduto(Scanner scanner){
        Produto produto = new Produto();
        System.out.println("Escolha um tipo de produto para adicionar:");
        return switch (verificaOpcao(scanner, new String[]{"TIPOS DE PRODUTO", "Barra.", "Caixa."}, 1)) {
            case 1 -> selecionaBarra(scanner);
            case 2 -> selecionaCaixa(scanner);
            default -> null;
        };
    }

    private Caixa selecionaCaixa(Scanner scanner) {
        Caixa caixa = new Caixa();
        for (TiposCaixas tipo : TiposCaixas.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // TIPO
        caixa.setTipo(escolheObjeto(scanner, TiposCaixas.values(), "Por favor selecione um tipo válido.", "obrigatorio"));
        // NOME
        caixa.setNome(caixa.getTipo().getNome());
        // QUANTIDADE
        caixa.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + caixa.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));
        // PRECO
        caixa.setPreco(Float.parseFloat(getInput(scanner, "Valor da unidade de " + caixa.getNome() + ": ",
                "Coloque um valor válido", Verifica::isFloat)));
        // VALIDADE
        caixa.setValidade(escolheDataFutura(scanner, "Qual a data de validade do caixa? Digite uma data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        // PESO UNITARIO
        caixa.setPeso(Integer.parseInt(getInput(scanner, "Peso da unidade de " + caixa.getNome() + " em quilos: ",
                "Coloque um valor decimal válido", Verifica::isNatural)));
        // EMBALAGEM
        System.out.println("Escolha um dos tipos de embalagem abaixo:");
        for (TiposEmbalagens tipo : TiposEmbalagens.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        caixa.setEmbalagem(escolheObjeto(scanner, TiposEmbalagens.values(), "Por favor selecione uma embalagem válida", "obrigatorio"));
        // LOTE
        caixa.setLote(Integer.parseInt(getInput(scanner, "Digite o lote de " + caixa.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));

        return caixa;
    }

    private Chocolate selecionaBarra(Scanner scanner) {
        Chocolate produto = new Chocolate();
        for (TiposChocolates tipo : TiposChocolates.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        // TIPO
        produto.setTipo(escolheObjeto(scanner, TiposChocolates.values(), "Por favor selecione um tipo válido.", "obrigatorio"));
        // NOME
        produto.setNome(produto.getTipo().getNome());
        // COMPLEMENTO
        if (Processa.normalizaString(getInput(scanner, "O produto tem algum adicional? Sim OU Não ", "Por favor, insira uma resposta válida. ",
                input -> input.matches("sim|nao|s|n"))).matches("sim|s")) {

            for (TiposComplementos complemento : TiposComplementos.values()) {
                System.out.println("(" + complemento.getId() + ") - " + complemento.getNome());
            }

            // Opção para sair da seleção de complementos
            System.out.println("(0) - Sair");
            System.out.println("Selecione até " + TiposComplementos.values().length + " complementos diferentes.");
            produto.setComplementos(escolheObjeto(scanner, TiposComplementos.values(), "Por favor, selecione um complemento válido.", "0",
                    TiposComplementos.values().length));
            produto.getComplementos().removeIf(Objects::isNull);
        }
        // ORIGEM CACAU
        produto.setOrigem_cacau(getInput(scanner, "Digite a origem do cacau: ", "Origem inválida, digite uma origem válida.", Verifica::isNome));
        // QUANTIDADE
        produto.setQuantidade(Integer.parseInt(getInput(scanner, "Quantidade de " + produto.getNome() + ": ",
                "Coloque um número inteiro maior que 0", Verifica::isNatural)));
        // PRECO
        produto.setPreco(Float.parseFloat(getInput(scanner, "Valor da unidade de " + produto.getNome() + ": ",
                "Coloque um valor válido", Verifica::isFloat)));
        // VALIDADE
        produto.setValidade(escolheDataFutura(scanner, "Qual a data de validade do produto? Digite uma data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        // PESO
        produto.setPeso(Integer.parseInt(getInput(scanner, "Peso da unidade de " + produto.getNome() + " em quilos: ",
                "Coloque um valor decimal válido", Verifica::isNatural)));
        // EMBALAGEM
        System.out.println("Escolha um dos tipos de embalagem abaixo:");
        for (TiposEmbalagens tipo : TiposEmbalagens.values()) {
            System.out.println("(" + tipo.getId() + ") - " + tipo.getNome());
        }
        produto.setEmbalagem(escolheObjeto(scanner, TiposEmbalagens.values(), "Por favor selecione uma embalagem válida", "obrigatorio"));
        return produto;
    }
}
