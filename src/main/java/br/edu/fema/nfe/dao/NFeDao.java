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

package br.edu.fema.nfe.dao;

import br.edu.fema.nfe.model.NotaFiscal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLXML;

public class NFeDao {
    /**
     * Exemplificação do processo de armazenamento em banco de dados de uma Nota Fiscal eletrônica
     * que foi autorizada com sucesso, bem como quais campo são importantes para serem persistidos numa
     * aplicação real
     *
     * @param notaFiscal a ser persistida
     * @throws SQLException caso haja problemas no banco de dados
     */
    public void salvar(NotaFiscal notaFiscal) throws SQLException{
        String sql = "INSERT INTO notafiscal(xmlnota, xmlautorizacao, chavenfe, datahoraemissao) VALUES  (?, ?, ?, ?)";
        PreparedStatement ps = Conexao.getConnection().prepareStatement(sql);
        SQLXML sqlXml = Conexao.getConnection().createSQLXML();
        sqlXml.setString(notaFiscal.getXmlNota());
        ps.setSQLXML(1, sqlXml);
        ps.setString(2, notaFiscal.getXmlAutorizacao());
        ps.setString(3, notaFiscal.getChaveNfe());
        ps.setObject(4, notaFiscal.getDataHoraEmissao());
        ps.execute();
        ps.close();
    }
}
