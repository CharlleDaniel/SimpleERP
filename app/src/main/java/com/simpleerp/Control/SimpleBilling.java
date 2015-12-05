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
            new File(dir).mkdir();
            buildPlanilha();
        }


    }

    private  void buildPlanilha() throws FileNotFoundException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        String dd =dateFormat.format(date);
        dateFormat = new SimpleDateFormat("mm");
        String mm =dateFormat.format(date);
        dateFormat = new SimpleDateFormat("yyyy");
        String yy =dateFormat.format(date);


        FileOutputStream outFile =new FileOutputStream(new File(dir+"Produção de "+producao.getNome().trim()+" "+dd+"."+mm+"."+yy+".xlsx"));

    }

    public float calculaCusto() throws Exception {
        float custoTotal = 0;

        for(Produto p : this.produtos){
            List<Insumo> insumos = this.sistema.getInsumosProduto(p);
            for(Insumo i : insumos){
                //O custo de cada insumo corresponde a: (preço unitário * quantidade)
                float custo = i.getCustoUnidade() * i.getQuantidade();
                custoTotal += custo;
            }
        }

        return custoTotal;
    }

    public float calculaFaturamento(){
        float receita = 0;

        for(Produto p : this.produtos){
            receita += p.getPreco();
        }

        return receita;
    }


}
