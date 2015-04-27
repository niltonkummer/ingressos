package dao;

import java.util.List;
import model.Filme;

/**
 *
 * @author niltonkummer
 */
public interface FilmeDao {
    public void inserir(Filme filme);
    public boolean deletar(Filme filme);
    public void atualizar(Filme filme);
    public Filme buscarPorId(int id);
    public List<Filme> buscarPorNome(String nome);
    public List<Filme> listar();
}
