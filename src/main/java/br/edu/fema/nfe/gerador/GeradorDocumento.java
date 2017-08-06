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

package br.edu.fema.nfe.gerador;

import br.edu.fema.nfe.xml.*;
import br.edu.fema.nfe.xml.TNFe.InfNFe.Det.Imposto;
import br.edu.fema.nfe.xml.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN102;
import br.edu.fema.nfe.xml.TNFe.InfNFe.Det.Imposto.PIS.PISOutr;
import br.edu.fema.nfe.xml.TNFe.InfNFe.Total;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static br.edu.fema.nfe.util.Uteis.extrairNumeros;
import static br.edu.fema.nfe.util.Uteis.normalizar;

public class GeradorDocumento {

    private static final String VERSAO_NFE = "3.10";

    public String gerarDocumento() throws Exception {
        StringWriter sw = new StringWriter();
        Marshaller marshaller = JAXBContext.newInstance("br.edu.fema.nfe.xml").createMarshaller();
        marshaller.marshal(gerarObjetoJAXB(), sw);
        return sw.toString();
    }

    private JAXBElement<TEnviNFe> gerarObjetoJAXB() throws Exception {
        TEnviNFe enviNFe = new TEnviNFe();
        enviNFe.getNFe().add(adicionarDadosNFe());
        enviNFe.setVersao(VERSAO_NFE);
        enviNFe.setIndSinc("1");
        //Magic number
        enviNFe.setIdLote(String.valueOf(System.currentTimeMillis()).substring(0, 5));
        return new ObjectFactory().createEnviNFe(enviNFe);
    }

    private TNFe adicionarDadosNFe() throws Exception {
        TNFe nfe = new TNFe();
        TNFe.InfNFe infNfe = new TNFe.InfNFe();
        infNfe.setEmit(adicionarDadosEmit());
        infNfe.setVersao(VERSAO_NFE);
        infNfe.setDest(adicionarDadosDest());
        adicionarDadosItens(infNfe);
        adicionarDadosTotal(infNfe);
        adicionarDadosTransportadora(infNfe);
        adicionarDadosDuplicata(infNfe);
        String chaveNfe = adicionarChaveAcesso();
        String digitoVerificador = calcularModulo11(chaveNfe);
        infNfe.setId(chaveNfe + digitoVerificador);
        infNfe.setIde(adicionarDadosIde(digitoVerificador));
        nfe.setInfNFe(infNfe);
        return nfe;
    }

    private TNFe.InfNFe.Ide adicionarDadosIde(String digitoVerificador) {
        TNFe.InfNFe.Ide ide = new TNFe.InfNFe.Ide();
        ide.setCUF("35");
        ide.setCNF("00000001");
        ide.setNatOp("venda");
        ide.setIndPag("1");
        ide.setNNF("000000001");
        ide.setDhEmi(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        ide.setDhSaiEnt(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        ide.setTpNF("1");
        ide.setIdDest("1");
        ide.setCMunFG("351000");
        ide.setTpImp("1");
        ide.setTpEmis("1");
        ide.setCDV(digitoVerificador);
        ide.setTpAmb("2"); //Homologacao
        ide.setFinNFe("1");
        ide.setIndFinal("1");
        ide.setIndPres("1");
        ide.setMod("55");
        ide.setProcEmi("0");
        ide.setIdDest("1");
        ide.setSerie("001");
        ide.setVerProc("1.00");
        return ide;
    }

    private void adicionarDadosDuplicata(TNFe.InfNFe infNfe) {
        TNFe.InfNFe.Cobr cobr = new TNFe.InfNFe.Cobr();
        TNFe.InfNFe.Cobr.Dup dup = new TNFe.InfNFe.Cobr.Dup();
        dup.setDVenc(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        dup.setNDup("1");
        dup.setVDup("30.00");
        cobr.getDup().add(dup);
        infNfe.setCobr(cobr);
    }

    private void adicionarDadosTransportadora(TNFe.InfNFe infNfe) {
        TNFe.InfNFe.Transp transporte = new TNFe.InfNFe.Transp();
        transporte.setModFrete("9");
        infNfe.setTransp(transporte);
    }

    private void adicionarDadosTotal(TNFe.InfNFe infNfe) {
        Total total = new Total();
        Total.ICMSTot icmsTot = new Total.ICMSTot();
        icmsTot.setVBC("0.00");
        icmsTot.setVICMS("0.00");
        icmsTot.setVCOFINS("0.00");
        icmsTot.setVDesc("0.00");
        icmsTot.setVFrete("0.00");
        icmsTot.setVICMSDeson("0.00");
        icmsTot.setVII("0.00");
        icmsTot.setVIPI("0.00");
        icmsTot.setVSeg("0.00");
        icmsTot.setVProd("30.00");
        icmsTot.setVOutro("0.00");
        icmsTot.setVNF("30.00");
        icmsTot.setVPIS("0.00");
        icmsTot.setVTotTrib("0.00");
        icmsTot.setVBCST("0.00");
        icmsTot.setVST("0.00");
        total.setICMSTot(icmsTot);
        infNfe.setTotal(total);

    }

    private void adicionarDadosItens(TNFe.InfNFe infNfe) {
        TNFe.InfNFe.Det det = new TNFe.InfNFe.Det();
        adicionarDadosProduto(det);
        det.setNItem("1");
        det.setImposto(getImpostoProduto());
        infNfe.getDet().add(det);
    }

    private Imposto getImpostoProduto() {
        Imposto imposto = new Imposto();
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoVTotTrib("0.00"));
        adicionarDadosICMS(imposto);
        adicionarDadosPis(imposto);
        adicionarDadosCofins(imposto);
        return imposto;
    }

    private void adicionarDadosCofins(Imposto imposto) {
        Imposto.COFINS cofins = new Imposto.COFINS();
        Imposto.COFINS.COFINSOutr cofinsOutros = new Imposto.COFINS.COFINSOutr();
        cofinsOutros.setCST("99");
        cofinsOutros.setQBCProd("0.00");
        cofinsOutros.setVAliqProd("0.00");
        cofinsOutros.setVCOFINS("0.00");
        cofins.setCOFINSOutr(cofinsOutros);
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoCOFINS(cofins));
    }

    private void adicionarDadosPis(Imposto imposto) {
        Imposto.PIS pis = new Imposto.PIS();
        PISOutr pisOutros = new PISOutr();
        pisOutros.setCST("99");
        pisOutros.setQBCProd("0.00");
        pisOutros.setVAliqProd("0.00");
        pisOutros.setVPIS("0.00");
        pis.setPISOutr(pisOutros);
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoPIS(pis));
    }

