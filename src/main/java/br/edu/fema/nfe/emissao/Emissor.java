package br.edu.fema.nfe.emissao;

import br.edu.fema.nfe.xml.TEnviNFe;

public interface Emissor {
    String emitir(TEnviNFe enviNFe) throws Exception;
}
