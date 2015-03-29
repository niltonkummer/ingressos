package view;

import view.menu.MainMenu;
import javax.swing.JOptionPane;
import repository.Filmes;
import repository.Salas;
import repository.Sessoes;
import repository.Vendas;

/**
 *
 * @author niltonkummer
 */
public class MainUI {

    private Filmes listFilmes;
    private Salas listSalas;
    private Sessoes listSessoes;
    private Vendas listVendas;

    public MainUI() {
        listFilmes = new Filmes();
        listSalas = new Salas();
        listSessoes = new Sessoes();
        listVendas = new Vendas();
        new SessaoUI(listSessoes, listSalas, listFilmes).createSessao();
    }

    public void run() {
        int opcao = 0;
        do {

            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(MainMenu.getOptions()));
            } catch (Exception e) {
                opcao = MainMenu.OP_SAIR;
            };

            switch (opcao) {
                case MainMenu.OP_FILMES:
                    // FILMES
                    new FilmesUI(listFilmes).run();
                    break;
                case MainMenu.OP_SALAS:
                    new SalasUI(listSalas).run();
                    break;
                case MainMenu.OP_SESSOES:
                    new SessaoUI(listSessoes, listSalas, listFilmes).run();
                    break;
                case MainMenu.OP_VENDAS:
                    new VendaUI(listVendas, listSessoes).run();
                    break;
                case MainMenu.OP_SAIR:
                    System.out.println("Aplicação finalizada!!!");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != MainMenu.OP_SAIR);
    }
}
