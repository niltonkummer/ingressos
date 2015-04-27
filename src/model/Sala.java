/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author niltonkummer
 */
public class Sala {

    private int numero;
    private int capacidade;

    public Sala(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
    }
    
    public Sala(int capacidade) {
        this.capacidade = capacidade;
    }
    
    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @return the capacidade
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
}
