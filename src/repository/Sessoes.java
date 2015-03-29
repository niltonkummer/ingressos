/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Sessao;

/**
 *
 * @author niltonkummer
 */
public class Sessoes {

    private List<Sessao> sessoes;

    public Sessoes() {
        sessoes = new ArrayList<Sessao>();
    }

    public boolean addSessao(Sessao sessao) {
        return (this.sessoes.add(sessao));
    }

    public List<Sessao> getListaSessoes() {
        return sessoes;
    }
    
    public Sessao getSessaoById(int codigo) {
         for (Sessao sessao : sessoes) {
            if (sessao.getCodigo() == codigo) {
                return sessao;
            }
        }
        return null;
    }

    public boolean isOcupado(int numero, Date horario, long duracao) {
        for (Sessao sessao : sessoes) {
            // Se sala e horario são iguais
            if (sessao.getSala().getNumero() == numero
                    && intervalTime(sessao.getFilme().getDuracaoInMiliseconds(), duracao, sessao.getHorario(),
                            horario)) {
                return true;
            }
        }
        return false;
    }

    private boolean intervalTime(long duration1, long duration2, Date h1, Date h2) {
        long a = h1.getTime();
        long b = h1.getTime() + duration1;
        long c = h2.getTime();
        long d = h2.getTime() + duration2;

        return (c >= a && c <= b)
                || (d >= a && d <= b)
                || (a >= c && a <= d)
                || (b >= c && b <= d);
    }

}
