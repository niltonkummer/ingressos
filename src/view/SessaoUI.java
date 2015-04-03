/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JOptionPane;
import model.Sala;
import model.Sessao;
import model.Filme;
import repository.Sessoes;
import repository.Salas;
import repository.Filmes;
import utils.table.ColumnTable;
import utils.table.RowTable;
import utils.table.TableBuilder;
import utils.DateUtil;
import utils.InputParse;
import view.menu.SalasMenu;
import view.menu.SessoesMenu;

/**
 *
 * @author niltonkummer
 */
public class SessaoUI {

    private Sessoes sessoes;
    private Salas salas;
    private Filmes filmes;

    public SessaoUI(Sessoes sessoes, Salas salas, Filmes filmes) {
        this.sessoes = sessoes;
        this.salas = salas;
        this.filmes = filmes;
    }
    
    public void createSessao() {
        Date hour = null; 
        try{hour = DateUtil.stringToHour("10:10");}catch(Exception e ){};
        Date date = null; 
        try{date = DateUtil.stringToDate("31/04/2015");}catch(Exception e ){};
        this.sessoes.addSessao(
                new Sessao(salas.getSalaById(1), 
                filmes.getFilmeById(100), 
                hour,
                date));
        
        try{hour = DateUtil.stringToHour("14:10");}catch(Exception e ){};
        try{date = DateUtil.stringToDate("31/04/2015");}catch(Exception e ){};
        this.sessoes.addSessao(
                new Sessao(salas.getSalaById(1), 
                filmes.getFilmeById(200), 
                hour,
                date));
        
        try{hour = DateUtil.stringToHour("13:10");}catch(Exception e ){};
        try{date = DateUtil.stringToDate("31/04/2015");}catch(Exception e ){};
        this.sessoes.addSessao(
                new Sessao(salas.getSalaById(2), 
                filmes.getFilmeById(100), 
                hour,
                date));
        
        try{hour = DateUtil.stringToHour("15:10");}catch(Exception e ){};
        try{date = DateUtil.stringToDate("31/04/2015");}catch(Exception e ){};
        this.sessoes.addSessao(
                new Sessao(salas.getSalaById(3), 
                filmes.getFilmeById(300), 
                hour,
                date));
        
    }

    public SessaoUI(Sessoes sessoes) {
        this.sessoes = sessoes;
    }

    public void run() {
        int opcao = 0;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(SessoesMenu.getOptions()));
            } catch (Exception e) {
                opcao = SessoesMenu.OP_SAIR;
            };

            switch (opcao) {
                case SessoesMenu.OP_CADASTRO:
                    // FILMES
                    cadastrarSessao();
                    break;
                case SessoesMenu.OP_LISTA:
                    // Listar sessoes
                    listarSessoes();
                    break;
                //case SessoesMenu.OP_LISTA_POR_SALA:
                // Listar sessoes
                //listarSessoes();
                // break;

                case SessoesMenu.OP_SAIR:
                    System.out.println("Volta para o menu principal");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != SalasMenu.OP_SAIR);
    }

    private void cadastrarSessao() {

        if (!mainValidate()) {
            return;
        }
        // Mostra as salas do cinema
        Sala sala = buscaSala();
        if (InputParse.isNull(sala)) {
            return;
        }
        // Codigo do filme
        Filme filme = buscaFilme();
        if (InputParse.isNull(filme)) {
            return;
        }
        boolean isOcupado = false;
        // Horario
        Date data = null;
        do {
            data = getHorario();
            if (InputParse.isNull(data)) {
                return;
            }

            isOcupado = sessoes.isOcupado(sala.getNumero(), data, filme.getDuracaoInMiliseconds());
            if (isOcupado) {
                JOptionPane.showMessageDialog(null, "Sessão invade horário de outra! Cadastre novamente.");
            }
        } while (isOcupado);

        // Data de expiração
        Date dataExp = getData();
        if (InputParse.isNull(dataExp)) {
            return;
        }

        Sessao sessao = new Sessao(sala, filme, data, dataExp);
        sessoes.addSessao(sessao);
        JOptionPane.showMessageDialog(null, "Sessão cadastrada!");
    }

    private boolean mainValidate() {
        if (salas.getListaSalas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem salas disponíveis");
            return false;
        }

        if (filmes.getListaFilmes().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem filmes disponíveis");
            return false;
        }

        return true;
    }

    private Sala buscaSala() {
        Sala sala = null;
        List<Sala> list = new SalasUI(salas).getSalasIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha uma sala:\n" + new SalasUI(salas).getSalasTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Cadastro de sessão cancelado.");
            return null;
        }
        return salas.getSalaById(Integer.parseInt(option));
    }

    private Filme buscaFilme() {
        List<Filme> list = new FilmesUI(filmes).getFilmesIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha um filme:\n" + new FilmesUI(filmes).getFilmesTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Busca por filme interrompida.");
            return null;
        }
        return filmes.getFilmeById(Integer.parseInt(option));
    }

    private Date getHorario() {
        String option = null;
        Date dt = null;
        while (InputParse.isNull(dt)) {
            option = JOptionPane.showInputDialog("Horario: HH:MM");
            if (InputParse.isNull(option)) {
                return null;
            }
            try {
                dt = DateUtil.stringToHour(option);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Horário inválido");
            }
        }
        return dt;
    }

    private Date getData() {
        String option = null;
        Date dt = null;
        while (InputParse.isNull(dt)) {
            option = JOptionPane.showInputDialog("Expira: DD/MM/AAAA");
            if (InputParse.isNull(option)) {
                return null;
            }
            try {
                dt = DateUtil.stringToDate(option);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data inválida");
            }
            if (dt.before(new Date())) {
                JOptionPane.showMessageDialog(null, "Data menor que o dia atual");
                dt = null;
            }
        }
        return dt;
    }

    private void listarSessoes() {
        if (sessoes.getListaSessoes().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem sessões cadastradas");
            return;
        }
        JOptionPane.showMessageDialog(null, getSessoesTable());
    }

    public List getSessoesIds() {
        ArrayList<String> rows = new ArrayList<>();
        for (Sessao sessao : sessoes.getListaSessoes()) {
            String row = Integer.toString(sessao.getCodigo());
            rows.add(row);
        }
        return rows;
    }

    public String getSessoesTable() {
        RowTable header = getHeader();
        ArrayList<RowTable> rows = new ArrayList<>();
        for (Sessao sessao : sessoes.getListaSessoes()) {
            RowTable row = getRow(sessao);
            rows.add(row);
        }
        return new TableBuilder(rows).buildTable(header);
    }

    private RowTable getHeader() {
        RowTable header = new RowTable();
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Filme"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Sala"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Horário Inicio"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Horário Fim"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Expira"));
        return header;
    }

    private RowTable getRow(Sessao sessao) {
        RowTable row = new RowTable();
        row.append(new ColumnTable(sessao.getFilme().getNome()));
        row.append(new ColumnTable(Integer.toString(sessao.getSala().getNumero())));
        row.append(new ColumnTable(DateUtil.dateToString("HH:mm", sessao.getHorario())));
        row.append(new ColumnTable(DateUtil.dateToString("HH:mm",
                new Date((long) sessao.getHorario().getTime()
                        + sessao.getFilme().getDuracaoInMiliseconds()))));
        row.append(new ColumnTable(DateUtil.dateToString("dd/MM/yyyy",
                new Date((long) sessao.getExpiracao().getTime()))));
        return row;
    }
}
