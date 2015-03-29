package model;

/**
 *
 * @author niltonkummer
 */
public class Filme {

    private static int codigoGerado = 0;
    private int codigo;
    private String nome;
    private String genero;
    private String sinopse;
    private int faixaEtaria;
    private long duracao;

    public Filme(int codigo, String nome, String genero, String sinopse, int duracao, int faixaEtaria) {
        this.codigo = codigo;// generateCodigo();
        this.nome = nome;
        this.genero = genero;
        this.sinopse = sinopse;
        this.duracao = duracao;
        this.faixaEtaria = faixaEtaria;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @return the sinopse
     */
    public String getSinopse() {
        return sinopse;
    }

    /**
     * @return the duracao
     */
    public long getDuracao() {
        return duracao;
    }

    public long getDuracaoInMiliseconds() {
        return duracao * 60 * 1000;
    }

    /**
     * @return the faixaEtaria
     */
    public int getFaixaEtaria() {
        return faixaEtaria;
    }
}
