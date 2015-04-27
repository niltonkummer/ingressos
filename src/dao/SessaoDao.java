package dao;

import java.util.List;
import model.Sessao;

/**
 *
 * @author niltonkummer
 */
public interface SessaoDao {
    public void inserir(Sessao sessao);
    public void deletar(Sessao sessao);
    public boolean atualizar(Sessao sessao);
    public boolean temSessao();
    public Sessao buscarPorId(int id);
    public List<Sessao> buscarPorNome(String nome);
    public List<Sessao> listar();
}
