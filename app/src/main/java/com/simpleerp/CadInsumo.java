package com.simpleerp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by CharlleNot on 14/10/2015.
 */
public class CadInsumo extends ActionBarActivity implements View.OnClickListener {
    private Toolbar bar;
    private RadioButton rbUnidade;
    private RadioButton rbQuilo;
    private RadioButton rbGrama;
    private EditText etQtdGrama;
    private EditText etPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_insumo);
        acessViews();


        bar.setTitleTextAppearance(this,R.layout.text);
        bar.setTitle("Adiconar Novo Insumo");
        bar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void acessViews(){
        bar= (Toolbar)findViewById(R.id.bar);
        rbGrama= (RadioButton)findViewById(R.id.radioButtonGrama);
        rbQuilo= (RadioButton)findViewById(R.id.radioButtonQuilo);
        rbUnidade= (RadioButton)findViewById(R.id.radioButtonUnidade);

        etQtdGrama=(EditText)findViewById(R.id.editTextQtdGrama);
        etPreco=(EditText)findViewById(R.id.editTextPreco);

        rbGrama.setOnClickListener(this);
        rbQuilo.setOnClickListener(this);
        rbUnidade.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.salvar){
            showMessage("Salvo");
            super.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==rbGrama.getId()){
            rbGrama.setChecked(true);
            rbQuilo.setChecked(false);
            rbUnidade.setChecked(false);
            etQtdGrama.setVisibility(View.VISIBLE);
            etPreco.setHint("Preço em Gramas");
        }else if(v.getId()==rbQuilo.getId()){
            rbGrama.setChecked(false);
            rbQuilo.setChecked(true);
            rbUnidade.setChecked(false);
            etQtdGrama.setVisibility(View.GONE);
            etPreco.setHint("Preço de Um Quilo");
        }else  if(v.getId()==rbUnidade.getId()){
            rbGrama.setChecked(false);
            rbQuilo.setChecked(false);
            rbUnidade.setChecked(true);
            etQtdGrama.setVisibility(View.GONE);
            etPreco.setHint("Preço de Uma Unidade");
        }
    }
}

