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
import java.util.ArrayList;
import java.util.List;


/**
 *  Tipo Pedido de Concessão de Autorização da Nota Fiscal Eletrônica
 *
 * <p>Classe Java de TEnviNFe complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType name="TEnviNFe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idLote" type="{http://www.portalfiscal.inf.br/nfe}TIdLote"/>
 *         &lt;element name="indSinc">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;whiteSpace value="preserve"/>
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NFe" type="{http://www.portalfiscal.inf.br/nfe}TNFe" maxOccurs="50"/>
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
@XmlType(name = "TEnviNFe", propOrder = {
        "idLote",
        "indSinc",
        "nFe"
})
public class TEnviNFe {

    @XmlElement(required = true)
    protected String idLote;
    @XmlElement(required = true)
    protected String indSinc;
    @XmlElement(name = "NFe", required = true)
    protected List<TNFe> nFe;
    @XmlAttribute(name = "versao", required = true)
    protected String versao;

    /**
     * Obtém o valor da propriedade idLote.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdLote() {
        return idLote;
    }

    /**
     * Define o valor da propriedade idLote.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdLote(String value) {
        this.idLote = value;
    }

    /**
     * Obtém o valor da propriedade indSinc.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIndSinc() {
        return indSinc;
    }

    /**
     * Define o valor da propriedade indSinc.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIndSinc(String value) {
        this.indSinc = value;
    }

    /**
     * Gets the value of the nFe property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nFe property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNFe().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TNFe }
     *
     *
     */
    public List<TNFe> getNFe() {
        if (nFe == null) {
            nFe = new ArrayList<>();
        }
        return this.nFe;
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
