/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FilmeDaoMysql;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Filme;
import view.menu.FilmesMenu;
import view.menu.MainMenu;
import repository.Filmes;
import utils.InputParse;
import utils.table.ColumnTable;
import utils.table.RowTable;
import utils.table.TableBuilder;
import view.menu.GlobalMenu;

/**
 *
 * @author niltonkummer
 */
public class FilmesUI {

    // private Filmes filmes;
    public FilmesUI(Filmes filmes) {
        // this.filmes = filmes;
    }

    public void run() {
        int opcao = 0;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(FilmesMenu.getOptions()));
            } catch (Exception e) {
                System.out.println("Opção inválida..");
                opcao = -1;
            };

            switch (opcao) {
                case FilmesMenu.OP_CADASTRO:
                    // FILMES
                    cadastrarFilme();
                    break;
                case FilmesMenu.OP_CONSULTA:
                    String query = JOptionPane.showInputDialog("Faça uma busca:");
                    consultarFilmes(query);
                    // 
                    break;
                case FilmesMenu.OP_LISTA:
                    // Listar filmes
                    listarFilmes();
                    break;
                case FilmesMenu.OP_SAIR:
                    System.out.println("Volta para o menu principal");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != MainMenu.OP_SAIR);
    }

    private void cadastrarFilme() {
        FilmeDaoMysql filmes = new FilmeDaoMysql();
        String[] camposLabel = new String[]{FilmesMenu.LBL_CODIGO,
            FilmesMenu.LBL_NOME,
            FilmesMenu.LBL_GENERO,
            FilmesMenu.LBL_SINOPSE,
            FilmesMenu.LBL_DURACAO,
            FilmesMenu.LBL_FAIXA_ETARIA,};
        ArrayList<String> camposValue = new ArrayList<String>();
        for (String item : camposLabel) {
            String label = item + ":";
            String option = JOptionPane.showInputDialog(label);
            if (item.equals(FilmesMenu.LBL_CODIGO)
                    || item.equals(FilmesMenu.LBL_DURACAO)) {
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

                if (item.equals(FilmesMenu.LBL_CODIGO)) {

                    if (filmes.buscarPorId(Integer.parseInt(option)) != null) {
                        JOptionPane.showMessageDialog(null, FilmesMenu.MSG_FILME_CADASTRADO);
                        return;
                    }
                }
            }
            if (InputParse.isNull(option)) {
                JOptionPane.showMessageDialog(null, "Cadastro de filme cancelado");
                return;
            }
            camposValue.add(option);
        }
        filmes.inserir(new Filme(Integer.parseInt(camposValue.get(0)), camposValue.get(1),
                camposValue.get(2), camposValue.get(3), Integer.parseInt(camposValue.get(4)),
                Integer.parseInt(camposValue.get(5))));
        JOptionPane.showMessageDialog(null, "Filme cadastrado!");
    }

    private void consultarFilmes(String substring) {
        FilmeDaoMysql filmes = new FilmeDaoMysql();
        List<Filme> lista = filmes.listar();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem filmes cadastrados");
            return;
        }
        RowTable header = getHeader();
        ArrayList<RowTable> rows = new ArrayList<>();
        for (Filme filme : lista) {
            if (filme.getNome().contains(substring)) {
                RowTable row = getRow(filme);
                rows.add(row);
            }
        }
        if (rows.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum Filme encontrado");
        } else {
            JOptionPane.showMessageDialog(null, new TableBuilder(rows).buildTable(header));
        }
    }

    private void listarFilmes() {
        FilmeDaoMysql filmes = new FilmeDaoMysql();
        List<Filme> lista = filmes.listar();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem filmes cadastrados");
            return;
        }
        JOptionPane.showMessageDialog(null, getFilmesTable());
    }

    public List getFilmesIds() {
        FilmeDaoMysql filmes = new FilmeDaoMysql();
        List<Filme> lista = filmes.listar();
        ArrayList<String> rows = new ArrayList<>();
        for (Filme filme : lista) {
            String row = Integer.toString(filme.getId());
            rows.add(row);
        }

        return rows;
    }

    public String getFilmesTable() {
        FilmeDaoMysql filmes = new FilmeDaoMysql();
        List<Filme> lista = filmes.listar();
        RowTable header = getHeader();
        ArrayList<RowTable> rows = new ArrayList<>();
        for (Filme filme : lista) {
            RowTable row = getRow(filme);
            rows.add(row);
        }

        return new TableBuilder(rows).buildTable(header);
    }

    private RowTable getHeader() {
        RowTable header = new RowTable();
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Código"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Nome"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Genero"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Sinopse"));
        header.append(new ColumnTable(ColumnTable.ALIGN_LEFT, "Duração"));
        return header;
    }

    private RowTable getRow(Filme filme) {
        RowTable row = new RowTable();
        row.append(new ColumnTable(Integer.toString(filme.getId())));
        row.append(new ColumnTable(filme.getNome()));
        row.append(new ColumnTable(filme.getGenero()));
        row.append(new ColumnTable(filme.getSinopse()));
        row.append(new ColumnTable(Integer.toString((int) filme.getDuracao()) + " minutos"));
        return row;
    }
}
