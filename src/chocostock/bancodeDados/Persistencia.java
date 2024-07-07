package chocostock.bancodeDados;

import chocostock.loja.Loja;
import chocostock.colaboradores.Cliente;
import chocostock.colaboradores.Fornecedor;
import chocostock.colaboradores.Funcionario;
import chocostock.itens.produtos.Produto;
import chocostock.itens.suprimentos.Suprimento;
import chocostock.loja.Pedido;

import java.io.*;

public class Persistencia {
    private static final String DIRETORIO_BANCO = "src/chocostock/bancodeDados";
    private static final String ARQUIVO_LOJA = "loja.dat";
    private static final String ARQUIVO_IDS = "ids.dat";

    public static void salvarLoja(Loja loja) {
        try {
            FileOutputStream fos = new FileOutputStream(DIRETORIO_BANCO + "/" + ARQUIVO_LOJA);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(loja);
            oos.close();
            fos.close();
            System.out.println("Loja salva com sucesso!");

            salvarIDs();
        }
        catch (IOException e) {
            System.err.println("Erro ao salvar a loja: " + e.getMessage());
        }
    }

    public static Loja carregarLoja() {
        Loja loja = null;
        try {
            FileInputStream fis = new FileInputStream(DIRETORIO_BANCO + "/" + ARQUIVO_LOJA);
            ObjectInputStream ois = new ObjectInputStream(fis);
            loja = (Loja) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Loja carregada com sucesso!");

            carregarIDs();
        }
        catch (FileNotFoundException e) {
            System.out.println("Criando nova loja!");
            loja = new Loja().criarNovaLoja();
        }
        catch (InvalidClassException ice) {
            // Caso a versão da classe serializada seja incompatível
            System.err.println("Erro ao carregar a loja: versão incompatível da classe serializada.");
            System.err.println("Excluindo o arquivo .dat anterior. E iniciando loja vazia");
            File arquivoLoja = new File(DIRETORIO_BANCO + "/" + ARQUIVO_LOJA);
            File arquivoIDs = new File(DIRETORIO_BANCO + "/" + ARQUIVO_IDS);

            if (arquivoLoja.exists()) {
                boolean delLoja = arquivoLoja.delete(); // Exclui o arquivo .dat anterior
                if (!delLoja) {
                    System.err.println("Falha ao excluir o arquivo loja.dat");
                }
            }
            if (arquivoIDs.exists()) {
                boolean delIDs = arquivoIDs.delete(); // Exclui o arquivo .dat anterior
                if (!delIDs) {
                    System.err.println("Falha ao excluir o arquivo ids.dat");
                }
            }
            loja = new Loja().criarNovaLoja(); // Cria uma nova loja vazia
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar a loja: " + e.getMessage());
        }
        return loja;
    }

    private static void salvarIDs() throws IOException {
        FileOutputStream fos = new FileOutputStream(DIRETORIO_BANCO + "/" + ARQUIVO_IDS);
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeInt(Cliente.getIdCliente());
        dos.writeInt(Fornecedor.getIdFornecedor());
        dos.writeInt(Funcionario.getIdFuncionario());
        dos.writeInt(Produto.getIdProduto());
        dos.writeInt(Suprimento.getIdSuprimento());
        dos.writeInt(Pedido.getIdPedido());

        dos.close();
        fos.close();
    }

    private static void carregarIDs() throws IOException {
        FileInputStream fis = new FileInputStream(DIRETORIO_BANCO + "/" + ARQUIVO_IDS);
        DataInputStream dis = new DataInputStream(fis);
        Cliente.setIdCliente(dis.readInt());
        Fornecedor.setIdFornecedor(dis.readInt());
        Funcionario.setIdFuncionario(dis.readInt());
        Produto.setIdProduto(dis.readInt());
        Suprimento.setIdSuprimento(dis.readInt());
        Pedido.setIdPedido(dis.readInt());

        dis.close();
        fis.close();
    }
}