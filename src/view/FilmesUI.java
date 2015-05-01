package view;

import dao.FilmeDaoMysql;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Filme;
import view.menu.FilmesMenu;
import view.menu.MainMenu;
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
    private FilmeDaoMysql filmes ;
    
    public FilmesUI() {
       filmes = new FilmeDaoMysql();
    }
    /**
     * Menu inicial de FilmesUI
     */
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
                case FilmesMenu.OP_ATUALIZAR:
                    // FILMES
                    atualizarFilme();
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
                case FilmesMenu.OP_DELETAR:
                    // Listar filmes
                    deletarFilme();
                    break;   
                 
                case FilmesMenu.OP_SAIR:
                    System.out.println("Volta para o menu principal");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != MainMenu.OP_SAIR);
    }
    /**
     * Cadastro de objetos Filme
     */
    private void cadastrarFilme() {
        
        String[] camposLabel = new String[]{
            FilmesMenu.LBL_NOME,
            FilmesMenu.LBL_GENERO,
            FilmesMenu.LBL_SINOPSE,
            FilmesMenu.LBL_DURACAO,
            FilmesMenu.LBL_FAIXA_ETARIA,};
        ArrayList<String> camposValue = new ArrayList<String>();
        for (String item : camposLabel) {
            String label = item + ":";
            String option = JOptionPane.showInputDialog(label);
            if (item.equals(FilmesMenu.LBL_DURACAO)
                    || item.equals(FilmesMenu.LBL_FAIXA_ETARIA)) {
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
            if (InputParse.isNull(option)) {
                JOptionPane.showMessageDialog(null, "Cadastro de filme cancelado");
                return;
            }
            camposValue.add(option);
        }
        filmes.inserir(new Filme(camposValue.get(0),
                camposValue.get(1), camposValue.get(2), Integer.parseInt(camposValue.get(3)),
                Integer.parseInt(camposValue.get(4))));
        JOptionPane.showMessageDialog(null, "Filme cadastrado!");
    }
    
    /**
     * Atualização de objetos Filme
     */
    private void atualizarFilme() {
        List<Filme> lista = filmes.listar();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem filmes cadastrados");
            return;
        }
        List<String> list = getFilmesIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha um filme para atulizar:\n" + new FilmesUI().getFilmesTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Atulização de filme interrompida.");
            return;
        }
        Filme filme = filmes.buscarPorId(Integer.parseInt(option));
        
        String[] camposLabel = new String[]{
            FilmesMenu.LBL_NOME,
            FilmesMenu.LBL_GENERO,
            FilmesMenu.LBL_SINOPSE,
            FilmesMenu.LBL_FAIXA_ETARIA};
        ArrayList<String> camposValue = new ArrayList<String>();
        for (String item : camposLabel) {
            String label = item + ":";
            option = JOptionPane.showInputDialog(label);
            if (item.equals(FilmesMenu.LBL_FAIXA_ETARIA)) {
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
            if (InputParse.isNull(option)) {
                JOptionPane.showMessageDialog(null, "Cadastro de filme cancelado");
                return;
            }
            camposValue.add(option);
        }
        
        filme.setNome(camposValue.get(0));
        filme.setGenero(camposValue.get(1));
        filme.setSinopse(camposValue.get(2));
        filme.setFaixaEtaria(Integer.parseInt(camposValue.get(3)));
        
        if (!filmes.atualizar(filme)) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atulizar o filme");
        }
        JOptionPane.showMessageDialog(null, "Filme atualizado!");
    }
    /**
     * Exclusão de objetos Filme
     */
    private void deletarFilme() {
        List<Filme> lista = filmes.listar();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem filmes cadastrados");
            return;
        }
        List<String> list = getFilmesIds();
        String option = (String) JOptionPane.showInputDialog(null,
                "Escolha um filme para deletar:\n" + new FilmesUI().getFilmesTable(), "",
                JOptionPane.QUESTION_MESSAGE, null,
                list.toArray(),
                list.get(0));
        if (InputParse.isNull(option)) {
            JOptionPane.showMessageDialog(null, "Deleção de filme interrompida.");
            return;
        }
        
        if (filmes.deletar(filmes.buscarPorId(Integer.parseInt(option)))) {
            JOptionPane.showMessageDialog(null, "Filme removido do catálogo.");
        }
    }
    
    /**
     * Busca de objetos Filme
     */
    private void consultarFilmes(String substring) {
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

    /**
     * Listagem de objetos Filme
     */
    public void listarFilmes() {
        List<Filme> lista = filmes.listar();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem filmes cadastrados");
            return;
        }
        JOptionPane.showMessageDialog(null, getFilmesTable());
    }

    /**
     * Busca por id de objetos Filme
     */
    public List<String> getFilmesIds() {
        List<Filme> lista = filmes.listar();
        ArrayList<String> rows = new ArrayList<>();
        for (Filme filme : lista) {
            String row = Integer.toString(filme.getId());
            rows.add(row);
        }

        return rows;
    }

    /**
     * Exclusão de objetos Filme
     */
    public String getFilmesTable() {
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
