package com.simpleerp.classesSecundarias;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
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
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.InsumoAdapter;
import com.simpleerp.entidades.Insumo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by CharlleNot on 24/10/2015.
 */
public class AddInsumoProduto extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar bar;
    private ListView listInsumo;
    private InsumoAdapter adapter;
    private static Insumo insumo;
    private SimpleControl sistema;
    private List<Insumo>listTemp;
    private List<Insumo>listRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_insumo_produto);
        acessViews();
        sistema = MenuPrincipal.sistema;
        listTemp=new LinkedList<>();
        listRemove=new LinkedList<>();

        bar.setTitle("Adicionar Insumos");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildListInsumos();

    }

    private void acessViews(){
        bar= (Toolbar)findViewById(R.id.bar);

    }

    public void buildListInsumos() {
        listInsumo = (ListView) findViewById(R.id.lvInsumos);
        listInsumo.setOnItemClickListener(this);
        registerForContextMenu(listInsumo);
        adapter = new InsumoAdapter(this, sistema.getInsumoList());
        listInsumo.setAdapter(adapter);

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
            showMessage("Alterado.");
            sistema.addInsumoProdutoTemp(listTemp);
            sistema.removeInsumoProdutoTemp(listRemove);
            finish();
        }if(id==android.R.id.home){
            onBackPressed();
            return  true;
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
        if(insumo.isAddList()){
            menu.add(0, 1, 0, "Editar");
        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){

            case 1:
                showMessage("Editar");
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
            insumo.setIsAddList(true);
            listTemp.add(insumo);
            listRemove.remove(insumo);
            showMessage("Adicionou "+insumo.getNome().trim()+" ao produto.");


        }else{
            insumo.setIsAddList(false);
            listRemove.add(insumo);
            listTemp.remove(insumo);
            showMessage("Removeu "+insumo.getNome().trim()+" do produto.");

        }
        buildListInsumos();
    }

    public void verificaAlteracoes(){
        if(listTemp.size()>0){
            sistema.setAllInsumos(false,listTemp);
        }
        if(listRemove.size()>0){
            sistema.setAllInsumos(true,listRemove);
        }
    }
    @Override
    public void onBackPressed() {
        verificaAlteracoes();
        super.onBackPressed();
    }

}