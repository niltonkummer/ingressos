/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import model.Filme;
import model.Ingresso;
import model.SessaoDia;
import utils.DateUtil;
import utils.InputParse;
import view.FilmesUI;

/**
 *
 * @author niltonkummer
 */
public class Vendas {

    //private List<Ingresso> ingressos;
    private HashMap<String, List<SessaoDia>> ingressos;

    public Vendas() {
        this.ingressos = new HashMap<String, List<SessaoDia>>();
    }

    public boolean addVenda(Ingresso ingresso) {
        String dateString = DateUtil.dateToString("dd/MM/yyyy", ingresso.getData());
        if (InputParse.isNull(ingressos.get(dateString))) {
            ingressos.put(dateString, new ArrayList<SessaoDia>());
        }

        List<SessaoDia> list = ingressos.get(dateString);
        SessaoDia sd = getSessaoDia(list, ingresso.getSessao().getCodigo());
        if (InputParse.isNull(sd)) {
            sd = new SessaoDia(ingresso.getSessao(), ingresso.getData());
            list.add(sd);
        }
        
        return sd.addVenda();
    }

    private SessaoDia getSessaoDia(List<SessaoDia> list, int codigo) {
        for (SessaoDia sd : list) {
            if (sd.getSessao().getCodigo() == codigo) {
                return sd;
            }
        }
        return null;
    }

    public Set<String> getDatas() {
        return ingressos.keySet();
    }

    public Filmes getFilmes() {
        Filmes filmes = new Filmes();
        for (List<SessaoDia> list : ingressos.values()) {
            for (SessaoDia item : list){
                if (!filmes.hasFilme(item.getSessao().getFilme().getCodigo())){
                    filmes.addFilme(item.getSessao().getFilme());
                }
            }
        }
        
        return filmes;
    }
    
    public List<String> getFilmesIds() {
        ArrayList<String> codigos = new ArrayList<>();
        for (List<SessaoDia> list : ingressos.values()) {
            for (SessaoDia item : list){
                if (!codigos.contains(item.getSessao().getFilme().getCodigo())){
                    codigos.add(Integer.toString(item.getSessao().getFilme().getCodigo()));
                }
            }
        }
        
        return codigos;
    }
    
    public List<SessaoDia> getIngressos() {
        List<SessaoDia> sessoesDia = new ArrayList<>();
        for (List<SessaoDia> sd : ingressos.values()) {
            for (SessaoDia sessaoDia : sd) {
                sessoesDia.add(sessaoDia);
            }
        }

        return sessoesDia;
    }

    public List<SessaoDia> getIngressosPorDia(Date date) {
        List<SessaoDia> sessoesDia = new ArrayList<>();
        for (String dateString : ingressos.keySet()) {
            if (dateString.equals(DateUtil.dateToString("dd/MM/yyyy", date))) {
                for (SessaoDia sessaoDia : ingressos.get(dateString)) {
                    sessoesDia.add(sessaoDia);
                }
            }
        }

        return sessoesDia;
    }

    public List<SessaoDia> getIngressosPorFilme(int codigo) {
        List<SessaoDia> sessoesDia = new ArrayList<>();
        for (List<SessaoDia> sd : ingressos.values()) {
            for (SessaoDia sessaoDia : sd) {
                if (sessaoDia.getSessao().getFilme().getCodigo() == codigo) {
                    sessoesDia.add(sessaoDia);
                }
            }
        }

        return sessoesDia;
    }

    public boolean hasIngresso() {
        return ingressos.isEmpty();
    }
}
