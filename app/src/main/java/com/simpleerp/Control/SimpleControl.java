package com.simpleerp.Control;

import android.content.Context;

import com.simpleerp.database.BD;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Produto;
import com.simpleerp.entidades.Usuario;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CharlleNot on 12/10/2015.
 */
public class SimpleControl {
    private List<Insumo> insumoList;
    private List<Produto>produtoList;
    private List<Producao>producaoList;
    private List<Insumo> tempInsumo;
    private Usuario usuarioLogado;
    private BD bd ;

    public SimpleControl(Context context) {

        bd= new BD(context);
        this.insumoList=bd.buscarInsumos();
        this.produtoList = bd.buscarProdutos();
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
        String tempNome=upCaseAllFristChar(insumo.getNome());
        for (Insumo i : insumoList){
            if(i.getNome().equalsIgnoreCase(tempNome)){
                test=true;
            }
        }
        if(test==false){

            insumo.setNome(tempNome);
            bd.inserirInsumo(insumo);
            List<Insumo>temp = bd.buscarInsumos();
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
        String tempNome = upCaseAllFristChar(produto.getNome());
        for(Produto p: produtoList) {
            if (p.getNome().equalsIgnoreCase(tempNome)) {
                test = true;
            }
        }
        if(test==false) {

            produto.setNome(tempNome);
            bd.inserirProduto(produto);
            List<Produto> temp = bd.buscarProdutos();
            for (Produto p : temp) {
                if (p.getNome().equalsIgnoreCase(produto.getNome())) {
                    this.produtoList.add(p);
                }
            }
        }else{
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
    public String upCaseAllFristChar(String txt){
        String temp="";
        Pattern p = Pattern.compile("[a-zA-Zà-úÀ-Ú]+");
        Matcher m = p.matcher(txt);
        boolean test = m.find();
        while (test==true){
            String parte = m.group();
            temp=temp+(parte.substring(0,1).toUpperCase())+(parte.substring(1, parte.length()))+" ";
            test = m.find();
        }


        return temp;
    }
    public void addInsumoProduto(List<Insumo> list) {
       if(tempInsumo!=null) {
           tempInsumo.addAll(list);
       }else{
           tempInsumo=list;
       }

    }
    public void removeInsumoProduto(){
        tempInsumo= null;

    }
    public List<Insumo> getInsumoProduto(){
        if(tempInsumo==null){
            tempInsumo= new LinkedList<Insumo>();
        }
        return tempInsumo;
    }
    public void removeInsumoProduto(Insumo insumo){
        tempInsumo.remove(insumo);

    }
    public void removeInsumoProduto(List <Insumo> insumo){
        for(Insumo i : insumo){
            tempInsumo.remove(i);
        }

    }
}
