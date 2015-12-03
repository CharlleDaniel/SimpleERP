package com.simpleerp.classesSecundarias;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.fragments.FragInsumo;


/**
 * Created by CharlleNot on 14/10/2015.
 */
public class EditaInsumo extends AppCompatActivity implements View.OnClickListener {
    private Toolbar bar;
    private RadioButton rbUnidade;
    private RadioButton rbQuilo;
    private RadioButton rbGrama;
    private EditText etQtd;
    private EditText etPreco;
    private EditText etNome;
    private String quant;
    protected SimpleControl sistema= MenuPrincipal.sistema;
    private Insumo insumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_insumo);
        acessViews();
        insumo= FragInsumo.insumo;

        etNome.setText(insumo.getNome());
        etPreco.setText(""+insumo.getCustoUnidade());
        if(insumo.getTipo().equalsIgnoreCase("Unidade")){
            rbUnidade.setChecked(true);
            rbGrama.setChecked(false);
            rbQuilo.setChecked(false);
        }
        if(insumo.getTipo().equalsIgnoreCase("Quilos")){
            rbUnidade.setChecked(false);
            rbGrama.setChecked(false);
            rbQuilo.setChecked(true);
            etQtd.setText("" + insumo.getQuantidade());
            etQtd.setVisibility(View.VISIBLE);
        }else{
            rbUnidade.setChecked(false);
            rbGrama.setChecked(true);
            rbQuilo.setChecked(false);
            etQtd.setText("" + insumo.getQuantidade());
            etQtd.setVisibility(View.VISIBLE);
        }

        bar.setTitle("Editar Insumo");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void acessViews(){
        bar= (Toolbar)findViewById(R.id.bar);
        rbGrama= (RadioButton)findViewById(R.id.radioButtonGrama);
        rbQuilo= (RadioButton)findViewById(R.id.radioButtonQuilo);
        rbUnidade= (RadioButton)findViewById(R.id.radioButtonUnidade);

        etQtd=(EditText)findViewById(R.id.editTextQtd);
        etPreco=(EditText)findViewById(R.id.editTextPreco);
        etNome=(EditText)findViewById(R.id.editTextNome);

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
            if(etNome.getText().toString().trim().equalsIgnoreCase("")|| etPreco.getText().toString().trim().equalsIgnoreCase("")){
                showMessage("Não é permitido campo em branco.");
            }
            else if(etQtd.getText().toString().trim().equalsIgnoreCase("")&& !rbUnidade.isChecked()){
                showMessage("Não é permitido campo em branco.");
            }
            else if(etNome.getText().toString().length()<4) {
                showMessage("O nome deve possuir no minimo quatro caracteres.");
            }else if (!rbUnidade.isChecked()&& Float.parseFloat(etQtd.getText().toString().trim())==0){
                showMessage("A quantidade tem que ser maior que Zero.");
            }else{
                try{
                    String tipo="";
                    if(rbUnidade.isChecked()){
                        tipo="Unidade";
                    }else if(rbQuilo.isChecked()){
                        tipo="Quilos";
                    }else{
                        tipo="Gramas";
                    }
                    insumo.setNome(etNome.getText().toString());
                    insumo.setCustoUnidade(Float.parseFloat(etPreco.getText().toString()));
                    insumo.setTipo(tipo);
                    if(tipo.equalsIgnoreCase("Unidade")){
                        insumo.setQuantidade(1);
                    }else{
                        insumo.setQuantidade(Float.parseFloat(etQtd.getText().toString()));
                    }
                    sistema.alteraInsumo(insumo);
                    showMessage("Salvo");
                    super.finish();
                }catch (Exception e){
                    showMessage(e.getMessage());
                }

            }


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
            etQtd.setHint("Quantidade em Gramas");
            etQtd.setVisibility(View.VISIBLE);
            etPreco.setVisibility(View.GONE);
            etQtd.setText("");
            etPreco.setText("");


        }else if(v.getId()==rbQuilo.getId()){
            rbGrama.setChecked(false);
            rbQuilo.setChecked(true);
            rbUnidade.setChecked(false);
            etQtd.setVisibility(View.GONE);
            etQtd.setHint("Quantidade em Quilos");
            etQtd.setVisibility(View.VISIBLE);
            etPreco.setVisibility(View.GONE);
            etQtd.setText("");
            etPreco.setText("");


        }else  if(v.getId()==rbUnidade.getId()) {
            rbGrama.setChecked(false);
            rbQuilo.setChecked(false);
            rbUnidade.setChecked(true);
            etQtd.setVisibility(View.GONE);
            etPreco.setHint("Preço da Unidade");
            etPreco.setVisibility(View.VISIBLE);
            etQtd.setText("");
            etPreco.setText("");

        }
        verificarCampo();

    }

    public void verificarCampo() {

        new Thread() {
            public void run() {
                while (etQtd.getText().toString().trim().equalsIgnoreCase("") && rbUnidade.isChecked()==false) {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            public void run() {

                                if(rbUnidade.isChecked()==false){
                                    etPreco.setVisibility(View.GONE);
                                }
                                etPreco.setText("");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    public void run() {

                        verificarCampo2();
                    }
                });

            }
        }.start();
    }


    public  void verificarCampo2(){

        quant=etQtd.getText().toString().trim();
        if(rbGrama.isChecked()){
            if(etQtd.getText().toString().trim().length()>=1){
                if(etQtd.getText().toString().trim().equals("1")){
                    etPreco.setHint("Preço de "+etQtd.getText().toString()+" Grama");

                }else{
                    etPreco.setHint("Preço de "+etQtd.getText().toString()+" Gramas");
                }
                etPreco.setVisibility(View.VISIBLE);
            }

        }

        if(rbQuilo.isChecked()){
            if(etQtd.getText().toString().trim().length()>=1){
                if(etQtd.getText().toString().trim().equals("1")){
                    etPreco.setHint("Preço de "+etQtd.getText().toString()+" Quilo");
                }else{
                    etPreco.setHint("Preço de " + etQtd.getText().toString() + " Quilos");
                }
                etPreco.setVisibility(View.VISIBLE);
            }


        }
        if(rbUnidade.isChecked()){
            etPreco.setVisibility(View.VISIBLE);
        }



        new Thread() {
            public void run() {
                while (quant.equalsIgnoreCase(etQtd.getText().toString().trim())&& rbUnidade.isChecked()==false) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    public void run() {

                        verificarCampo();
                    }
                });

            }
        }.start();
    }


}

