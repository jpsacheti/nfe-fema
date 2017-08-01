package br.edu.fema.nfe.emissao;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;

import br.edu.fema.nfe.ws.NfeAutorizacaoStub;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeAutorizacaoLoteResult;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeCabecMsg;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeCabecMsgE;
import br.edu.fema.nfe.ws.NfeAutorizacaoStub.NfeDadosMsg;

public class EmissorAutorizacao implements Emissor {

	private static final String VERSAO_WS = "3.10";
	private final NfeAutorizacaoStub stub;

	public EmissorAutorizacao() throws AxisFault {
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
