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
public class VendasMenu {

    public static final int OP_CADASTRO = 1;
    public static final int OP_LISTA = 2;
    public static final int OP_LISTA_POR_FILME = 3;
    public static final int OP_LISTA_POR_DIA = 4;
    public static final int OP_SAIR = 0;

    public static String getOptions() {
        return "Cadastro de Venda: 1\n"
                + "Lista de Vendas: 2\n"
                + "Lista de Vendas por Filme: 3\n"
                + "Lista de Vendas por Dia: 4\n"
                + "Sair: 0\n";
    }
}
