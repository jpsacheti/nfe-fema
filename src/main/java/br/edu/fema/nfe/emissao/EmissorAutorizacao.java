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

import br.edu.fema.nfe.ws.NfeAutorizacaoStub;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeAutorizacaoLoteResult;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeCabecMsg;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeCabecMsgE;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeDadosMsg;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;

public class EmissorAutorizacao implements Emissor {

    private static final String VERSAO_WS = "3.10";
    private final NfeAutorizacaoStub stub;

    EmissorAutorizacao() throws AxisFault {
        stub = new NfeAutorizacaoStub();
    }

    @Override
    public String emitir(String documento) throws Exception {
        NfeDadosMsg dadosMsg = new NfeDadosMsg();
        OMElement ome = AXIOMUtil.stringToOM(documento);

        dadosMsg.setExtraElement(ome);
        NfeCabecMsg dadosCabecalho = new NfeCabecMsg();
        dadosCabecalho.setCUF("35");
        dadosCabecalho.setVersaoDados(VERSAO_WS);
        NfeCabecMsgE cabecalho = new NfeCabecMsgE();
        cabecalho.setNfeCabecMsg(dadosCabecalho);
        NfeAutorizacaoLoteResult result = stub.nfeAutorizacaoLote(dadosMsg, cabecalho);
        return result.getExtraElement().toString();
    }

}
