package com.simpleerp;


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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.adapters.ProdutoAdapter;
import com.simpleerp.classesSecundarias.AddProdutoProducao;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Produto;

import java.util.List;


/**
 * Created by CharlleNot on 14/10/2015.
 */
public class CadProducao extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar bar;
    private ListView lvProdutos;
    private static Produto produto;
    private ProdutoAdapter adapter;
    private SimpleControl sistema;
    private TextView nomeProducao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cad_producao);
        acessViews();
        sistema = MenuPrincipal.sistema;
        buildListInsumos();

        bar.setTitle("Nova Produção");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void acessViews(){
        bar= (Toolbar)findViewById(R.id.bar);
        nomeProducao=(TextView)findViewById(R.id.editTextNome);
    }
    public void buildListInsumos() {
        lvProdutos = (ListView) findViewById(R.id.listProdutos);
        registerForContextMenu(lvProdutos);
        lvProdutos.setOnItemClickListener(this);
        adapter = new ProdutoAdapter(this, MenuPrincipal.sistema.getProdutoProducao());
        lvProdutos.setAdapter(adapter);

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
            if(nomeProducao.getText().toString().trim().equalsIgnoreCase("")){
                showMessage("Não é perimitido campo em branco.");
            }
            if(nomeProducao.getText().toString().trim().length()<4){
                showMessage("O nome deve possuir no minimo quatro caracteres.");
            }else{
                try{
                    Producao p = new Producao();
                    p.setNome(nomeProducao.getText().toString());
                    sistema.addProducao(p);
                    showMessage("Salvo");
                    finish();
                    sistema.setAllProdutos(false);
                    sistema.removeProdutoProducao();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        produto=adapter.getItem(position);

       try{
           List <Insumo> list=sistema.getInsumoProduto(produto);
           for(Insumo l:list){
               showMessage(""+l.getNome());
           }

       }catch (Exception e){
           showMessage(e.getMessage());
       }

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

        produto =adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + produto.getNome());
        menu.add(1, 1, 1, "Remover da Produção");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 1:
                produto.setIsAddList(false);
                sistema.removeProdutoProducao(produto);
                onResume();
            case 2:
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    public void addProdutoProducao(View view){
        Intent intent= new Intent(this,AddProdutoProducao.class);
        startActivity(intent);
    }
    public void clearAllProdutos(View view){
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_sim_nao_text);
        d.setTitle("Aviso");
        d.setCancelable(true);
        TextView text=(TextView)d.findViewById(R.id.textDialog);
        text.setText("Deseja mesmo limpar todos os produtos?");
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
                sistema.removeProdutoProducao();
                sistema.setAllProdutos(false);
                buildListInsumos();
                d.dismiss();
            }
        });
        d.show();

    }
}

