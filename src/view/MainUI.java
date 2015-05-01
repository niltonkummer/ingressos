package view;

import dao.FilmeDaoMysql;
import dao.SalaDaoMysql;
import view.menu.MainMenu;
import javax.swing.JOptionPane;

/**
 * Menu principal da aplicação
 * @author niltonkummer
 */
public class MainUI {

    public MainUI() {}

    /**
     * Inicia a funcionalidade de menu
     */
    public void run() {
        int opcao = 0;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(MainMenu.getOptions()));
            } catch (Exception e) {
                opcao = -1;
            };

            switch (opcao) {
                case MainMenu.OP_FILMES:
                    // FILMES
                    new FilmesUI().run();
                    break;
                case MainMenu.OP_SALAS:
                    new SalasUI().run();
                    break;
                case MainMenu.OP_SESSOES:
                    new SessaoUI().run();
                    break;
                case MainMenu.OP_VENDAS:
                    new VendaUI().run();
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
