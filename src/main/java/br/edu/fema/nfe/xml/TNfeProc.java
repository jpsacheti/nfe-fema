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

//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.07.16 às 05:41:14 PM BRT 
//


package br.edu.fema.nfe.xml;

import javax.xml.bind.annotation.*;


/**
 *  Tipo da NF-e processada
 *
 * <p>Classe Java de TNfeProc complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType name="TNfeProc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NFe" type="{http://www.portalfiscal.inf.br/nfe}TNFe"/>
 *         &lt;element name="protNFe" type="{http://www.portalfiscal.inf.br/nfe}TProtNFe"/>
 *       &lt;/sequence>
 *       &lt;attribute name="versao" use="required" type="{http://www.portalfiscal.inf.br/nfe}TVerNFe" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TNfeProc", propOrder = {
        "nFe",
        "protNFe"
})
public class TNfeProc {

    @XmlElement(name = "NFe", required = true)
    protected TNFe nFe;
    @XmlElement(required = true)
    protected TProtNFe protNFe;
    @XmlAttribute(name = "versao", required = true)
    protected String versao;

    /**
     * Obtém o valor da propriedade nFe.
     *
     * @return
     *     possible object is
     *     {@link TNFe }
     *
     */
    public TNFe getNFe() {
        return nFe;
    }

    /**
     * Define o valor da propriedade nFe.
     *
     * @param value
     *     allowed object is
     *     {@link TNFe }
     *
     */
    public void setNFe(TNFe value) {
        this.nFe = value;
    }

    /**
     * Obtém o valor da propriedade protNFe.
     *
     * @return
     *     possible object is
     *     {@link TProtNFe }
     *
     */
    public TProtNFe getProtNFe() {
        return protNFe;
    }

    /**
     * Define o valor da propriedade protNFe.
     *
     * @param value
     *     allowed object is
     *     {@link TProtNFe }
     *
     */
    public void setProtNFe(TProtNFe value) {
        this.protNFe = value;
    }

    /**
     * Obtém o valor da propriedade versao.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVersao() {
        return versao;
    }

    /**
     * Define o valor da propriedade versao.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersao(String value) {
        this.versao = value;
    }

}
