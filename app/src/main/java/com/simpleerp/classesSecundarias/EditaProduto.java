package com.simpleerp.classesSecundarias;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.InsumoAdapter;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Produto;
import com.simpleerp.fragments.FragProduto;

import java.util.List;


/**
 * Created by CharlleNot on 14/10/2015.
 */
public class EditaProduto extends AppCompatActivity{
    private Toolbar bar;
    private ListView lvInsumo;
    private InsumoAdapter adapter;
    private static Insumo insumo;
    private EditText etNome;
    private EditText etPreco;
    private SimpleControl sistema;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_produto);
        acessViews();

        sistema = MenuPrincipal.sistema;
        produto = FragProduto.produto;

        List<Insumo> list = sistema.getInsumosProduto(produto);
        sistema.addInsumoProdutoTemp(list);

        etNome.setText(produto.getNome());
        etPreco.setText(""+produto.getPreco());

        bar.setTitle("Editar Produto");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildListInsumos();

    }
    private void acessViews(){
        bar= (Toolbar)findViewById(R.id.bar);
        etPreco=(EditText)findViewById(R.id.editTextPreco);
        etNome=(EditText)findViewById(R.id.editTextNome);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad, menu);
        return true;
    }
    public void buildListInsumos() {
        lvInsumo = (ListView) findViewById(R.id.lvInsumo);
        registerForContextMenu(lvInsumo);
        adapter = new InsumoAdapter(this,sistema.getInsumoProdutoTemp());
        lvInsumo.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.salvar){

            if(etNome.getText().toString().trim().equalsIgnoreCase("")|| etPreco.getText().toString().trim().equalsIgnoreCase("")){
                showMessage("Não é permitido campo em branco.");

            }
            else if(etNome.getText().toString().length()<4) {
                showMessage("O nome deve possuir no minimo quatro caracters.");
            }else if (Float.parseFloat(etPreco.getText().toString().trim())==0) {
                showMessage("O preço tem que ser maior que Zero.");
            }else{

                produto.setNome(etNome.getText().toString().trim());
                produto.setPreco(Float.parseFloat(etPreco.getText().toString()));

                try{
                    sistema.alteraProduto(produto);
                    showMessage("Salvo.");
                    sistema.setAllInsumos(false);
                    sistema.removeAllInsumosProdutoTemp();
                    finish();
                }catch (Exception e){
                    showMessage(e.getMessage());
                }

            }

        }if(id==android.R.id.home){
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void addInsumoProduto(View v) {
        Intent intent= new Intent(this, AddInsumoProduto.class);
        startActivity(intent);
    }
    @Override
    protected  void onResume(){
        buildListInsumos();
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        insumo =adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + insumo.getNome());
        menu.add(1, 1, 1, "Remover do Produto");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 1:
                insumo.setIsAddList(false);
                sistema.removeInsumoProdutoTemp(insumo);
                buildListInsumos();
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    public void clearAllInsumos(View view){
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_sim_nao_text);
        d.setTitle("Aviso");
        d.setCancelable(true);
        Button buttonYes=(Button)d.findViewById(R.id.buttonYes);
        Button buttonNo=(Button)d.findViewById(R.id.buttonNO);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sistema.setAllInsumos(false);
                sistema.removeAllInsumosProdutoTemp();
                buildListInsumos();
                d.dismiss();
            }
        });
        d.show();

    }
    @Override
    public void onBackPressed() {
        sistema.setAllInsumos(false);
        sistema.removeAllInsumosProdutoTemp();
        super.onBackPressed();
    }


}

