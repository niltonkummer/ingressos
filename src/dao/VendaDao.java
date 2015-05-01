/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import model.Venda;
import model.Ingresso;

/**
 *
 * @author niltonkummer
 * Interface Dao Venda
 */
public interface VendaDao {
    public void inserir(Ingresso ingresso);
    public void deletar(Ingresso ingresso);
    public Venda buscarPorSessaoData(int id, Date data);
    
    public boolean temVendas();
    public int totalVendaPorSessaoData(int id, Date data);
    public List<Venda> listar();
    public List<String> listarDias();
    public List<Venda> buscarVendasPorDia(Date data);
    public List<Venda> buscarVendasPorFilme(int id);
}
