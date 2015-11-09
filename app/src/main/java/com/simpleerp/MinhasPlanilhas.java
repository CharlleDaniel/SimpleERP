package com.simpleerp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;

import java.io.File;

/**
 * Created by CharlleNot on 30/10/2015.
 */
public class MinhasPlanilhas extends AppCompatActivity {
    private SimpleControl sistema;
    private File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_minhas_planilhas);
        sistema = MenuPrincipal.sistema;
        try{
            files=sistema.carregaArquivosDiretorioRaiz();

        }catch(Exception e){
            showMessage(e.getMessage());
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





}
