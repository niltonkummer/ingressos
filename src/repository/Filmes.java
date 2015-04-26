/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import model.Filme;

/**
 *
 * @author niltonkummer
 */
public class Filmes {

    private List<Filme> filmes;

    public Filmes() {
        filmes = new ArrayList<Filme>();
        // Testes
        addFilme(new Filme(100, "Crepusculo", "Romance", "Uma menina vive um romance entre um vampiro e um lobo", 120, 14));
        addFilme(new Filme(200, "Harry Potter", "Romance", "Um garoto vê sua vida mudar completamente quando descobre que é um bruxo", 120, 10));
        addFilme(new Filme(300, "A origem", "Romance", "Os segredos de um sonho podem ser descobertos nesse filme", 240, 12));
        addFilme(new Filme(400, "Matrix", "Ação", "Um homem sai da rotina tomando um decisão que vai mudar o seu mundo", 190, 16));
    }

    public boolean addFilme(Filme filme) {
        return (filmes.add(filme));
    }

    public List<Filme> getListaFilmes() {
        return filmes;
    }

    public boolean hasFilme(int codigo) {
        for (Filme filme : filmes) {
            if (filme.getId()== codigo) {
                return true;
            }
        }
        return false;
    }

    public Filme getFilmeById(int codigo) {
        for (Filme filme : filmes) {
            if (filme.getId()== codigo) {
                return filme;
            }
        }
        return null;
    }

    public List<Filme> buscarFilme(String substring) {
        List<Filme> listRetorno = new ArrayList<>();
        for (Filme filme : filmes) {
            if (filme.getNome().contains(substring)) {
                listRetorno.add(filme);
                //return filme;
            }
        }
        return listRetorno;
    }
    
    public boolean isEmpty() {
        return filmes.isEmpty();
    }
}
