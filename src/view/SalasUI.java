/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Sala;
import repository.Salas;
import utils.table.ColumnTable;
import utils.table.RowTable;
import utils.table.TableBuilder;
import view.menu.SalasMenu;

/**
 *
 * @author niltonkummer
 */
public class SalasUI {
    private Salas salas;

    public SalasUI(Salas salas) {
        this.salas = salas;
    }
    
    public void run() {
        int opcao = 0;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(SalasMenu.getOptions()));
            } catch (Exception e) {
                opcao = SalasMenu.OP_SAIR;
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
                case SalasMenu.OP_SAIR:
                    System.out.println("Volta para o menu principal");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != SalasMenu.OP_SAIR);
    }

    private void cadastraSala() {
        String[] camposLabel = new String[]{SalasMenu.LBL_NUMERO, SalasMenu.LBL_CAPACIDADE};
        ArrayList<String> camposValue = new ArrayList<String>();
        for (String item : camposLabel) {
            String option = JOptionPane.showInputDialog(item + ":");
            if (item.equals(camposLabel[0])) {
                int codigo = Integer.parseInt(option);
                if (salas.hasSala(codigo)) {
                    JOptionPane.showMessageDialog(null, SalasMenu.MSG_SALA_EXISTE);
                    return;
                }
            }
            if (option == null) {
                JOptionPane.showMessageDialog(null, SalasMenu.MSG_SALA_CANCELADA);
                return;
            }
            camposValue.add(option);
        }
        salas.addSala(new Sala(Integer.parseInt(camposValue.get(0)), Integer.parseInt(camposValue.get(1))));
        JOptionPane.showMessageDialog(null, SalasMenu.MSG_SALA_CADASTRADA);
    }


    private void listarSalas() {
        JOptionPane.showMessageDialog(null, getSalasTable());
    }
    
    public List getSalasIds() {
        ArrayList<String> rows = new ArrayList<>();
        for (Sala sala : salas.getListaSalas()) {
            String row = Integer.toString(sala.getNumero());
            rows.add(row);
        }
        return rows;
    }
    
    public String getSalasTable() {
        RowTable header = getHeader();
        ArrayList<RowTable> rows = new ArrayList<>();
        for (Sala sala : salas.getListaSalas()) {
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
