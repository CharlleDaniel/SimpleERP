package com.simpleerp.entidades;

/**
 * Created by Thainan on 27/06/15.
 */
public class Produto {

    private long id;
    private String nome;
    private float preco;
    private String periodo;
    private float qtdVendas;
    private boolean isAddList;

    public Produto(String nome, float preco) {
        this.nome = nome;
        this.preco = preco;
        this.isAddList=false;
    }
    public Produto() {
        this.nome = "";
        this.preco = 0;
        this.isAddList=false;
    }

    public boolean isAddList() {
        return isAddList;
    }

    public void setIsAddList(boolean isAddList) {
        this.isAddList = isAddList;
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

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public float getQtdVendas() {
        return qtdVendas;
    }

    public void setQtdVendas(float qtdVendas) {
        this.qtdVendas = qtdVendas;
    }
}
