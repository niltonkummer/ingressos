/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import repository.Sessoes;

/**
 *
 * @author niltonkummer
 */
public class Ingresso {
    
    private static int numero_serie;
    private int codigo;
    private Sessao sessao;
    private Date data;

    // o controle da 
    // quantidadeAssentos. 

    public Ingresso(Sessao sessao, Date data) {
        this.codigo = generateCodigo();
        this.sessao = sessao;
        this.data = data;
    }

     private int generateCodigo() {
        return numero_serie++;
    }
    
    /**
     * @return the sessao
     */
    public Sessao getSessao() {
        return sessao;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }
    
    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

}
