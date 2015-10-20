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

    public SimpleControl(Context context) {

        bd= new BD(context);
        this.insumoList=bd.buscarInsumo();


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

    public List<Insumo> getInsumoList() {
        return insumoList;
    }

    public void addInsumo(Insumo insumo) throws Exception {
        boolean test= false;
        for (Insumo i : insumoList){
            if(i.getNome().equalsIgnoreCase(insumo.getNome())){
                test=true;
            }
        }
        if(test==false){

            bd.inserirInsumo(insumo);
            List<Insumo>temp = bd.buscarInsumo();
            for (Insumo i : temp) {
                if (i.getNome().equalsIgnoreCase(insumo.getNome())) {
                    this.insumoList.add(i);
                }
            }
        }else{
            throw new Exception ("Já existe este insumo.");
        }

    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void addProduto(Produto produto) throws Exception{
        boolean test = false;
        for(Produto p: produtoList) {
            if (p.getNome().equalsIgnoreCase(produto.getNome())) {
                test = true;
            }
        }
        if(test == false){
            this.produtoList.add(produto);
        }
        else{
            throw new Exception("Já existe este produto.");
        }

    }

    public List<Producao> getProducaoList() {
        return producaoList;
    }

    public void addProducao(Producao producao) throws Exception{
        boolean test = false;
        for(Producao pro: producaoList) {
            if (pro.getNome().equalsIgnoreCase(producao.getNome())) {
                test = true;
            }
        }
        if(test == false){
            this.producaoList.add(producao);
        }
        else{
            throw new Exception("Já existe esta produção.");
        }
    }
    public void setAllInsumos(boolean bool){
        for(Insumo i: insumoList){
            i.setIsAddList(bool);
        }
    }
}
