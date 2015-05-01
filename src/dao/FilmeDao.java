package dao;

import java.util.List;
import model.Filme;

/**
 *
 * @author niltonkummer
 * Interface do model Filme
 */
public interface FilmeDao {
    public void inserir(Filme filme);
    public boolean deletar(Filme filme);
    public boolean atualizar(Filme filme);
    public Filme buscarPorId(int id);
    public List<Filme> buscarPorNome(String nome);
    public List<Filme> listar();
}
