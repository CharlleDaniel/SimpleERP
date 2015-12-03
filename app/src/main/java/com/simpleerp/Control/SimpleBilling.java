package com.simpleerp.Control;

import com.simpleerp.entidades.Producao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Charlle Daniel on 30/11/2015.
 */
public class SimpleBilling {
    private Producao producao;

    private final String dir= "/sdcard/SimpleERP/Planilhas/";

    public SimpleBilling(Producao p){

        this.producao=p;

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


}
