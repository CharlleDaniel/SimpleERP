package com.simpleerp.Control;

import android.content.Context;


import com.simpleerp.database.BD;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Produto;
import com.simpleerp.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CharlleNot on 12/10/2015.
 */
public class SimpleControl {
    private List<Insumo> insumoList;
    private List<Produto>produtoList;
    private List<Producao>producaoList;
    private Usuario usuarioLogado;
    private BD bd ;

    public SimpleControl() {

        this.insumoList = new ArrayList<Insumo>();
        this.produtoList = new ArrayList<Produto>();
        this.producaoList = new ArrayList<Producao>();

    }

    public void login(Usuario usuarioLogado){
        boolean test= false;
        List<Usuario>temp= bd.buscarUsuario();
        if (temp.size() > 0) {
            for(Usuario u : temp) {
                if (u.getNome().equalsIgnoreCase(usuarioLogado.getNome())) {
                    this.usuarioLogado = u;
                    u.setIsLog("true");
                    bd.atualizarUsuario(u);
                    test = true;
                }
            }
        }
        if(test!=true) {
            usuarioLogado.setIsLog("true");
            bd.inserirUsuario(usuarioLogado);
            temp = bd.buscarUsuario();
            for (Usuario u : temp) {
                if (u.getNome().equalsIgnoreCase(usuarioLogado.getNome())) {
                    this.usuarioLogado = u;
                }
            }
        }


    }

    public Usuario getUsuarioLogado(){
        List<Usuario>temp= bd.buscarUsuario();
        if (temp.size() > 0) {
            for(Usuario u : temp) {
                if(u.isLog().equalsIgnoreCase("true")){
                    this.usuarioLogado = u;
                }
            }
        }
        return this.usuarioLogado;
    }
    public void setBd(Context context){
        bd= new BD(context);
    }

    public List<Insumo> getInsumoList() {
        return insumoList;
    }

    public void addInsumo(Insumo insumo) {
        this.insumoList.add(insumo);
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void addProduto(Produto produto) {
        this.produtoList.add(produto);
    }

    public List<Producao> getProducaoList() {
        return producaoList;
    }

    public void addProducao(Producao producao) {
        this.producaoList.add(producao);
    }
}
