package dao;

import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Sessao;

/**
 *
 * @author niltonkummer Implementação do Dao Sessão para Mysql
 */
public class SessaoDaoMysql extends DefaultDao implements SessaoDao {

    private static final String idtabela = "idsessao";
    private static final String tabela = "sessao";

    @Override
    public void inserir(Sessao sessao) {
        try {
            String sql = "INSERT INTO " + tabela + " "
                    + "(idsala,idfilme,horario,expiracao) VALUES(?,?,?,?)";
            conectar(sql);
            comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            setAllFields(comando, sessao);

            comando.executeUpdate();
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                sessao.setId(resultado.getInt(1));
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deletar(Sessao sessao) {
        try {
            String sql = "DELETE FROM " + tabela + " WHERE id=?";
            conectar(sql);
            comando.setInt(1, sessao.getId());
            comando.executeUpdate();
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean atualizar(Sessao sessao) {
        try {
            String sql = "UPDATE " + tabela + " SET idsala=?, idfilme=?, horario=?, "
                    + "expiracao=? WHERE " + idtabela + "=?";
            conectar(sql);
            setAllFields(comando, sessao);
            comando.setInt(5, sessao.getId());
            comando.executeUpdate();
            fecharConexao();
            return true;
        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Sessao buscarPorId(int id) {
        Sessao sessao = null;
        try {
            String sql = "SELECT * FROM " + tabela + " WHERE idsessao=?";
            conectar(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                sessao = buildObject(resultado);

            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessao;
    }

    @Override
    public List<Sessao> buscarPorNome(String nome) {
        List<Sessao> listaSessoes = new ArrayList<Sessao>();
        try {
            String sql = "SELECT * FROM " + tabela + " WHERE nome LIKE ?";
            conectar(sql);
            comando.setString(1, "%" + nome + "%");
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Sessao sessao = buildObject(resultado);
                listaSessoes.add(sessao);
            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSessoes;
    }

    @Override
    public List<Sessao> listar() {
        List<Sessao> listaSessoes = new ArrayList<Sessao>();
        try {
            String sql = "SELECT * FROM " + tabela + "";
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Sessao sessao = buildObject(resultado);
                listaSessoes.add(sessao);
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSessoes;
    }

    @Override
    public boolean temSessao() {
        try {
            String sql = "SELECT count(*) as total FROM " + tabela + "";
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                int total = resultado.getInt("total");
                return (total > 0);
            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void setAllFields(PreparedStatement comando, Sessao sessao) throws SQLException {
        // idsala,idfilme,horario,expiracao
        comando.setInt(1, sessao.getSala().getNumero());
        comando.setInt(2, sessao.getFilme().getId());
        comando.setTimestamp(3, new java.sql.Timestamp(sessao.getHorario().getTime()));
        comando.setTimestamp(4, new java.sql.Timestamp(sessao.getExpiracao().getTime()));
    }

    private Sessao buildObject(ResultSet resultado) throws SQLException {
        return new Sessao(
                resultado.getInt("idsessao"),
                new SalaDaoMysql().buscarPorId(resultado.getInt("idsala")),
                new FilmeDaoMysql().buscarPorId(resultado.getInt("idfilme")),
                new Date(resultado.getTimestamp("horario").getTime()),
                new Date(resultado.getTimestamp("expiracao").getTime())
        );
    }

}
