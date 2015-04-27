/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conn.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author niltonkummer
 */
public class DefaultDao {

    public Connection conexao;
    public PreparedStatement comando;

    public void conectar(String sql) throws ClassNotFoundException, SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql);
    }

    public void fecharConexao() throws SQLException {
        comando.close();
        conexao.close();
    }
}
