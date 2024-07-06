package chocostock.enums;

import chocostock.interfaces.Codificavel;
import chocostock.interfaces.Nomeavel;

/**
 * Lista as unidades federativas possíveis para os endereços.
 */
public enum Estados implements Nomeavel, Codificavel {
    XX("XX", "", -1),
    AC("AC", "Acre", 699),
    AL("AL", "Alagoas", 570),
    AP("AP", "Amapá", 689),
    AM("AM", "Amazonas", 690),
    BA("BA", "Bahia", 400),
    CE("CE", "Ceará", 600),
    DF("DF", "Distrito Federal", 700),
    ES("ES", "Espírito Santo", 290),
    GO("GO", "Goiás", 728),
    MA("MA", "Maranhão", 650),
    MT("MT", "Mato Grosso", 780),
    MS("MS", "Mato Grosso do Sul", 790),
    MG("MG", "Minas Gerais", 300),
    PA("PA", "Pará", 660),
    PB("PB", "Paraíba", 580),
    PR("PR", "Paraná", 800),
    PE("PE", "Pernambuco", 500),
    PI("PI", "Piauí", 640),
    RJ("RJ", "Rio de Janeiro", 200),
    RN("RN", "Rio Grande do Norte", 590),
    RS("RS", "Rio Grande do Sul", 900),
    RO("RO", "Rondônia", 789),
    RR("RR", "Roraima", 693),
    SC("SC", "Santa Catarina", 880),
    SP("SP", "São Paulo", 10),
    SE("SE", "Sergipe", 490),
    TO("TO", "Tocantins", 770);

    private final String codigo;
    private final String nome;
    private final int id;

    Estados(String codigo, String nome, int id) {
        this.codigo = codigo;
        this.nome = nome;
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public static String[] getTipos(){
        int num_tipos = Estados.values().length;
        int i = 0;
        String[] tipos = new String[num_tipos];
        for(Estados t : Estados.values()){
            tipos[i] = t.getNome();
            i++;
        }
        return tipos;
    }

    public static Estados parseEstado(String nome) {
        for (Estados estado : Estados.values()) {
            if (estado.getNome().equals(nome))
                return estado;
        }
        return XX;
    }
}