package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Venda;
import model.Ingresso;
import utils.DateUtil;

/**
 *
 * @author niltonkummer
 */
public class VendaDaoMysql extends DefaultDao implements VendaDao {

    private static final String idtabela = "idsessao";
    private static final String tabela = "venda";

    @Override
    public void inserir(Ingresso ingresso) {
        try {
            String sql = "INSERT INTO " + tabela + " "
                    + "(idsessao, data) VALUES(?,?)";
            conectar(sql);
            comando = conexao.prepareStatement(sql);

            setAllFields(comando, ingresso);

            comando.executeUpdate();
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deletar(Ingresso ingresso) {
        try {
            String sql = "DELETE FROM " + tabela + " WHERE " + idtabela + "=?";
            conectar(sql);
            comando.setInt(1, ingresso.getCodigo());
            comando.executeUpdate();
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Venda buscarPorSessaoData(int id, Date data) {
        Venda venda = null;
        try {
            String sql = "SELECT * FROM " + tabela + " WHERE " + idtabela + "=? AND data=?";
            conectar(sql);
            
            comando.setInt(1, id);
            comando.setDate(2, new java.sql.Date(data.getTime()));
            System.out.println(sql);
            
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                
                venda = buildObject(resultado);
                System.out.println("Item: "+venda);
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venda;
    }
    
    @Override
    public boolean temVendas(){
        try {
            String sql = "SELECT count(*) as total FROM " + tabela;
            conectar(sql);
            System.out.println(sql);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                int total = resultado.getInt("total");
                return (total > 0); 
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    @Override
    public int totalVendaPorSessaoData(int id, Date data){
         int total = 0;
        try {
            String sql = "SELECT count(*) as total FROM " + tabela + " WHERE " + idtabela + "=? AND data=?";
            conectar(sql);
            comando.setInt(1, id);
            comando.setDate(2, new java.sql.Date(data.getTime()));
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                total = resultado.getInt("total");
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
        
    }

    @Override
    public List<Venda> listar() {
        List<Venda> listaVendas = new ArrayList<Venda>();
        try {
            String sql = "SELECT idsessao, data, count(*) as total FROM venda GROUP BY idsessao, data";
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Venda venda = buildObject(resultado);
                listaVendas.add(venda);
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVendas;
    }
    
    @Override
    public List<String> listarDias() {
        List<String> listaDias = new ArrayList<String>();
        try {
            String sql = "SELECT data FROM venda GROUP BY data";
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                String dataStr = DateUtil.dateToString("dd/MM/yyyy",resultado.getDate("data"));
                listaDias.add(dataStr);
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDias;
    }

    public List<Venda> buscarVendasPorDia(Date data){
        List<Venda> listaVendas = new ArrayList<Venda>();
        try {
            String sql = "SELECT idsessao, data, count(*) as total FROM venda WHERE data=? GROUP BY data";
            conectar(sql);
            comando.setDate(1, new java.sql.Date(data.getTime()));
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Venda venda = buildObject(resultado);
                listaVendas.add(venda);
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVendas;
        
    }
    
    public List<Venda> buscarVendasPorFilme(int id){
        List<Venda> listaVendas = new ArrayList<Venda>();
        try {
            String sql = "SELECT f.nome, s.idsessao, v.data,count(*) as total FROM venda v "
                    + "JOIN sessao s ON s.idsessao = v.idsessao "
                    + "JOIN filme f ON f.idfilme = s.idfilme "
                    + "WHERE s.idfilme=? group by v.data";
            conectar(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Venda venda = buildObject(resultado);
                listaVendas.add(venda);
            }
            fecharConexao();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DefaultDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVendas;
        
    }
    
    private void setAllFields(PreparedStatement comando, Ingresso ingresso) throws SQLException {
        comando.setInt(1, ingresso.getSessao().getId());
        comando.setDate(2, new java.sql.Date(ingresso.getData().getTime()));
    }

    private Venda buildObject(ResultSet resultado) throws SQLException {
        return new Venda(
                new SessaoDaoMysql().buscarPorId(resultado.getInt("idSessao")),
                new Date(resultado.getDate("data").getTime()),
                resultado.getInt("total")
        );
    }

   

}
