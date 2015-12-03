package com.simpleerp.Control;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.simpleerp.database.BD;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Produto;
import com.simpleerp.entidades.Usuario;

import java.io.File;
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
    private List <Produto> tempProdutos;

    public SimpleControl(Context context) {

        bd= new BD(context);
        this.insumoList=bd.buscarInsumos();
        this.produtoList = bd.buscarProdutos();
        this.producaoList = bd.buscarProducoes();

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
        String tempNome=upCaseAllFirstChar(insumo.getNome());
        for (Insumo i : insumoList){
            if(i.getNome().equalsIgnoreCase(tempNome)){
                test=true;
                break;
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
        String tempNome = upCaseAllFirstChar(produto.getNome());
        for(Produto p: produtoList) {
            if (p.getNome().equalsIgnoreCase(tempNome)) {
                test = true;
                break;
            }
        }
        if(test==false) {

            produto.setNome(tempNome);
            bd.inserirProduto(produto);
            List<Produto> temp = bd.buscarProdutos();
            for (Produto p : temp) {
                if (p.getNome().equalsIgnoreCase(produto.getNome())) {
                    this.produtoList.add(p);
                    for(Insumo t: tempInsumo){
                        bd.inserirInsumoProduto(p,t);
                    }

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
        String tempNome = upCaseAllFirstChar(producao.getNome());
        for(Producao pro: producaoList) {
            if (pro.getNome().equalsIgnoreCase(tempNome)) {
                test = true;
                break;
            }
        }
        if(test == false){
            producao.setNome(tempNome);
            bd.inserirProducao(producao);
            List<Producao> temp = bd.buscarProducoes();
            for (Producao p : temp) {
                if (p.getNome().equalsIgnoreCase(producao.getNome())) {
                    this.producaoList.add(p);
                    for(Produto t: tempProdutos){
                        bd.inserirProdutoProducao(p, t);
                    }
                }
            }
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
    public void setAllInsumos(boolean bool, List<Insumo>list){
        for(Insumo i: insumoList){
            for(Insumo in : list){
                if(i.getId()==in.getId()){
                    i.setIsAddList(bool);
                }
            }

        }
    }
    public String upCaseAllFirstChar(String txt){
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

    // Insumo_Produto temp

    public void addInsumoProdutoTemp(List<Insumo> list) {
        if(tempInsumo!=null) {
            for(Insumo i : list){
                boolean teste = false;
                for(Insumo in :tempInsumo){
                    if(i.getId() == in .getId()){
                        teste = true;
                        break;
                    }
                }
                if(teste== false){
                    tempInsumo.add(i);
                }
            }

        }else{
            tempInsumo=list;
        }
        if(tempInsumo!=null){
            for(Insumo i :tempInsumo){
                i.setIsAddList(true);
                for(Insumo l : insumoList){
                    if(i.getId()==l.getId()){
                        l.setIsAddList(true);
                        break;
                    }
                }
            }
        }

    }
    public List<Insumo> getInsumoProdutoTemp(){
        if(tempInsumo==null){
            tempInsumo= new LinkedList<Insumo>();
        }
        return tempInsumo;
    }
    public void removeAllInsumosProdutoTemp(){

        tempInsumo.clear();
    }

    public void removeInsumoProdutoTemp(Insumo insumo){
        for( Insumo i : tempInsumo){
            if(i.getId()== insumo.getId()){
                i.setIsAddList(false);
                tempInsumo.remove(i);
                for(Insumo l : insumoList){
                    if(i.getId()==l.getId()){
                        l.setIsAddList(false);
                        break;
                    }
                }
                break;
            }

        }

    }
    public void removeInsumoProdutoTemp(List <Insumo> insumos){
        for(Insumo i : insumos){

            for(Insumo in :tempInsumo){
                if(i.getId()==in.getId()){
                    in.setIsAddList(false);
                    tempInsumo.remove(in);
                    break;
                }
            }
            for(Insumo l : insumoList){
                if(i.getId()==l.getId()){
                    l.setIsAddList(false);
                    break;
                }
            }
        }
    }

    public List<Insumo> getInsumosProduto(Produto p) throws Exception {
        try{
            List<Insumo> listTemp=bd.buscarInsumoProduto(p);
            if(listTemp==null){
                listTemp= new LinkedList<Insumo>();
            }
            return listTemp;
        }catch (Exception e){
            throw new Exception("Erro");
        }



    }


    //Produto_Produção
    public void addProdutoProducao(List<Produto> list){
        if(tempProdutos != null){
            this.tempProdutos.addAll(list);
        }
        else {
            this.tempProdutos = list;
        }
    }
    public void removeProdutoProducao(){
        tempProdutos= null;

    }
    public void removeProdutoProducao(List<Produto> list){
        for(Produto p: list){
            tempProdutos.remove(p);
        }
    }
    public void removeProdutoProducao(Produto produto){
        tempProdutos.remove(produto);

    }
    public List<Produto> getProdutoProducao(){

        if(tempProdutos==null){
            tempProdutos= new LinkedList<Produto>();
        }
        return tempProdutos;
    }

    public void setAllProdutos(boolean b) {
        for(Produto p : produtoList){
            p.setIsAddList(b);
        }
    }



    //Planilhas

    public File[] carregaArquivosDiretorioRaiz() throws Exception {
        File []f= new File[0];
        File [] retorno= new File[0];
        try {
            if((new File("/sdcard/SimpleERP/Planilhas")).exists()){
                f=(new File("/sdcard/SimpleERP/Planilhas/")).listFiles();

                if(f.length>0){
                    retorno= new File[f.length];
                    for (int k=0; k<f.length;k++){
                        if(f[k].getName().endsWith(".xlsx")){
                            retorno[k]=f[k];
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Não foi Possivel encontrar a pasta do simpleERP");
        }
        return retorno;
    }

    public Intent abrirPlanilha(File file){
        String type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        Intent it = new Intent();
        it.setAction(android.content.Intent.ACTION_VIEW);
        it.setDataAndType(Uri.fromFile(file), type);
        return it;
    }
    public void removeProduto(Produto p){
        produtoList.remove(p);
        bd.deletarProduto(p);
    }

    public void removeInsumo(Insumo i){
        insumoList.remove(i);
        bd.deletarInsumo(i);
    }

    public void removeProducao(Producao p){
        producaoList.remove(p);
        bd.deletarProducao(p);
    }
    public void alteraInsumo(Insumo i)throws Exception{
        try{
            bd.atualizarInsumo(i);
        }catch (Exception e){
            throw new Exception("Não foi possível salvar suas alterações.");
        }
    }
    public void alteraProduto(Produto p)throws Exception{
        try{
            List<Insumo> insumos=bd.buscarInsumoProduto(p);
            if(insumos.size()>0){
                for(Insumo i :insumos){
                    boolean teste = false;
                    for (Insumo in : tempInsumo) {
                        if (i.getId() == in.getId()) {
                            teste = true;
                            break;
                        }
                    }
                    if(teste==false){
                        bd.deletarInsumoProduto(p, i);
                    }

                }
                for(Insumo i :tempInsumo){
                    boolean teste = false;
                    for(Insumo in : insumos){
                        if(i.getId()==in.getId()){
                            teste=true;
                            break;
                        }
                    }
                    if(teste==false){
                       bd.inserirInsumoProduto(p,i);
                    }
                }
            }else{
                for(Insumo i :tempInsumo){
                    bd.inserirInsumoProduto(p,i);
                }
            }
            bd.atualizarProduto(p);

        }catch (Exception e){
            throw new Exception("Não foi possível salvar suas alterações.");
        }
    }
}
