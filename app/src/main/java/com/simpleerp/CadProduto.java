package com.simpleerp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.adapters.InsumoAdapter;
import com.simpleerp.entidades.Insumo;

/**
 * Created by CharlleNot on 14/10/2015.
 */
public class CadProduto extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar bar;
    private ListView listInsumo;
    private InsumoAdapter adapter;
    private static Insumo insumo;
    private SimpleControl sistema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_produto);
        acessViews();

        sistema = MenuPrincipal.sistema;


        bar.setTitle("Adiconar Novo Produto");
        bar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bluidListInsumos();

    }
    private void acessViews(){
        bar= (Toolbar)findViewById(R.id.bar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad, menu);
        return true;
    }
    public void bluidListInsumos() {
        listInsumo = (ListView) findViewById(R.id.lvInsumo);
        listInsumo.setOnItemClickListener(this);
        registerForContextMenu(listInsumo);
        adapter = new InsumoAdapter(this, MenuPrincipal.sistema.getInsumoList());
        listInsumo.setAdapter(adapter);
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.salvar){
            showMessage("Salvo");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        insumo =adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + insumo.getNome());
        menu.add(1, 1, 1, "Excluir");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 1:

                showMessage("Apagou");

                onResume();
                break;
            case 2:
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        insumo=adapter.getItem(position);

        if(insumo.isAddList()==false){
            showMessage("Você adicionou "+insumo.getNome()+" ao Produto");
            insumo.setIsAddList(true);
        }else{
            showMessage("Você retirou " + insumo.getNome() + " do Produto");
            insumo.setIsAddList(false);
        }
        bluidListInsumos();
    }

    @Override
    public void finish() {
        sistema.setAllInsumos(false);
        super.finish();
    }
    @Override
    protected void onStop() {
        sistema.setAllInsumos(false);
        super.onStop();
    }

}

