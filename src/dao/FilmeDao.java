package dao;

import java.util.List;
import model.Filme;

/**
 *
 * @author niltonkummer
 */
public interface FilmeDao {
    public void inserir(Filme paciente);
    public void deletar(Filme paciente);
    public void atualizar(Filme paciente);
    public Filme buscarPorId(int id);
    public List<Filme> buscarPorNome(String nome);
    public List<Filme> listar();
}
