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

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.InsumoAdapter;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Produto;
import com.simpleerp.fragments.FragProduto;

/**
 * Created by Victor Hugo on 30/11/2015.
 */

public class EditaProduto extends AppCompatActivity {
    private Toolbar bar;
    private ListView lvInsumo;
    private InsumoAdapter adapter;
    private static Insumo insumo;
    private EditText etNome;
    private SimpleControl sistema;
    private EditText etPreco;
    private Produto produto;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_produto);
        acessViews();

        produto = FragProduto.produto;

        sistema = MenuPrincipal.sistema;

        etNome.setText(produto.getNome());
        etPreco.setText("" + produto.getPreco());
        bar.setTitle("Editar " + produto.getNome());
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildListInsumos();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void acessViews() {
        bar = (Toolbar) findViewById(R.id.bar);
        etPreco = (EditText) findViewById(R.id.editTextPreco);
        etNome = (EditText) findViewById(R.id.editTextNome);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad, menu);
        return true;
    }

    public void buildListInsumos() {
        lvInsumo = (ListView) findViewById(R.id.lvInsumo);
        registerForContextMenu(lvInsumo);
        try {
            adapter = new InsumoAdapter(this, MenuPrincipal.sistema.getInsumoProduto(produto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lvInsumo.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salvar) {

            if (etNome.getText().toString().trim().equalsIgnoreCase("") || etPreco.getText().toString().trim().equalsIgnoreCase("")) {
                showMessage("Não é permitido campo em branco.");

            } else if (etNome.getText().toString().length() < 4) {
                showMessage("O nome deve possuir no minimo quatro caracters.");
            } else if (Float.parseFloat(etPreco.getText().toString().trim()) == 0) {
                showMessage("O preço tem que ser maior que Zero.");
            } else {

                produto.setNome(etNome.getText().toString());
                produto.setPreco(Float.parseFloat(etPreco.getText().toString()));

                try {
                    sistema.alteraProduto(produto);
                    showMessage("Salvo");
                    super.finish();
                } catch (Exception e) {
                    showMessage(e.getMessage());
                }

            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void addInsumoProduto(View v) {
        Intent intent = new Intent(this, AddInsumoProduto.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        buildListInsumos();
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        insumo = adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + insumo.getNome());
        menu.add(1, 1, 1, "Remover do Produto");
        //menu.add(1, 2, 2, "Editar");  Em teste.

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId) {
            case 1:
                insumo.setIsAddList(false);
                //sistema.removeInsumoProduto(produto, insumo);
                sistema.removeInsumoProduto(insumo);
                onResume();
                break;
    /*        case 2:
                Intent intent = new Intent(this, EditaInsumo.class);
                startActivity(intent);
                break;  //Em teste: Pesquisar tipo de intent de EditaProduto para EditaInsumo.class*/
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    public void clearAllInsumos(View view) {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_sim_nao_text);
        d.setTitle("Aviso");
        d.setCancelable(true);
        Button buttonYes = (Button) d.findViewById(R.id.buttonYes);
        Button buttonNo = (Button) d.findViewById(R.id.buttonNO);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*try {
                    for(Insumo i: sistema.getInsumoProduto(produto)){
                        sistema.removeInsumoProduto(produto, i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                sistema.setAllInsumos(false);
                sistema.removeInsumoProduto();
                buildListInsumos();
                d.dismiss();
            }
        });
        d.show();
    }
}