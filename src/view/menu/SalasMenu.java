package view.menu;

/**
 *
 * @author niltonkummer
 */
public class SalasMenu {

    public static final int OP_CADASTRO = 1;
    public static final int OP_LISTA = 2;
    public static final int OP_DELETAR = 3;
    public static final int OP_SAIR = 0;
    
    public static final String MSG_SALA_EXISTE = "Este número de sala já existe";
    public static final String MSG_SALA_CADASTRADA = "Sala cadastrada!";
    public static final String MSG_SALA_CANCELADA = "Cadastro de sala cancelada";
    
    public static final String LBL_NUMERO = "Número";
    public static final String LBL_CAPACIDADE = "Capacidade";
    
    public static String getOptions() {
        return "Cadastro de Sala: 1\n"
                + "Lista de Salas: 2\n"
                + "Deletar de Sala: 3\n"
                + "Sair: 0\n";
    }
}
