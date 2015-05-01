package dao;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Sala;

/**
 *
 * @author niltonkummer Implementação do Dao Sala para Mysql
 */
public class SalaDaoMysql extends DefaultDao implements SalaDao {

    private static final String idtabela = "idsala";
    private static final String tabela = "sala";

    @Override
    public void inserir(Sala sala) {
        try {
            String sql = "INSERT INTO " + tabela + " "
                    + "(idsala,capacidade) VALUES(?,?)";
            conectar(sql);
            comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            setAllFields(comando, sala);

            comando.executeUpdate();
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                sala.setNumero(resultado.getInt(1));
            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean deletar(Sala sala) {
        try {
            String sql = "DELETE FROM " + tabela + " WHERE " + idtabela + "=?";
            conectar(sql);
            comando.setInt(1, sala.getNumero());
            comando.executeUpdate();
            fecharConexao();
            return true;
        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (MySQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Sala não pode ser removida pois já existe sessão vinculada");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void atualizar(Sala sala) {
        try {
            String sql = "UPDATE " + tabela + " SET nome=?, genero=?, sinopse=?, "
                    + "faixa_etaria=?, duracao=? WHERE " + idtabela + "=?";
            conectar(sql);
            setAllFields(comando, sala);
            comando.setInt(5, sala.getNumero());
            comando.executeUpdate();
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Sala buscarPorId(int id) {
        Sala sala = null;
        try {
            String sql = "SELECT * FROM " + tabela + " WHERE " + idtabela + "=?";
            conectar(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                sala = buildObject(resultado);

            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sala;
    }

    @Override
    public List<Sala> listar() {
        List<Sala> listarSalas = new ArrayList<Sala>();
        try {
            String sql = "SELECT * FROM " + tabela + "";
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Sala sala = buildObject(resultado);
                listarSalas.add(sala);
            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalaDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarSalas;
    }

    private void setAllFields(PreparedStatement comando, Sala sala) throws SQLException {
        comando.setInt(1, sala.getNumero());
        comando.setInt(2, sala.getCapacidade());

    }

    private Sala buildObject(ResultSet resultado) throws SQLException {
        return new Sala(
                resultado.getInt("idsala"),
                resultado.getInt("capacidade")
        );
    }

}
