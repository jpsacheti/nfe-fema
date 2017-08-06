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

public class ProcessadorEnvioNFe {
    private final char[] senhaCertificado;
    private KeyStoreLoader keyStoreLoader;

    public ProcessadorEnvioNFe(KeyStoreLoader keyStoreLoader, char[] senhaCertificado) {
        this.keyStoreLoader = keyStoreLoader;
        this.senhaCertificado = senhaCertificado;
    }

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

    private TRetEnviNFe getResultadoSaida(String saidaWs) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TRetEnviNFe.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StreamSource(new StringReader(saidaWs)), TRetEnviNFe.class).getValue();
    }

    private void processarResultado(NotaFiscal nf, TRetEnviNFe resultado) {
        if (Objects.equals(resultado.getProtNFe().getInfProt().getCStat(), "100")) {
            nf.setChaveNfe(resultado.getProtNFe().getInfProt().getChNFe());
        } else {
            throw new IllegalStateException("NFe não autorizada!");
        }
    }

}
