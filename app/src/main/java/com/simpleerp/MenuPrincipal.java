package com.simpleerp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.google.android.gms.location.LocationListener;

import java.util.Locale;


public class MenuPrincipal extends ActionBarActivity implements View.OnClickListener {
    private RelativeLayout producao;
    private RelativeLayout produto;
    private RelativeLayout insumo;
    private RelativeLayout llproducao;
    private RelativeLayout llproduto;
    private RelativeLayout llinsumo;
    private ScrollView scrollView;
    private boolean showLLproducao;
    private boolean showLLproduto;
    private boolean showLLinsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        String title= MainActivity.edNomeEmpresa.getText().toString();
        title=title.trim();
        title=title.substring(0,1).toUpperCase()+title.substring(1);
        setTitle(title);

        acessViews();
    }

    public void acessViews(){
        producao= (RelativeLayout)findViewById(R.id.producao);
        produto= (RelativeLayout)findViewById(R.id.produto);
        insumo= (RelativeLayout)findViewById(R.id.insumo);
        llproducao= (RelativeLayout)findViewById(R.id.llProducao);
        llproduto= (RelativeLayout)findViewById(R.id.llProduto);
        llinsumo= (RelativeLayout)findViewById(R.id.llInsumo);
        scrollView=(ScrollView)findViewById(R.id.scrollView);
        producao.setOnClickListener(this);
        produto.setOnClickListener(this);
        insumo.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.drive:
                Intent it = new Intent(this,CriarArquivo.class);
                startActivity(it);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==producao.getId()&& showLLproducao!=true){
            scrollView.fullScroll(View.FOCUS_UP);
            llproducao.setVisibility(View.VISIBLE);
            llproduto.setVisibility(View.GONE);
            llinsumo.setVisibility(View.GONE);
            showLLproducao=true;
            showLLproduto=false;
            showLLinsumo=false;
        }
        else if(v.getId()==produto.getId()&& showLLproduto!=true){
            llproducao.setVisibility(View.GONE);
            scrollView.fullScroll(View.FOCUSABLES_TOUCH_MODE);
            llproduto.setVisibility(View.VISIBLE);
            llinsumo.setVisibility(View.GONE);
            showLLproducao=false;
            showLLproduto=true;
            showLLinsumo=false;
        }
        else if(v.getId()==insumo.getId()&& showLLinsumo!=true){
            scrollView.fullScroll(View.FOCUS_DOWN);
            llproducao.setVisibility(View.GONE);
            llproduto.setVisibility(View.GONE);
            llinsumo.setVisibility(View.VISIBLE);
            showLLproducao=false;
            showLLproduto=false;
            showLLinsumo=true;
        }
        else{
            hideAllMenu();
        }

    }

    private void hideAllMenu() {

        llproducao.setVisibility(View.GONE);
        llproduto.setVisibility(View.GONE);
        llinsumo.setVisibility(View.GONE);
        showLLproducao=false;
        showLLproduto=false;
        showLLinsumo=false;
    }
}
