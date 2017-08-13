/*
 * Copyright 2017 João Pedro Sacheti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License athttp://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.edu.fema.nfe.emissao;

import br.edu.fema.nfe.dao.NFeDao;
import br.edu.fema.nfe.gerador.AssinadorDocumento;
import br.edu.fema.nfe.gerador.GeradorDocumento;
import br.edu.fema.nfe.model.NotaFiscal;
import br.edu.fema.nfe.util.KeyStoreLoader;
import br.edu.fema.nfe.xml.TRetEnviNFe;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe que orquestra  o procedimento envolvido no envio e processamento da NFe
 */
public class ProcessadorEnvioNFe {
    //Senhas de certificado digital sempre deverão ser armazenadas em memória por um vetor de char
    private final char[] senhaCertificado;
    private KeyStoreLoader keyStoreLoader;

    public ProcessadorEnvioNFe(KeyStoreLoader keyStoreLoader, char[] senhaCertificado) {
        this.keyStoreLoader = keyStoreLoader;
        this.senhaCertificado = senhaCertificado;
    }

    /**
     * Método público para envio da NFe, validação e gravação no banco de dados
     *
     * @throws Exception caso haja qualquer tipo de erro
     */
    public void processar() throws Exception {
        Emissor emissor = new EmissorAutorizacao();
        NotaFiscal nf = new NotaFiscal();
        nf.setDataHoraEmissao(LocalDateTime.now());
        String documento = new GeradorDocumento().gerarDocumento();
        String documentoAssinado = AssinadorDocumento.assinarDocumento(documento, keyStoreLoader, senhaCertificado);
        nf.setXmlNota(documentoAssinado);
        String saidaWs = emissor.emitir(documentoAssinado);
        TRetEnviNFe resultado = getResultadoSaida(saidaWs);
        processarResultado(nf, resultado);
        nf.setXmlAutorizacao(saidaWs);
        new NFeDao().salvar(nf);
    }

    /**
     * Invoca o JAXB para fazer o parse do resultado
     * @param saidaWs
     * @return O objeto contendo a resposta do servidor da Sefaz
     * @throws JAXBException
     */
    private TRetEnviNFe getResultadoSaida(String saidaWs) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TRetEnviNFe.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StreamSource(new StringReader(saidaWs)), TRetEnviNFe.class).getValue();
    }

    /**
     * Tenta extrair a chave da resposta da SEFAZ e vincula ao BO.
     * @param nf nota na qual será vinculada a chave
     * @param resultado vindo do webservice da secretaria da fazenda
     * @throws IllegalStateException caso a nota não tenha sido autorizada
     */
    private void processarResultado(NotaFiscal nf, TRetEnviNFe resultado) {
        final String CODIGO_AUTORIZACAO_NFE = "100";
        if (Objects.equals(resultado.getProtNFe().getInfProt().getCStat(), CODIGO_AUTORIZACAO_NFE)) {
            nf.setChaveNfe(resultado.getProtNFe().getInfProt().getChNFe());
        } else {
            throw new IllegalStateException("NFe não autorizada!");
        }
    }

}
