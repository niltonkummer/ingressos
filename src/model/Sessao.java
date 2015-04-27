/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author niltonkummer
 */
public class Sessao {

    private int id;
    private Sala sala;
    private Filme filme;
    private Date horario;
    private Date expiracao;

    public Sessao(Sala sala, Filme filme, Date horario, Date expira) {
        this.sala = sala;
        this.filme = filme;
        this.horario = horario;
        this.expiracao = expira;
    }
    
    public Sessao(int id,Sala sala, Filme filme, Date horario, Date expira) {
        this.id = id;
        this.sala = sala;
        this.filme = filme;
        this.horario = horario;
        this.expiracao = expira;
    }


    /**
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * @return the filme
     */
    public Filme getFilme() {
        return filme;
    }

    /**
     * @return the horario
     */
    public Date getHorario() {
        return horario;
    }
    /**
     * @return the expira
     */
    public Date getExpiracao() {
        return expiracao;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
