package com.simpleerp.entidades;

/**
 * Created by Thainan on 27/06/15.
 */
public class Producao {

    private long id;
    private String nome;

    public Producao(String nome, int id){ this.nome = nome; this.id = id; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String name){
        this.nome = name;
    }
}
