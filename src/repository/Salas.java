/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import model.Sala;

/**
 *
 * @author niltonkummer
 */
public class Salas {
    private List<Sala> salas;

    public Salas() {
        salas = new ArrayList<Sala>();
        addSala(new Sala(1, 150));
        addSala(new Sala(2, 150));
        addSala(new Sala(3, 250));
        addSala(new Sala(4, 200));
        
    }

    public boolean addSala(Sala sala) {
        return (salas.add(sala));
    }

    public List<Sala> getListaSalas() {
        return salas;
    }

    public boolean hasSala(int codigo) {
        for (Sala sala : salas) {
            if (sala.getNumero() == codigo) {
                return true;
            }
        }
        return false;
    }
    
    public Sala getSalaById(int codigo) {
         for (Sala sala : salas) {
            if (sala.getNumero() == codigo) {
                return sala;
            }
        }
        return null;
    }
}
