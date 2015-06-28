package com.simpleerp.entidades;

/**
 * Created by Thainan on 27/06/15.
 */
public class Produto {

    private float id;
    private String nome;
    private float preco;

    public float getId() {
        return id;
    }

    public void setId(float id) {
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
}
