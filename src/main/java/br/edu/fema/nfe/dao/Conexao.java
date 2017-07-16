package br.edu.fema.nfe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by joao on 16/07/17.
 */
public class Conexao {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if(connection == null) {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "masterkey");
            }
            return connection;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
}
