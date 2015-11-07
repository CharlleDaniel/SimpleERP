package com.simpleerp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

/**
 * Created by CharlleNot on 30/10/2015.
 */
public class MinhasPlanilhas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_minhas_planilhas);
        File[] f=carregaArquivosDiretorioRaiz();
        if(f.length>0) {
            if(f[0]!=null){
                abrirPlanilha(f[0]);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            showMessage("Settings");
        }
        return super.onOptionsItemSelected(item);
    }
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void abrirPlanilha(File file){
        String type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        Intent it = new Intent();
        it.setAction(android.content.Intent.ACTION_VIEW);
        it.setDataAndType(Uri.fromFile(file), type);
        try {
            startActivity(it);
        } catch (Exception e) {

           showMessage("Aplicativo necessário para abrir o arquivo não encontrado.");
        }
    }

    public File[] carregaArquivosDiretorioRaiz(){
        File []f= new File[0];
        File [] retorno= new File[0];
        try {
            if((new File("/sdcard/SimpleERP/Planilhas")).exists()){// Cria o diretório
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
           showMessage("Não foi Possivel encontrar a pasta do simpleERP");
        }
        return retorno;
    }

}
