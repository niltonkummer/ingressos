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
public class SessaoDia {

    private Sessao sessao;
    private Date data;
    private int total;

    public SessaoDia(Sessao sessao, Date data) {
        this.sessao = sessao;
        this.data = data;
    }

    private boolean isLotado() {
        return this.total >= sessao.getSala().getCapacidade();
    }

    public boolean addVenda() {
        if (!isLotado()) {
            this.total++;
            return true;
        }
        return false;
    }

    /**
     * @return the sessao
     */
    public Sessao getSessao() {
        return sessao;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }
}
