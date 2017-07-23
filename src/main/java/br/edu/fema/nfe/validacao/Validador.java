package br.edu.fema.nfe.validacao;

import br.edu.fema.nfe.xml.TEnviNFe;

public interface Validador {
    boolean validar(TEnviNFe valor) throws Exception;
}
