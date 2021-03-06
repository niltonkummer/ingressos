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
import model.Filme;

/**
 *
 * @author niltonkummer Implementação do Dao Filme para Mysql
 */
public class FilmeDaoMysql extends DefaultDao implements FilmeDao {

    private static final String idtabela = "idfilme";
    private static final String tabela = "filme";

    @Override
    public void inserir(Filme filme) {
        try {
            String sql = "INSERT INTO " + tabela + " "
                    + "(nome,genero,sinopse,duracao,faixa_etaria) VALUES(?,?,?,?,?)";
            conectar(sql);
            comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            setAllFields(comando, filme);

            comando.executeUpdate();
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                filme.setId(resultado.getInt(1));
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean deletar(Filme filme) {
        try {
            String sql = "DELETE FROM " + tabela + " WHERE " + idtabela + "=?";
            conectar(sql);
            comando.setInt(1, filme.getId());
            comando.executeUpdate();
            fecharConexao();
            return true;
        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (MySQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Filme não pode ser removido pois já existe sessão vinculada");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean atualizar(Filme filme) {
        try {
            String sql = "UPDATE " + tabela + " SET nome=?, genero=?, sinopse=?, "
                    + " faixa_etaria=? WHERE " + idtabela + "=?";
            conectar(sql);
            comando.setString(1, filme.getNome());
            comando.setString(2, filme.getGenero());
            comando.setString(3, filme.getSinopse());
            comando.setInt(4, filme.getFaixaEtaria());
            comando.setInt(5, filme.getId());
            comando.executeUpdate();
            fecharConexao();
            return true;
        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Filme buscarPorId(int id) {
        Filme filme = null;
        try {
            String sql = "SELECT * FROM " + tabela + " WHERE " + idtabela + "=?";
            conectar(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                filme = buildObject(resultado);
            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filme;
    }

    @Override
    public List<Filme> buscarPorNome(String nome) {
        List<Filme> listaFilmes = new ArrayList<Filme>();
        try {
            String sql = "SELECT * FROM " + tabela + " WHERE nome LIKE ?";
            conectar(sql);
            comando.setString(1, "%" + nome + "%");
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Filme filme = buildObject(resultado);
                listaFilmes.add(filme);
            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaFilmes;
    }

    @Override
    public List<Filme> listar() {
        List<Filme> listaFilmes = new ArrayList<Filme>();
        try {
            String sql = "SELECT * FROM " + tabela + "";
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Filme filme = buildObject(resultado);
                listaFilmes.add(filme);
            }
            fecharConexao();

        } catch (CommunicationsException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um problema ao conectar com o banco de dados");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaFilmes;
    }

    private void setAllFields(PreparedStatement comando, Filme filme) throws SQLException {
        comando.setString(1, filme.getNome());
        comando.setString(2, filme.getGenero());
        comando.setString(3, filme.getSinopse());
        comando.setLong(4, filme.getDuracao());
        comando.setInt(5, filme.getFaixaEtaria());
    }

    private Filme buildObject(ResultSet resultado) throws SQLException {
        return new Filme(
                resultado.getInt("idfilme"),
                resultado.getString("nome"),
                resultado.getString("genero"),
                resultado.getString("sinopse"),
                resultado.getInt("duracao"),
                resultado.getInt("faixa_etaria")
        );
    }

}
