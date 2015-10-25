package com.simpleerp.entidades;

/**
 * Created by Thainan on 27/06/15.
 */
public class Produto_Insumo {

    private float idProduto;
    private long idInsumo;

    public Produto_Insumo() {
        idProduto=0;
        idInsumo=0;
    }
    public Produto_Insumo(float idProduto, long idInsumo) {
        this.idProduto = idProduto;
        this.idInsumo = idInsumo;

    }

    public float getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(float idProduto) {
        this.idProduto = idProduto;
    }

    public long getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(long idInsumo) {
        this.idInsumo = idInsumo;
    }


}
