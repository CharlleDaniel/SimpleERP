package com.simpleerp.entidades;

/**
 * Created by Thainan on 27/06/15.
 */
public class Producao_Produto {

    private long idProducao;
    private long idProduto;

    public Producao_Produto() {
        this.idProducao=0;
        this.idProduto=0;
    }

    public Producao_Produto(long idProducao, long idProduto) {
        this.idProducao = idProducao;
        this.idProduto = idProduto;

    }

    public long getIdProducao() {
        return idProducao;
    }

    public void setIdProducao(long idProducao) {
        this.idProducao = idProducao;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }
}
