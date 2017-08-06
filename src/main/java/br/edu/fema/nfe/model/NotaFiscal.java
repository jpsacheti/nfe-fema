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

package br.edu.fema.nfe.model;

import java.time.LocalDateTime;

public class NotaFiscal {
    private LocalDateTime dataHoraEmissao;
    private String chaveNfe;
    private Integer codigo;
    private String xmlNota;
    private String xmlAutorizacao;

    public LocalDateTime getDataHoraEmissao() {
        return dataHoraEmissao;
    }

    public void setDataHoraEmissao(LocalDateTime dataHoraEmissao) {
        this.dataHoraEmissao = dataHoraEmissao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getXmlNota() {
        return xmlNota;
    }

    public void setXmlNota(String xmlNota) {
        this.xmlNota = xmlNota;
    }

    public String getChaveNfe() {
        return chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        this.chaveNfe = chaveNfe;
    }

    public String getXmlAutorizacao() {
        return xmlAutorizacao;
    }

    public void setXmlAutorizacao(String xmlAutorizacao) {
        this.xmlAutorizacao = xmlAutorizacao;
    }
}
