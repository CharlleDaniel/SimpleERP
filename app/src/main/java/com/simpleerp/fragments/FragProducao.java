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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.simpleerp.Control.SimpleBilling;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.PlanilhaAdapter;
import com.simpleerp.adapters.ProducaoAdapter;
import com.simpleerp.classesSecundarias.EditaProducao;
import com.simpleerp.entidades.Producao;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class FragProducao extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView planilhas;
    private SimpleControl sistema;
    private PlanilhaAdapter adapter;
    private View view;
    private static File file;
    private ListView producoes;
    private ProducaoAdapter adapaterProducao;
    public static Producao producao;
    private SimpleBilling sistemaFaturamento;
    private RelativeLayout containerMax;
    private ScrollView containerMin;
    private TextView tvProd;
    private TextView tvPla;
    private TextView tvFatu;
    public static boolean isAbaMax;
    private FloatingActionButton fab;
    private RadioButton rbMes;
    private RadioButton rb6Mes;
    private RadioButton rbAno;

    private TextView tvVE;
    private TextView tvVS;
    private TextView tvVL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema= MenuPrincipal.sistema;


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_layout_producao, container, false);
        isAbaMax= false;
        acessViews(view);
        accessFaturamento(view);
        buildListProducoes();
        buildListPlanilhas();
        buildFaturamento();
        return view;

    }

    private void buildListProducoes() {
        if(isAbaMax){
            producoes=(ListView)view.findViewById(R.id.lvAbaProd);
        }else{
            producoes=(ListView)view.findViewById(R.id.listViewProducoes);
        }
        registerForContextMenu(producoes);
        producoes.setOnItemClickListener(this);
        producoes.setVerticalScrollBarEnabled(false);
        adapaterProducao= new ProducaoAdapter(getContext(),sistema.getProducaoList());
        producoes.setAdapter(adapaterProducao);
    }

    public void acessViews(View view){
        planilhas=(ListView)view.findViewById(R.id.listViewPlanilhas);
        producoes=(ListView)view.findViewById(R.id.listViewProducoes);
        containerMax= (RelativeLayout)view.findViewById(R.id.containerMax);
        containerMin= (ScrollView)view.findViewById(R.id.containerMini);

        tvProd= (TextView)view.findViewById(R.id.tvProd);
        tvPla= (TextView)view.findViewById(R.id.tvPlanilhas);
        tvFatu= (TextView)view.findViewById(R.id.tvFaturamento);

        tvProd.setOnClickListener(this);
        tvPla.setOnClickListener(this);
        tvFatu.setOnClickListener(this);

        RelativeLayout aba1=(RelativeLayout) view.findViewById(R.id.Aba1);
        RelativeLayout aba2= (RelativeLayout)view.findViewById(R.id.Aba2);
        RelativeLayout aba3= (RelativeLayout)view.findViewById(R.id.Aba3);

        aba1.setOnClickListener(this);
        aba2.setOnClickListener(this);
        aba3.setOnClickListener(this);

        fab = (FloatingActionButton) view.findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAbaMax=false;
                containerMin.setVisibility(View.VISIBLE);
                containerMax.setVisibility(View.GONE);
                onResume();
            }
        });


    }

    public void accessFaturamento(View view){
        rbMes=(RadioButton)view.findViewById(R.id.rbMesF);
        rb6Mes=(RadioButton)view.findViewById(R.id.rb6MF);
        rbAno=(RadioButton)view.findViewById(R.id.rbAnoF);

        rbMes.setOnClickListener(rbControlFaturamento());
        rb6Mes.setOnClickListener(rbControlFaturamento());
        rbAno.setOnClickListener(rbControlFaturamento());

        tvVE=(TextView)view.findViewById(R.id.tvVauleE);
        tvVS=(TextView)view.findViewById(R.id.tvValueS);
        tvVL=(TextView)view.findViewById(R.id.tvValueL);

    }
    public View.OnClickListener rbControlFaturamento (){
        View.OnClickListener on = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==rbMes.getId()){
                    rbMes.setChecked(true);
                    rb6Mes.setChecked(false);
                    rbAno.setChecked(false);
                }
                else if(v.getId()==rb6Mes.getId()){
                    rbMes.setChecked(false);
                    rb6Mes.setChecked(true);
                    rbAno.setChecked(false);
                }else{
                    rbMes.setChecked(false);
                    rb6Mes.setChecked(false);
                    rbAno.setChecked(true);
                }
                buildFaturamento();
            }
        };
        return  on;
    }
    public void buildFaturamento(){
        List<Producao> list = sistema.getProducaoList();
        if(list.size()>0){
            float custoTotal=0;
            float lucroTotal=0;
            float fatuTotal=0;
            for(Producao p :list){
                sistemaFaturamento = new SimpleBilling(p);

                fatuTotal=fatuTotal+sistemaFaturamento.calculaFaturamento();
                custoTotal=custoTotal+sistemaFaturamento.calculaCusto();
                lucroTotal=lucroTotal+sistemaFaturamento.calculaLucro();
            }
            DecimalFormat dc= new DecimalFormat("#.00");

            if(rbMes.isChecked()){
                tvVE.setText(dc.format((fatuTotal*30)));
                tvVS.setText(dc.format((custoTotal*30)));
                tvVL.setText(dc.format((lucroTotal*30)));
            }else if(rb6Mes.isChecked()){
                tvVE.setText(dc.format((fatuTotal*180)));
                tvVS.setText(dc.format((custoTotal*180)));
                tvVL.setText(dc.format((lucroTotal*180)));
            }else{
                tvVE.setText(dc.format((fatuTotal*360)));
                tvVS.setText(dc.format((custoTotal*360)));
                tvVL.setText(dc.format((lucroTotal*360)));
            }

        }else{
            tvVE.setText("0.0");
            tvVS.setText("0.0");
            tvVL.setText("0.0");
        }


    }
    public void buildListPlanilhas() {

        if(isAbaMax){
            planilhas=(ListView)view.findViewById(R.id.lvAbaPlan);
        }else{
            planilhas=(ListView)view.findViewById(R.id.listViewPlanilhas);
        }

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
        buildFaturamento();
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
            menu.setHeaderTitle("Opções de " + producao.getNome());
            menu.add(0, 5, 0, "Excluir");
            menu.add(0,8,0, "Editar");
            menu.add(0, 7, 0, "Gerar Planilha");

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
                    buildFaturamento();
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
                break;
            case 8 :
                Intent it = new Intent(getActivity(), EditaProducao.class);
                startActivity(it);
                break ;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        isAbaMax=true;
        containerMin.setVisibility(View.GONE);
        containerMax.setVisibility(View.VISIBLE);

        LinearLayout aba1=(LinearLayout) view.findViewById(R.id.abaL1);
        LinearLayout aba2= (LinearLayout)view.findViewById(R.id.abaL2);
        LinearLayout aba3= (LinearLayout)view.findViewById(R.id.abaL3);

        RelativeLayout containerAba1= (RelativeLayout)view.findViewById(R.id.rlAba1);
        RelativeLayout containerAba2= (RelativeLayout)view.findViewById(R.id.rlAba2);
        RelativeLayout containerAba3= (RelativeLayout)view.findViewById(R.id.rlAba3);

        if(v.getId()==R.id.tvProd || v.getId()==R.id.Aba1){
            aba1.setVisibility(View.GONE);
            containerAba1.setVisibility(View.VISIBLE);

            aba2.setVisibility(View.VISIBLE);
            containerAba2.setVisibility(View.GONE);

            aba3.setVisibility(View.VISIBLE);
            containerAba3.setVisibility(View.GONE);
            buildListProducoes();
        }
        else if (v.getId()==R.id.tvPlanilhas|| v.getId()==R.id.Aba2){
            aba1.setVisibility(View.VISIBLE);
            containerAba1.setVisibility(View.GONE);

            aba2.setVisibility(View.GONE);
            containerAba2.setVisibility(View.VISIBLE);

            aba3.setVisibility(View.VISIBLE);
            containerAba3.setVisibility(View.GONE);
            buildListPlanilhas();
        }
        else{
            aba1.setVisibility(View.VISIBLE);
            containerAba1.setVisibility(View.GONE);

            aba2.setVisibility(View.VISIBLE);
            containerAba2.setVisibility(View.GONE);

            aba3.setVisibility(View.GONE);
            containerAba3.setVisibility(View.VISIBLE);
        }
    }


}
