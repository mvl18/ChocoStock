package chocostock.interfaces;

import chocostock.enums.TiposComplementos;

import java.util.ArrayList;

/**
 * A interface Complementavel define o contrato para classes que possuem
 * um método para obter um complemento.
 */
public interface Complementavel {
    ArrayList<TiposComplementos> getComplementos();
}
