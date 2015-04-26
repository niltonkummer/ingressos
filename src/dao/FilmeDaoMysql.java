package dao;

import conn.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Filme;

/**
 *
 * @author niltonkummer
 */
public class FilmeDaoMysql implements FilmeDao {

    private Connection conexao;
    private PreparedStatement comando;
    private static final String tabela = "filme";

    @Override
    public void inserir(Filme filme) {
        try {
            String sql = "INSERT INTO " + tabela + " "
                    + "(nome,genero,sinopse,faixa_etaria,duracao) VALUES(?,?,?,?,?)";
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
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deletar(Filme filme) {
        try {
            String sql = "DELETE FROM " + tabela + " WHERE id=?";
            conectar(sql);
            comando.setInt(1, filme.getId());
            comando.executeUpdate();
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void atualizar(Filme filme) {
        try {
            String sql = "UPDATE " + tabela + " SET nome=?, genero=?, sinopse=?, "
                    + "faixa_etaria=?, duracao=? WHERE id=?";
            conectar(sql);
            setAllFields(comando, filme);
            comando.setInt(5, filme.getId());
            comando.executeUpdate();
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Filme buscarPorId(int id) {
        Filme filme = null;
        try {
            String sql = "SELECT * FROM " + tabela + " WHERE idfilme=?";
            conectar(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                filme = buildFilme(resultado);

            }
            fecharConexao();

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
                Filme filme = buildFilme(resultado);
                listaFilmes.add(filme);
            }
            fecharConexao();

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
                Filme filme = buildFilme(resultado);
                listaFilmes.add(filme);
            }
            fecharConexao();

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

    private Filme buildFilme(ResultSet resultado) throws SQLException {
        return new Filme(
                resultado.getInt("idfilme"),
                resultado.getString("nome"),
                resultado.getString("genero"),
                resultado.getString("sinopse"),
                resultado.getInt("duracao"),
                resultado.getInt("faixa_etaria")
        );
    }

    private void conectar(String sql) throws ClassNotFoundException, SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql);
    }

    private void fecharConexao() throws SQLException {
        comando.close();
        conexao.close();
    }

}
