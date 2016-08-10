package com.simpleerp.Control;

import android.os.Environment;

import com.simpleerp.MenuPrincipal;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Produto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Charlle Daniel on 30/11/2015.
 */
public class SimpleBilling {
    private SimpleControl sistema;
    private Producao producao;
    private List<Produto> produtos;

    private String dir= Environment.getExternalStorageDirectory().toString()+"/SimpleERP/Planilhas/";

    public SimpleBilling(Producao p){
        this.sistema = MenuPrincipal.sistema;
        this.producao=p;
        this.produtos = this.sistema.getProdutoProducao(p);

    }

    public  void gerarPlanilhas() throws Exception {
        if(new File(dir).exists()){
            buildPlanilha();
        } else{
            (new File(dir)).mkdirs();
            buildPlanilha();
        }


    }

    private  void buildPlanilha() throws FileNotFoundException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        String dd =dateFormat.format(date);
        dateFormat = new SimpleDateFormat("MM");
        String mm =dateFormat.format(date);
        dateFormat = new SimpleDateFormat("yyyy");
        String yy =dateFormat.format(date);

        File f = new File(dir+"Produção de "+producao.getNome().trim()+" "+dd+"."+mm+"."+yy+".xlsx");
        FileOutputStream outFile =new FileOutputStream(f);
        String teste="teste";
        try {
            outFile.write(teste.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public float calculaCusto() {
        float custoTotal= 0;

        for(Produto p : this.produtos){
            List<Insumo> insumos = this.sistema.getInsumosProduto(p);
            Map<Long,List<String>> relacao=sistema.getRelacaoTemp(p);
            float custo=0;
            for(Insumo i : insumos){

                float x=Float.parseFloat(relacao.get(i.getId()).get(1));
                if(i.getTipo().equalsIgnoreCase(relacao.get(i.getId()).get(0))){
                    if(x>i.getQuantidade()){
                        custo=custo+((x/i.getQuantidade())*i.getCustoUnidade());
                    }else if(x==i.getCustoUnidade()) {
                        custo=custo+i.getCustoUnidade();
                    }else{
                        float temp = (i.getCustoUnidade()/i.getQuantidade());
                        custo=custo+(temp*x);
                    }
                }else{
                    if(i.getTipo().equalsIgnoreCase("Quilos")){
                        float tempQuant=i.getQuantidade()*1000;
                        float temp = (i.getCustoUnidade()/tempQuant);
                        custo=custo+(temp*x);
                    }else{
                        float tempQuant=i.getQuantidade()/1000;
                        custo=custo+((x/tempQuant)*i.getCustoUnidade());
                    }

                }
            }
            Map<Long,Float> relacaoP=sistema.getRelacaoTemp(producao);
            float xP = relacaoP.get(p.getId());
            custo=(custo*xP);
            custoTotal=custoTotal+custo;
        }

        return custoTotal;
    }

    public float calculaFaturamento(){
        float receita = 0;
        Map<Long,Float> relacao=sistema.getRelacaoTemp(producao);
        for(Produto p : this.produtos){
            float x = relacao.get(p.getId());
            receita += (x*p.getPreco());
        }
        return receita;
    }
    public float calculaLucro(){
        float lucro=0;
        lucro=calculaFaturamento()-calculaCusto();
        return lucro;
    }
    public List <String> verificaProducao() {
        List <String> list= new LinkedList<>();
        String txt="";
        String txtDimi="";
        for (Produto p : this.produtos) {

            Map<Long,Float> relacaoP=sistema.getRelacaoTemp(producao);
            float x = relacaoP.get(p.getId());

            if(p.getPeriodo().equalsIgnoreCase("Dia")){
                if(p.getQtdVendas()>x){
                    float v = p.getQtdVendas() - x;
                    txt="Aumentar "+p.getNome()+" "+v+";";
                    list.add(txt);
                }else{
                    float v = x-p.getQtdVendas();
                    txtDimi="Diminuir"+p.getNome()+" "+v+";";
                    list.add(txtDimi);
                }
            }else if(p.getPeriodo().equalsIgnoreCase("Semana")){
                float qtd=(p.getQtdVendas()/7);
                if(qtd>x){
                    float v = qtd - x;
                    txt="Aumentar "+p.getNome()+" "+v+";";
                    list.add(txt);
                }else{
                    float v = x-qtd;
                    txtDimi="Diminuir "+p.getNome()+" "+v+";";
                    list.add(txtDimi);
                }

            }else{
                float qtd=(p.getQtdVendas()/30);
                if(qtd>x){
                    float v = qtd - x;
                    txt="Aumentar "+p.getNome()+" "+v+";";
                    list.add(txt);
                }else{
                    float v = x-qtd;
                    txtDimi="Dimininuir "+p.getNome()+" "+v+";";
                    list.add(txtDimi);
                }
            }
        }
        return  list;
    }



}
