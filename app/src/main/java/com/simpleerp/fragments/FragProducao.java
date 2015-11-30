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
import android.widget.Toast;

import com.simpleerp.Control.SimpleBilling;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.PlanilhaAdapter;
import com.simpleerp.adapters.ProducaoAdapter;
import com.simpleerp.entidades.Producao;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    private static Producao producao;
    private SimpleBilling sistemaFaturamento;

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
            adapter = new PlanilhaAdapter(getContext(), files);
            planilhas.setAdapter(adapter);

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
        String nameAdapter=parent.getAdapter().toString();
        nameAdapter=nameAdapter.substring(nameAdapter.lastIndexOf(".") + 1, nameAdapter.indexOf("@"));
        if(nameAdapter.equalsIgnoreCase("PlanilhaAdapter")){
            file = adapter.getItem(position);
            try {
                startActivity(sistema.abrirPlanilha(file));
            }catch (Exception e ){
                showMessage("Aplicativo necessário para abrir o arquivo não encontrado.");
            }
        }else{
            producao= adapaterProducao.getItem(position);
            showMessage(producao.getNome());
        }





    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        AdapterView parent = (AdapterView)v;
        String nameAdapter=parent.getAdapter().toString();
        nameAdapter=nameAdapter.substring(nameAdapter.lastIndexOf(".") + 1, nameAdapter.indexOf("@"));

        if(nameAdapter.equalsIgnoreCase("PlanilhaAdapter")){
            file =adapter.getItem(aInfo.position);

            menu.setHeaderTitle("Opções de " + file.getName());
            menu.add(0, 1, 0, "Excluir");
            menu.add(0, 2, 0, "Salvar no Drive");
            menu.add(0, 3, 0, "Remover do Drive");

        }else{
            producao=adapaterProducao.getItem(aInfo.position);
            sistemaFaturamento = new SimpleBilling(producao);
            menu.setHeaderTitle("Opções de "+ producao.getNome());
            menu.add(0, 5, 0, "Excluir");
            menu.add(0,7,0, "Gerar Planilha");
        }



    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        switch (itemId){
            case 1:
                try {
                    file.delete();
                    buildListPlanilhas();
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
            case 5:
                try {
                    sistema.removeProducao(producao);
                    showMessage("Excluido.");
                    buildListProducoes();
                }catch (Exception e){
                    showMessage("Não Excluido.");
                }
                break;
            case 7:
                try {
                    sistemaFaturamento.gerarPlanilhas();
                    showMessage("Planilha criada com sucesso.");
                    buildListPlanilhas();

                } catch (FileNotFoundException e) {
                    showMessage("Planilha não criada.");
                }

            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

}
