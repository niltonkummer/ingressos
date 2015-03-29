package view.menu;


import java.lang.reflect.Array;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author niltonkummer
 */
public class MainMenu {

    public static final int OP_FILMES = 1;
    public static final int OP_SALAS = 2;
    public static final int OP_SESSOES = 3;
    public static final int OP_VENDAS = 4;
    public static final int OP_RELATORIO = 5;
    public static final int OP_SAIR = 0;

    public static String getOptions() {

        return "Menu de Filmes: 1\n"
                + "Menu de Salas: 2\n"
                + "Menu de Sessoes: 3\n"
                + "Menu de Vendas: 4\n"
                + "Menu de Relatorios: 5\n"
                + "Sair: 0\n";
    }
}
