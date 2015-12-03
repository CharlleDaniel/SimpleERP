package com.simpleerp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.InsumoAdapter;
import com.simpleerp.classesSecundarias.EditaInsumo;
import com.simpleerp.entidades.Insumo;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class FragInsumo extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listInsumos;
    private InsumoAdapter adapter;
    private SimpleControl sistema;
    public static Insumo insumo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema = MenuPrincipal.sistema;


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_layout_insumo, container, false);

        accessViews(view);
        buildListInsumos();

        return view;
    }

    public void accessViews(View view){
        listInsumos = (ListView) view.findViewById(R.id.listInsumosFrag);
    }

    public void buildListInsumos(){
        this.listInsumos.setOnItemClickListener(this);
        registerForContextMenu(listInsumos);
        adapter = new InsumoAdapter(getContext(),sistema.getInsumoList());
        listInsumos.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        insumo = adapter.getItem(position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        insumo =adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + insumo.getNome());
        menu.add(0, 24, 0, "Excluir");
        menu.add(0, 25, 0, "Editar");

    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 24:
                try {
                    sistema.removeInsumo(insumo);
                    showMessage("Excluido.");
                    buildListInsumos();
                }catch (Exception e){
                    showMessage("Não Excluido.");
                }
                break;
            case 25 :
                try{
                    Intent intent = new Intent(getActivity(),EditaInsumo.class);
                    startActivity(intent);
                }catch (Exception e){
                    showMessage("Erro ");
                }
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }
    @Override
    public void onResume(){
        buildListInsumos();
        super.onResume();
    }

}