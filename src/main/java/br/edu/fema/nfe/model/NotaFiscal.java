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
