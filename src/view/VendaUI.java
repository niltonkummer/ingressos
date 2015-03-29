/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import model.Ingresso;
import model.Sessao;
import model.SessaoDia;
import repository.Sessoes;
import repository.Vendas;
import utils.DateUtil;
import utils.InputParse;
import utils.table.ColumnTable;
import utils.table.RowTable;
import utils.table.TableBuilder;
import view.menu.VendasMenu;

/**
 *
 * @author niltonkummer
 */
public class VendaUI {

    private Vendas vendas;
    private Sessoes sessoes;

    public VendaUI(Vendas vendas, Sessoes sessoes) {
        this.vendas = vendas;
        this.sessoes = sessoes;
    }

    public void run() {
        int opcao = 0;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(VendasMenu.getOptions()));
            } catch (Exception e) {
                opcao = VendasMenu.OP_SAIR;
            };

            switch (opcao) {
                case VendasMenu.OP_CADASTRO:
                    // Cadastra a venda
                    cadastrarVenda();
                    break;
                case VendasMenu.OP_LISTA:
                    // Listar sessoes
                    listarVendas();
                    break;
                case VendasMenu.OP_LISTA_POR_DIA:
                    listarVendasPorDia();
                    break;
                 case VendasMenu.OP_LISTA_POR_FILME:
                    listarVendasPorFilme();
                    break;   
                    
                case VendasMenu.OP_SAIR:
                    System.out.println("Volta para o menu principal");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != VendasMenu.OP_SAIR);
    }

    public void cadastrarVenda() {

        // Mostra os filmes, as salas e os horarios:
        Sessao sessao = buscaSessao();
        if (InputParse.isNull(sessao)) {
            return;
        }
        // Escolha do dia
        Date data = buscaDatas(sessao);
        if (InputParse.isNull(data)) {
            return;
        }
        
        if (!this.vendas.addVenda(new Ingresso(sessao, data))){
            JOptionPane.showMessageDialog(null, "Sessão Esgotada!");
        }
        //Mostra todos os pacientes existentes no repositório de pacientes. Notem que o PacienteUI tem uma função exclusiva para mostrar na tela.
        /*
         new PacienteUI(listaPacientes).mostrarPacientes();
         String rg = Console.scanString("Escolha o RG do paciente: ");
         Paciente paciente = listaPacientes.buscarPaciente(rg);

         //Relaciona o prontuário:
         String prontuario = Console.scanString("Escreva o prontuario: ");

         //Instancia a consulta (com o horário atual e sem medicamentos)
         Consulta consulta = new Consulta(paciente, prontuario);

         //Chama o menu receituario para cadastrar os medicamentos na consulta.
         new ReceituarioUI(consulta, listaMedicamentos).executar();

         //Tarefa do aluno: caso permita o modo de edição, a finalização não deve ser efetuada nesse método
         consulta.finalizar();

         //Adiciona consulta no repositório
         listaConsultas.addConsulta(consulta);
         */
    }

    private Sessao buscaSessao() {
        List<Sessoes> list = new SessaoUI(sessoes).getSessoesIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha uma sessao:\n" + new SessaoUI(sessoes).getSessoesTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Venda cancelada.");
            return null;
        }
        return sessoes.getSessaoById(Integer.parseInt(option));
    }

    private Date buscaDatas(Sessao sessao) {
        List<String> dates = montaDatas(sessao);
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha um dia:\n", "",
                JOptionPane.QUESTION_MESSAGE, null,
                dates.toArray(),
                dates.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Venda cancelada.");
            return null;
        }
        Date data = null;
        try {
            data = DateUtil.stringToDate(option);
        } catch (Exception e) {
        };
        return data;
    }

    private List<String> montaDatas(Sessao sessao) {
        // Venda permitida até o fim da sessao
        ArrayList<String> dates = new ArrayList<>();
        Date data = new Date();
        long diff = (long) (sessao.getExpiracao().getTime() - data.getTime()) / 1000;
        long tDias = diff / 60 / 60 / 24;
        for (int t = 1; t <= tDias; t++) {
            Date datat = new Date();
            datat.setTime(data.getTime() + 1000 * 60 * 60 * 24 * t);
            dates.add(DateUtil.dateToString("dd/MM/yyyy", datat));
        }
        return dates;
    }
    
    private void listarVendas() {
        if (vendas.hasIngresso()) {
            JOptionPane.showMessageDialog(null, "Sem vendas cadastradas");
            return;
        }
        JOptionPane.showMessageDialog(null, getVendasTable());
    }
    
    private void listarVendasPorDia() {
        if (vendas.hasIngresso()) {
            JOptionPane.showMessageDialog(null, "Sem vendas cadastradas");
            return;
        }
        List<String> dates = getVendasPorDia();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha um dia:\n", "",
                JOptionPane.QUESTION_MESSAGE, null,
                dates.toArray(),
                dates.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Consulta cancelada.");
            return;
        }
        Date data = null;
        try {
            data = DateUtil.stringToDate(option);
        } catch (Exception e) {
        };
        RowTable header = getHeader();
        JOptionPane.showMessageDialog(null,new TableBuilder(getVendasPorDiaTable(data)).buildTable(header));
    }
    
    private void listarVendasPorFilme() {
        if (vendas.hasIngresso()) {
            JOptionPane.showMessageDialog(null, "Sem vendas cadastradas");
            return;
        }
        List<String> filmes = getVendasPorFilme();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha um Filme:\n"+new FilmesUI(vendas.getFilmes()).getFilmesTable(),"",
                JOptionPane.QUESTION_MESSAGE, null,
                filmes.toArray(),
                filmes.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Consulta cancelada.");
            return;
        }
        int codigo = Integer.parseInt(option);
        RowTable header = getHeader();
        JOptionPane.showMessageDialog(null,new TableBuilder(getVendasPorFilmeTable(codigo)).buildTable(header));
    }
    
    
    public List getVendasIds() {
        ArrayList<String> rows = new ArrayList<>();
        for (SessaoDia sessaoDia : vendas.getIngressos()) {
            String row = Integer.toString(sessaoDia.hashCode());
            rows.add(row);
        }
        return rows;
    }
    public List getVendasPorDia() {
        ArrayList<String> rows = new ArrayList<>();
        for (String date : vendas.getDatas()) {
            String row = date;
            rows.add(row);
        }
        return rows;
    }
    
    public List getVendasPorDiaTable(Date data) {
        ArrayList<RowTable> rows = new ArrayList<>();
        for (SessaoDia sessaoDia : vendas.getIngressosPorDia(data)) {
            RowTable row = getRow(sessaoDia);
            rows.add(row);
        }
        return rows;
    }
    
     public List getVendasPorFilme() {
        ArrayList<String> rows = new ArrayList<>();
        for (String date : vendas.getFilmesIds()) {
            String row = date;
            rows.add(row);
        }
        return rows;
    }
    
    public List getVendasPorFilmeTable(int codigo) {
        ArrayList<RowTable> rows = new ArrayList<>();
        for (SessaoDia sessaoDia : vendas.getIngressosPorFilme(codigo)) {
            RowTable row = getRow(sessaoDia);
            rows.add(row);
        }
        return rows;
    }
    

    public String getVendasTable() {
        RowTable header = getHeader();
        ArrayList<RowTable> rows = new ArrayList<>();
        for (SessaoDia sessaoDia : vendas.getIngressos()) {
            RowTable row = getRow(sessaoDia);
            rows.add(row);
        }
        return new TableBuilder(rows).buildTable(header);
    }

    private RowTable getHeader() {
        RowTable header = new RowTable();
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Filme"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Sala"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Data"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Horário"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Faixa Etária"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Lugares"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Total Vendido"));
        return header;
    }

    private RowTable getRow(SessaoDia sessaoDia) {
        RowTable row = new RowTable();
        row.append(new ColumnTable(sessaoDia.getSessao().getFilme().getNome()));
        row.append(new ColumnTable(Integer.toString(sessaoDia.getSessao().getSala().getNumero())));
        row.append(new ColumnTable(DateUtil.dateToString("dd/MM/yyyy", sessaoDia.getData())));
        row.append(new ColumnTable(DateUtil.dateToString("HH:mm", sessaoDia.getSessao().getHorario())));
        row.append(new ColumnTable(Integer.toString(sessaoDia.getSessao().getFilme().getFaixaEtaria())));
        row.append(new ColumnTable(Integer.toString(sessaoDia.getSessao().getSala().getCapacidade())));
        row.append(new ColumnTable(Integer.toString(sessaoDia.getTotal())));
        return row;
    }
}
