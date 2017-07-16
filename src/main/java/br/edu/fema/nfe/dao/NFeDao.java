package br.edu.fema.nfe.dao;

import br.edu.fema.nfe.model.NotaFiscal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLXML;

public class NFeDao {
	public void salvar(NotaFiscal notaFiscal) throws SQLException{
        String sql = "insert into notafiscal(xmlnota, chavenfe, datahoraemissao) VALUES  (?, ?, ?)";
        PreparedStatement ps = Conexao.getConnection().prepareStatement(sql);
        SQLXML sqlXml = Conexao.getConnection().createSQLXML();
        sqlXml.setString(notaFiscal.getXmlNota());
        ps.setSQLXML(1, sqlXml);
        ps.setString(2, notaFiscal.getChaveNfe());
        ps.setObject(3, notaFiscal.getDataHoraEmissao());
        ps.execute();
        ps.close();
	}
}
