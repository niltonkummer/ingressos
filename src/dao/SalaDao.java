package dao;

import java.util.List;
import model.Sala;

/**
 *
 * @author niltonkummer
 * Interface Dao para Sala
 */
public interface SalaDao {
    public void inserir(Sala sala);
    public boolean deletar(Sala sala);
    public void atualizar(Sala sala);
    public Sala buscarPorId(int id);
    public List<Sala> listar();
}

