package com.simpleerp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.PlanilhaAdapter;
import com.simpleerp.adapters.ProdutoAdapter;

import java.io.File;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class Producao extends Fragment implements AdapterView.OnItemClickListener {
    private ListView planilhas;
    private SimpleControl sistema;
    private PlanilhaAdapter adapter;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema= MenuPrincipal.sistema;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_layout_producao, container, false);
        acessViews(view);
        buildListPlanilhas();
        return view;

    }

    public void acessViews(View view){
        planilhas=(ListView)view.findViewById(R.id.listViewPlanilhas);
    }
    public void buildListPlanilhas() {
        planilhas.setVerticalScrollBarEnabled(false);
        planilhas.setOnItemClickListener( this);

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
        super.onResume();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
           startActivity(sistema.abrirPlanilha(adapter.getItem(position)));
        }catch (Exception e ){
            showMessage("Aplicativo necessário para abrir o arquivo não encontrado.");
        }
    }
}
