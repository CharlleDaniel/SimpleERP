package com.simpleerp.entidades;

/**
 * Created by Thainan on 27/06/15.
 */
public class Usuario {
    private String nome;
    private String email;
    private String isLog;
    private long id;
    public Usuario(){
        isLog= "false";
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String isLog() {
        return isLog;
    }
    public void setIsLog(String isLog) {
        this.isLog = isLog;
    }
}
