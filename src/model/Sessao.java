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

    /**
     * Instancia um novo objeto Sessao sem o id
     * @param sala
     * @param filme
     * @param horario
     * @param expira 
     */
    public Sessao(Sala sala, Filme filme, Date horario, Date expira) {
        this.sala = sala;
        this.filme = filme;
        this.horario = horario;
        this.expiracao = expira;
    }
    /**
     * Instancia um novo objeto Filme com o id
     * @param id
     * @param sala
     * @param filme
     * @param horario
     * @param expira 
     */
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

    /**
     * @param sala the sala to set
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * @param filme the filme to set
     */
    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(Date horario) {
        this.horario = horario;
    }

    /**
     * @param expiracao the expiracao to set
     */
    public void setExpiracao(Date expiracao) {
        this.expiracao = expiracao;
    }
}
