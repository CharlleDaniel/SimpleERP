package com.simpleerp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.simpleerp.adapters.ProdutoAdapter;
import com.simpleerp.entidades.Produto;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class FragProduto extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listProdutos;
    private ProdutoAdapter adapter;
    private SimpleControl sistema;
    private static Produto produto;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema = MenuPrincipal.sistema;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_layout_produto, container, false);


        buildListProdutos();

        return view;

    }


    public void buildListProdutos(){
        listProdutos = (ListView) view.findViewById(R.id.listProdutosFrag);
        registerForContextMenu(listProdutos);
        listProdutos.setOnItemClickListener(this);
        adapter = new ProdutoAdapter(getContext(),sistema.getProdutoList());
        listProdutos.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        produto = adapter.getItem(position);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        produto =adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + produto.getNome());
        menu.add(0, 14, 0, "Excluir");

    }


    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 14:
                try {
                    sistema.removeProduto(produto);
                    showMessage("Excluido.");
                    buildListProdutos();
                }catch (Exception e){
                    showMessage("Não Excluido.");
                }
                break;

            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    @Override
    public void onResume(){
        buildListProdutos();
        super.onResume();
    }


}
