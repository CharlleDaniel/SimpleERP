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
import com.simpleerp.adapters.ProdutoAdapter;



/**
 * Created by CharlleNot on 09/10/2015.
 */
public class Produto extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listProdutos;
    private ProdutoAdapter adapter;
    private SimpleControl sistema;
    private com.simpleerp.entidades.Produto produto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_layout_produto, container, false);
        this.sistema = MenuPrincipal.sistema;
        this.accessViews(view);
        buildListProdutos();

        return view;

    }

    public void accessViews(View view){
        this.listProdutos = (ListView) view.findViewById(R.id.listProdutos);
    }

    public void buildListProdutos(){
        this.listProdutos.setOnItemClickListener(this);
        registerForContextMenu(this.listProdutos);
        this.adapter = new ProdutoAdapter(getContext(),this.sistema.getProdutoList());
        this.listProdutos.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.produto = this.adapter.getItem(position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        produto =adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + produto.getNome());
        menu.add(1, 1, 1, "Excluir");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 1:
                produto.setIsAddList(false);
                sistema.removeProduto(produto);
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
