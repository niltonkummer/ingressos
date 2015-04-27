/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menu;

/**
 *
 * @author niltonkummer
 */
public class SessoesMenu {

    public static final int OP_CADASTRO = 1;
    public static final int OP_LISTA = 2;
    public static final int OP_ATUALIZAR = 3;
    public static final int OP_SAIR = 0;

    public static String getOptions() {
        return "Cadastro de Sessao: 1\n"
                + "Lista de Sessões: 2\n"
                + "Atualizar de Sessões: 3\n"
                + "Sair: 0\n";
    }
}