    private void adicionarDadosICMS(Imposto imposto) {
        Imposto.ICMS icms = new Imposto.ICMS();
        ICMSSN102 icms102 = new ICMSSN102();
        icms102.setOrig("0");
        icms102.setCSOSN("102");
        icms.setICMSSN102(icms102);
        imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoICMS(icms));
    }

    private void adicionarDadosProduto(TNFe.InfNFe.Det det) {
        TNFe.InfNFe.Det.Prod prod = new TNFe.InfNFe.Det.Prod();
        prod.setCProd("1");
        prod.setCEAN("");
        prod.setCEANTrib("");
        prod.setXProd(normalizar("Camiseta branca masculina algodão", 120));
        prod.setNCM(extrairNumeros("6105.10.00"));
        prod.setEXTIPI("00");
        prod.setCFOP("5102");
        prod.setQCom("1");
        prod.setUCom(normalizar("und", 6));
        prod.setVUnCom("30.00");
        prod.setQTrib("1");
        prod.setVUnTrib("und");
        prod.setUTrib(normalizar("und", 6));
        prod.setVProd("30.00");
        prod.setIndTot("1");
        det.setProd(prod);
    }

    private String adicionarChaveAcesso() {
        return new StringBuilder().append("NFe")
                .append("35")
                .append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM")))
                .append("72345396000110")
                .append("55")
                .append("001")
                .append("000000001")
                .append("1")
                .append("00000001")
                .toString();
    }

    private String calcularModulo11(String sequencia) {
        int soma = 0;
        int multiplicador = 2;
        int digito;
        int resto;
        for (int i = sequencia.length(); i > 0; i--) {
            digito = Character.getNumericValue(sequencia.charAt(i - 1));
            soma += digito * multiplicador;
            multiplicador++;
            if (multiplicador > 9) {
                multiplicador = 2;
            }
        }
        resto = soma % 11;
        if (resto == 0 || resto == 1) {
            return "0";
        } else {
            return String.valueOf(11 - resto);
        }

    }

    private TNFe.InfNFe.Dest adicionarDadosDest() throws Exception {
        TNFe.InfNFe.Dest dest = new TNFe.InfNFe.Dest();
        dest.setCNPJ(extrairNumeros("36.165.203/0001-85"));
        dest.setXNome(normalizar("Enzo e Rebeca Pizzaria Delivery ME", 60));
        dest.setIE(extrairNumeros("158.417.703.691"));
        dest.setIndIEDest("1");
        dest.setEnderDest(getEnderDest());
        return dest;
    }

    private TEndereco getEnderDest() {
        TEndereco endereco = new TEndereco();
        endereco.setXLgr(normalizar("Rua Sete", 60));
        endereco.setNro("180");
        endereco.setXBairro("Recanto Viracopos");
        endereco.setCMun("352050");
        endereco.setUF(TUf.SP);
        endereco.setXMun(normalizar("Indaiatuba", 60));
        endereco.setCEP(extrairNumeros("13336-526"));
        endereco.setFone(extrairNumeros("(19)2952-7294"));
        return endereco;
    }

    private TNFe.InfNFe.Emit adicionarDadosEmit() {
        TNFe.InfNFe.Emit emit = new TNFe.InfNFe.Emit();
        emit.setCNPJ("72345396000110");
        emit.setXNome("Empresa Teste ME");
        emit.setIE("ISENTO");
        /*
         * 1=Simples Nacional;
         * 2=Simples Nacional, excesso sublimite de receita bruta;
         * 3=Regime Normal.
         */
        emit.setCRT("1");
        emit.setEnderEmit(adicionarEnderEmit());
        return emit;
    }

    private TEnderEmi adicionarEnderEmit() {
        TEnderEmi enderEmit = new TEnderEmi();
        enderEmit.setXLgr(normalizar("Rua Hipotética", 60));
        enderEmit.setNro("1055");
        enderEmit.setXBairro(normalizar("Bairro X", 60));
        enderEmit.setCMun("351000");
        enderEmit.setXMun(normalizar("Cândido Mota", 60));
        enderEmit.setUF(TUfEmi.SP);
        enderEmit.setCEP(extrairNumeros("19880-000"));
        enderEmit.setFone(extrairNumeros("(18)3341-0000"));
        return enderEmit;
    }
}
