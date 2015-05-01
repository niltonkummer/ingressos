/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import view.MainUI;

/**
 *
 *
 *
 * Tema 2: Venda de Cinema no Cinema Funcionalidades: 
 *- Cadastro de Filmes:
 *    anota-se para filme o código, nome do filme, gênero (String) e sinopse.
 *- Cadastro de Salas: 
 *    anota-se para sala o número da sala e a quantidade de assentos 
 *- Cadastro de Seção: relaciona-se em cada seção a sala (objeto), um
 *    horário (só hora, mas utilizar Date) e o filme (objeto). 
 *- Venda de Cinema: realiza o registro de uma venda, relacionando a seção e realizando
 *    o controle da quantidade de assentos. 
 *- Relatórios de venda: por filme, horários, etc.
 *
 * @author niltonkummer
 */
public class Cinema {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO Relatórios de venda: por filme, horários, etc. 
        // TODO Tratamento para valores inteiros.
        new MainUI().run();
    }

}
