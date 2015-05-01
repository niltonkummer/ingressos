package view;

import dao.SalaDaoMysql;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Sala;
import utils.InputParse;
import utils.table.ColumnTable;
import utils.table.RowTable;
import utils.table.TableBuilder;
import view.menu.GlobalMenu;
import view.menu.SalasMenu;

/**
 *
 * @author niltonkummer
 */
public class SalasUI {

    private SalaDaoMysql salas;

    public SalasUI() {
        salas = new SalaDaoMysql();
    }

    /**
     * Inicia a funcionalidade de menu
     */
    public void run() {
        int opcao = 0;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(SalasMenu.getOptions()));
            } catch (Exception e) {
                opcao = -1;
            };

            switch (opcao) {
                case SalasMenu.OP_CADASTRO:
                    // FILMES
                    cadastraSala();
                    break;
                case SalasMenu.OP_LISTA:
                    // Listar filmes
                    listarSalas();
                    break;
                case SalasMenu.OP_DELETAR:
                    // Listar filmes
                    deletarSala();
                    break;
                case SalasMenu.OP_SAIR:
                    System.out.println("Volta para o menu principal");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != SalasMenu.OP_SAIR);
    }

    /**
     * Cadastro de objetos Sala
     */
    private void cadastraSala() {
        String[] camposLabel = new String[]{SalasMenu.LBL_CAPACIDADE};
        ArrayList<String> camposValue = new ArrayList<String>();
        for (String item : camposLabel) {
            String label = item + ":";
            String option = JOptionPane.showInputDialog(item + ":");
            if (item.equals(SalasMenu.LBL_NUMERO) || item.equals(SalasMenu.LBL_CAPACIDADE)) {
                boolean valid = false;
                do {
                    if (InputParse.isNull(option)) {
                        return;
                    }
                    valid = InputParse.validateInt(option);
                    if (!valid) {
                        JOptionPane.showMessageDialog(null, GlobalMenu.MSG_VALOR_INVALIDO + " " + item);
                        option = JOptionPane.showInputDialog(label);
                    }
                } while (!valid);
            }
            if (option == null) {
                JOptionPane.showMessageDialog(null, SalasMenu.MSG_SALA_CANCELADA);
                return;
            }
            camposValue.add(option);
        }
        salas.inserir(new Sala(Integer.parseInt(camposValue.get(0))));
        JOptionPane.showMessageDialog(null, SalasMenu.MSG_SALA_CADASTRADA);
    }

    private void listarSalas() {
        if (salas.listar().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem salas cadastradas.");
            return;
        }
        JOptionPane.showMessageDialog(null, getSalasTable());
    }

    /**
     * Exclusão de objetos Sala
     */
    private void deletarSala() {
        List<Sala> lista = salas.listar();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem salas cadastradas");
            return;
        }
        List<String> list = getSalasIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha uma sala para deletar:\n" + new SalasUI().getSalasTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Deleção de sala interrompida.");
            return;
        }

        if (salas.deletar(salas.buscarPorId(Integer.parseInt(option)))) {
            JOptionPane.showMessageDialog(null, "Sala removida.");
        }
    }

    /**
     * Busca de objetos Sala por id
     */
    public List getSalasIds() {

        ArrayList<String> rows = new ArrayList<>();
        for (Sala sala : salas.listar()) {
            String row = Integer.toString(sala.getNumero());
            rows.add(row);
        }
        return rows;
    }

    public String getSalasTable() {
        RowTable header = getHeader();
        ArrayList<RowTable> rows = new ArrayList<>();
        for (Sala sala : salas.listar()) {
            RowTable row = getRow(sala);
            rows.add(row);
        }
        return new TableBuilder(rows).buildTable(header);
    }

    private RowTable getHeader() {
        RowTable header = new RowTable();
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, SalasMenu.LBL_NUMERO));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, SalasMenu.LBL_CAPACIDADE));
        return header;
    }

    private RowTable getRow(Sala sala) {
        RowTable row = new RowTable();
        row.append(new ColumnTable(Integer.toString(sala.getNumero())));
        row.append(new ColumnTable(Integer.toString(sala.getCapacidade())));
        return row;
    }
}
