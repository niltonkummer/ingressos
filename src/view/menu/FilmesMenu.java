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
public class FilmesMenu {

    public static final int OP_CADASTRO = 1;
    public static final int OP_CONSULTA = 2;
    public static final int OP_LISTA = 3;
    public static final int OP_SAIR = 0;

    public static final String MSG_FILME_CADASTRADO = "Este filme já esta cadastrado";
    
    public static final String LBL_CODIGO = "Código";
    public static final String LBL_NOME = "Nome";
    public static final String LBL_GENERO = "Genero";
    public static final String LBL_SINOPSE = "Sinopse";
    public static final String LBL_DURACAO = "Duração";
    public static final String LBL_FAIXA_ETARIA = "Faixa etária";

    public static String getOptions() {
        return "Cadastro de Filmes: 1\n"
                + "Consulta de Filmes: 2\n"
                + "Lista de Filmes: 3\n"
                + "Sair: 0\n";
    }
}
