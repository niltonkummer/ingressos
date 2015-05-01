/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FilmeDaoMysql;
import dao.SalaDaoMysql;
import dao.SessaoDaoMysql;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JOptionPane;
import model.Sala;
import model.Sessao;
import model.Filme;
import utils.table.ColumnTable;
import utils.table.RowTable;
import utils.table.TableBuilder;
import utils.DateUtil;
import utils.InputParse;
import view.menu.SalasMenu;
import view.menu.SessoesMenu;

/**
 * Contém as informações sobre o Filme e Sala que pertencem ao objeto Sessao 
 * @author niltonkummer
 */
public class SessaoUI {

    private SessaoDaoMysql sessoes;
    private SalaDaoMysql salas;
    private FilmeDaoMysql filmes;

    public SessaoUI() {
        this.sessoes = new SessaoDaoMysql();
        this.salas = new SalaDaoMysql();
        this.filmes = new FilmeDaoMysql();
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
                case SessoesMenu.OP_ATUALIZAR:
                    // FILMES
                    atualizarSessao();
                    break;
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

            isOcupado = isOcupado(sala.getNumero(), data, filme.getDuracaoInMiliseconds());
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
        sessoes.inserir(sessao);
        JOptionPane.showMessageDialog(null, "Sessão cadastrada!");
    }

    private void atualizarSessao() {
        // TODO Validar se existem sessoes
        List<Sessao> list = getSessoesIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha uma sessao:\n" + new SessaoUI().getSessoesTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Atualização cancelada.");
            return;
        }
        Sessao sessao = sessoes.buscarPorId(Integer.parseInt(option));
    
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

            isOcupado = isOcupado(sala.getNumero(), data, filme.getDuracaoInMiliseconds());
            if (isOcupado) {
                JOptionPane.showMessageDialog(null, "Sessão invade horário de outra! Cadastre novamente.");
            }
        } while (isOcupado);

        // Data de expiração
        Date dataExp = getData();
        if (InputParse.isNull(dataExp)) {
            return;
        }
        sessao.setFilme(filme);
        sessao.setSala(sala);
        sessao.setHorario(data);
        sessao.setExpiracao(dataExp);
        if (!sessoes.atualizar(sessao)) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao salvar a sessão!");
        }
        JOptionPane.showMessageDialog(null, "Sessão cadastrada!");
        
    }
    
    private boolean mainValidate() {
        if (salas.listar().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem salas disponíveis");
            return false;
        }

        if (filmes.listar().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem filmes disponíveis");
            return false;
        }

        return true;
    }

    private Sala buscaSala() {
        Sala sala = null;
        List<Sala> list = new SalasUI().getSalasIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha uma sala:\n" + new SalasUI().getSalasTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Cadastro de sessão cancelado.");
            return null;
        }
        return salas.buscarPorId(Integer.parseInt(option));
    }

    private Filme buscaFilme() {
        List<String> list = new FilmesUI().getFilmesIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha um filme:\n" + new FilmesUI().getFilmesTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Busca por filme interrompida.");
            return null;
        }
        return filmes.buscarPorId(Integer.parseInt(option));
    }

    private Date getHorario() {
        String option = null;
        Date dt = null;
        while (InputParse.isNull(dt)) {
            option = JOptionPane.showInputDialog("Horario: HH:MM");
            if (InputParse.isNull(option)) {
                return null;
            }
            dt = new Date();
            if (!DateUtil.stringToHour(option, dt)) {
                JOptionPane.showMessageDialog(null, "Horário inválido");
                dt = null;
                continue;
            }
        }
        return dt;
    }

    private Date getData() {
        String option = null;
        Date dt = null;
        while (InputParse.isNull(dt)) {
            option = JOptionPane.showInputDialog("Expira em: DD/MM/AAAA");
            if (InputParse.isNull(option)) {
                return null;
            }
            dt = new Date();
            if (!DateUtil.stringToDate(option, dt)) {
                JOptionPane.showMessageDialog(null, "Data inválida");
                dt = null;
                continue;
            }
            if (dt.before(new Date())) {
                JOptionPane.showMessageDialog(null, "Data menor que o dia atual");
                dt = null;
            }
        }
        return dt;
    }

    private void listarSessoes() {
        if (sessoes.listar().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem sessões cadastradas");
            return;
        }
        JOptionPane.showMessageDialog(null, getSessoesTable());
    }

    public boolean isOcupado(int numero, Date horario, long duracao) {
        for (Sessao sessao : sessoes.listar()) {
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

    public List getSessoesIds() {
        ArrayList<String> rows = new ArrayList<>();
        for (Sessao sessao : sessoes.listar()) {
            String row = Integer.toString(sessao.getId());
            rows.add(row);
        }
        return rows;
    }

    public String getSessoesTable() {
        RowTable header = getHeader();
        ArrayList<RowTable> rows = new ArrayList<>();
        for (Sessao sessao : sessoes.listar()) {
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
