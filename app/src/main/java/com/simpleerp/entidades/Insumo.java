package com.simpleerp.entidades;

/**
 * Created by Thainan on 27/06/15.
 */
public class Insumo {

    private long id;
    private String nome;
    private float quantidade;
    private float custoUnidade;
    private String tipo;
    private boolean isAddList;


    public Insumo() {
        this.nome = null;
        this.quantidade =0;
        this.custoUnidade = 0;
        this.tipo=null;
        this.isAddList = false;
    }
    public Insumo(String nome, float quantidade, float custoUnidade, String tipo ) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.custoUnidade = custoUnidade;
        this.tipo=tipo;
        this.isAddList = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public float getCustoUnidade() {
        return custoUnidade;
    }

    public void setCustoUnidade(float custoUnidade) {
        this.custoUnidade = custoUnidade;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAddList() {
        return isAddList;
    }

    public void setIsAddList(boolean isAddList) {
        this.isAddList = isAddList;
    }
}
