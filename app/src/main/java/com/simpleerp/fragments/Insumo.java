package com.simpleerp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.InsumoAdapter;
import com.simpleerp.adapters.ProdutoAdapter;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class Insumo extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listInsumos;
    private InsumoAdapter adapter;
    private SimpleControl sistema;
    private com.simpleerp.entidades.Insumo insumo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_layout_insumo, container, false);
        this.sistema = MenuPrincipal.sistema;
        this.accessViews(view);

        return view;

    }

    public void accessViews(View view){
        this.listInsumos = (ListView) view.findViewById(R.id.listInsumos);
    }

    public void buildListProdutos(){
        this.listInsumos.setOnItemClickListener(this);
        registerForContextMenu(this.listInsumos);
        this.adapter = new InsumoAdapter(getContext(),this.sistema.getInsumoList());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.insumo = this.adapter.getItem(position);
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
                insumo.setIsAddList(false);
                sistema.removeInsumo(insumo);
                onResume();
                break;
            case 2:
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }
}
