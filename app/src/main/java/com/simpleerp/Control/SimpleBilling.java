package com.simpleerp.Control;

import com.simpleerp.MenuPrincipal;
import com.simpleerp.database.BD;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Produto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Charlle Daniel on 30/11/2015.
 */
public class SimpleBilling {
    private SimpleControl sistema;
    private Producao producao;
    private List<Produto> produtos;

    private final String dir= "/sdcard/SimpleERP/Planilhas/";

    public SimpleBilling(Producao p){
        this.sistema = MenuPrincipal.sistema;
        this.producao=p;
        this.produtos = this.sistema.getProdutoProducao(p);

    }

    public  void gerarPlanilhas() throws FileNotFoundException {
        if(new File(dir).exists()){
            buildPlanilha();
        }else{
            new File(dir).mkdirs();
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


}
