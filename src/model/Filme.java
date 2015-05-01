package model;

/**
 *
 * @author niltonkummer
 */
public class Filme {

    private int id;
    private String nome;
    private String genero;
    private String sinopse;
    private int faixaEtaria;
    private long duracao;
    /**
     * Instancia um novo objeto Filme com o id
     * @param id
     * @param nome
     * @param genero
     * @param sinopse
     * @param duracao
     * @param faixaEtaria 
     */
    public Filme(int id, String nome, String genero, String sinopse, int duracao, int faixaEtaria) {
        this.id = id;// generateCodigo();
        this.nome = nome;
        this.genero = genero;
        this.sinopse = sinopse;
        this.duracao = duracao;
        this.faixaEtaria = faixaEtaria;
    }

    /**
     * Instancia um novo objeto Filme sem o id
     * @param nome
     * @param genero
     * @param sinopse
     * @param duracao
     * @param faixaEtaria 
     */
    public Filme(String nome, String genero, String sinopse, int duracao, int faixaEtaria) {
        this.nome = nome;
        this.genero = genero;
        this.sinopse = sinopse;
        this.duracao = duracao;
        this.faixaEtaria = faixaEtaria;
    }
    
    /**
     * @return the codigo
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @param sinopse the sinopse to set
     */
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    /**
     * @param faixaEtaria the faixaEtaria to set
     */
    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    
}
