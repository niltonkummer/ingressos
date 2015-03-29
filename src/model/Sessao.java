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

    private static int codigo_ai = 1;
    private int codigo;
    private Sala sala;
    private Filme filme;
    private Date horario;
    private Date expiracao;

    public Sessao(Sala sala, Filme filme, Date horario, Date expira) {
        this.codigo = generateCodigo();
        this.sala = sala;
        this.filme = filme;
        this.horario = horario;
        this.expiracao = expira;
    }

    private int generateCodigo() {
        return codigo_ai++;
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
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @return the expira
     */
    public Date getExpiracao() {
        return expiracao;
    }
}
