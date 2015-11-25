package com.simpleerp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.PlanilhaAdapter;
import com.simpleerp.adapters.ProducaoAdapter;

import java.io.File;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class FragProducao extends Fragment implements AdapterView.OnItemClickListener {
    private ListView planilhas;
    private SimpleControl sistema;
    private PlanilhaAdapter adapter;
    private View view;
    private static File file;
    private ListView producoes;
    private ProducaoAdapter adapaterProducao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema= MenuPrincipal.sistema;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_layout_producao, container, false);
        acessViews(view);
        buildListProducoes();
        buildListPlanilhas();
        return view;

    }

    private void buildListProducoes() {
        registerForContextMenu(producoes);
        producoes.setOnItemClickListener(this);
        producoes.setVerticalScrollBarEnabled(false);
        adapaterProducao= new ProducaoAdapter(getContext(),sistema.getProducaoList());
        producoes.setAdapter(adapaterProducao);
    }

    public void acessViews(View view){
        planilhas=(ListView)view.findViewById(R.id.listViewPlanilhas);
        producoes=(ListView)view.findViewById(R.id.listViewProducoes);
    }
    public void buildListPlanilhas() {
        registerForContextMenu(planilhas);
        planilhas.setVerticalScrollBarEnabled(false);
        planilhas.setOnItemClickListener(this);

        try{
            File [] files =sistema.carregaArquivosDiretorioRaiz();
            if(files.length>0){
                if(files[0]!=null){
                    adapter = new PlanilhaAdapter(getContext(), files);
                    planilhas.setAdapter(adapter);

                }
            }
        }catch (Exception e){
            showMessage(e.getMessage());
        }
    }


    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume(){
        buildListPlanilhas();
        buildListProducoes();
        super.onResume();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        file = adapter.getItem(position);
        showMessage(parent.getAdapter().toString());
        try {
           startActivity(sistema.abrirPlanilha(file));
        }catch (Exception e ){
            showMessage("Aplicativo necessário para abrir o arquivo não encontrado.");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        file =adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + file.getName());
        menu.add(0, 1, 0, "Excluir");
        menu.add(0, 2, 0, "Salvar no Drive");
        menu.add(0, 3, 0, "Remover do Drive");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 1:
                try {
                    file.delete();
                    }catch (Exception e){
                    showMessage("Não Excluido.");
                }
                break;
            case 2:
                showMessage("Arquivo"+file.getName()+" em Upload no Drive.");
                break;
            case 3:
                showMessage("Arquivo"+file.getName()+" Removido do Drive.");
                break;

            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

}
