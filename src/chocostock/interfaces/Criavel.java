package chocostock.interfaces;

import chocostock.auxiliar.Escolhe;
import chocostock.auxiliar.Processa;
import chocostock.auxiliar.Verifica;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Fornecedor;
import chocostock.enums.Status;
import chocostock.loja.Loja;
import chocostock.loja.Pedido;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public interface Criavel extends ValidadorInput, Escolhivel {
    default Cliente criarCliente(Scanner scanner) {
        Cliente cliente = new Cliente();
        System.out.println("Cadastrando novo cliente: ");
        // NOME
        cliente.setNome(getInput(scanner, "Nome do cliente: ", "Nome inválido.", Verifica::isNome));
        // TELEFONE
        cliente.setTelefone(getInput(scanner, "Telefone do cliente: ", "Insira um número válido, não esqueça o DDD!",
                Verifica::isTelefone).replaceAll("\\D", ""));
        // EMAIL
        cliente.setEmail(getInput(scanner, "Email do cliente: ", "Insira um email válido!", Verifica::isEmail));
        // ENDERECO
        System.out.println("Criando endereço: ");
        cliente.setEndereco(cliente.criaEndereco(scanner));

        return cliente;
    }

    default Fornecedor criarFornecedor(Scanner scanner) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(getInput(scanner, "Nome do fornecedor: ", "Nome invalido. Insira novamente.", Verifica::isNome));
        fornecedor.setTelefone(getInput(scanner, "Telefone do fornecedor: ","Telefone inválido. Insira novamente.", Verifica::isTelefone));
        fornecedor.setTelefone(getInput(scanner, "Email do fornecedor:", "Email inválido. Insira novamente.", Verifica::isEmail));
        fornecedor.setTelefone(getInput(scanner, "Endereço do fornecedor:", "Endereço inválido. Insira novamente.", Verifica::isNome)); // Mudar o isNome?
        fornecedor.setCnpj(getInput(scanner, "CNPJ do fornecedor:", "CNPJ inválido. Insira novamente.", Verifica::isCnpj));
        fornecedor.setCnpj(getInput(scanner, "Site do fornecedor:", "Site inválido. Insira novamente.", Verifica::isSite));
        return fornecedor;
    }

    /*
    BUGS:
    -Digita uma string quando pede INT ou FLOAT
    -Digita ID de ingrediente invalido (1-17)
     */


    default Pedido criarPedido(Scanner scanner, Loja loja)  {
        Pedido pedido = new Pedido();
//        String msg =   """
//                --- NOVO PEDIDO ---
//                Selecione uma das opções:
//                (1) - Mostrar lista de clientes já cadastrados.
//                (2) - Adicionar novo cliente.
//                """;

        // CLIENTE
        switch (verificaOpcao(scanner, new String[]{"NOVO PEDIDO", "Mostrar lista de clientes já cadastrados.", "Adicionar novo cliente."}, 1)) {
            case 1:
                System.out.println(loja.listaClientes());
                System.out.println("Seu cliente não está na lista? Para adicionar um novo cliente digite 'novo'.");
                System.out.println("Insira o ID ou nome do seu cliente");
                Cliente cliente = loja.escolheObjeto(scanner, loja.getClientes(), "Cliente inexistente. Digite o ID ou nome de algum usuário listado.", "novo");
                if (cliente == null) {
                    Cliente cliente2 = criarCliente(scanner);
                    loja.addCliente(cliente2);
                    pedido.setId_cliente(cliente2.getId());
                    break;
                }
                pedido.setId_cliente(cliente.getId());
                break;
            case 2:
                Cliente cliente2 = criarCliente(scanner);
                loja.addCliente(cliente2);
                pedido.setId_cliente(cliente2.getId());
                break;
            default:
                System.out.println("Da próxima selecione uma resposta válida! Finalizando programa!");
                break;
        }

        // DATA_ENTREGA
        System.out.println("Qual a data de entrega do pedido? ");
        pedido.setData_entrega(escolheDataFutura(scanner, "Digite a data futura no formato DD/MM/YYYY: ",
                "Formato de data inválido. Por favor, insira uma data futura no formato DD/MM/YYYY."));
        System.out.println("Data inserida: " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(pedido.getData_entrega()));

        // PAGO OU N
        pedido.setPago(Processa.normaliza(loja.getInput(scanner, "O pedido feito já foi pago? Sim OU Não", "Por favor, insira uma resposta valida. ",
                input -> input.matches("sim|nao|s|n"))).equals("sim|s"));
        System.out.println(pedido.isPago() ? "Pedido foi marcado como pago!" : "Pedido foi marcado como nao pago!");

        // STATUS
        for (Status status : Status.values()) {
            System.out.println(status.getId() + "-" + status.getNome());
        }
        System.out.println("Qual o status do pedido dentre os acima? ");
        pedido.setStatus(escolheObjeto(scanner, Status.values(), "Status inválido. Digite um número válido ou o nome do status.", "obrigatorio"));
        System.out.println("O status do seu pedido foi definido para " + pedido.getStatus().getNome() + ".");

        // PRODUTOS_PENDENTES
        System.out.println("Selecione qual produto precisa ser adicionado ao pedido. ");
        pedido.setProdutos(loja.escolheProdutos(scanner));
        // pega_produtos_do_estoque() para tirar de pendentes

        // PRECO TOTAL
        pedido.calculaPrecoTotal();
        System.out.println("Preco total do pedido ficou: R$" + pedido.getPreco_total() + ".");

        return pedido;
    }

}
