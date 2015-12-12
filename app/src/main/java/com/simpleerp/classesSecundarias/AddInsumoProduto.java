package com.simpleerp.classesSecundarias;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.InsumoAdapter;
import com.simpleerp.entidades.Insumo;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.simpleerp.fragments.FragProduto;
import com.simpleerp.entidades.Produto;
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
    private Map<Long,List<String>> relacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_insumo_produto);
        acessViews();
        Produto p = FragProduto.produto;
        sistema = MenuPrincipal.sistema;
        listTemp=new LinkedList<>();
        listRemove=new LinkedList<>();
        relacao= new HashMap <>();
        if(p!=null){
            relacao=sistema.getRelacaoTemp(p);
        }

        bar.setTitle("Adicionar Insumos");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildListInsumos();

    }

    private void acessViews() {

        bar = (Toolbar) findViewById(R.id.bar);
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
            sistema.addRelacaoTemp(relacao);
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
            final Dialog d = new Dialog(this);
            d.setContentView(R.layout.dialog_add_text);

            d.setTitle("Adicionar " + insumo.getNome());
            d.setCancelable(false);
            String check="";
            final RelativeLayout rl = (RelativeLayout)d.findViewById(R.id.rlMedida);
            final EditText qtdProduzida = (EditText) d.findViewById(R.id.qtdProduzida);
            final TextView tvQtdProduzida= (TextView)d.findViewById(R.id.tvQtdProduzida);
            final  RadioButton rbGrama= (RadioButton)d.findViewById(R.id.radioButton);
            final RadioButton rbQuilo= (RadioButton)d.findViewById(R.id.radioButton2);
            rbGrama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rbGrama.setChecked(true);
                    rbQuilo.setChecked(false);
                    tvQtdProduzida.setText("Quantas Gramas Deseja Adicionar ?");
                    qtdProduzida.setHint("Quantidade de gramas");
                }
            });
            rbQuilo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rbGrama.setChecked(false);
                    rbQuilo.setChecked(true);
                    tvQtdProduzida.setText("Quantos Quilos Deseja Adicionar ?" );
                    qtdProduzida.setHint("Quantidade de Quilos");
                }
            });
            if(!insumo.getTipo().equalsIgnoreCase("Unidade")){
                rl.setVisibility(View.VISIBLE);
                if(insumo.getTipo().equalsIgnoreCase("Quilos")){
                    tvQtdProduzida.setText("Quantos Quilos Deseja Adicionar ?" );
                    rbQuilo.setChecked(true);
                    qtdProduzida.setHint("Quantidade de Quilos");
                }else{
                    tvQtdProduzida.setText("Quantas Gramas Deseja Adicionar ?");
                    rbGrama.setChecked(true);
                    qtdProduzida.setHint("Quantidade de gramas");
                }

            }else{
                qtdProduzida.setHint("Quantas unidades");
                tvQtdProduzida.setText("Quantas Unidades Deseja Adicionar ?" );
            }


            Button buttonClosed= (Button)d.findViewById(R.id.btCancelar);
            buttonClosed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });

            Button buttonYes=(Button)d.findViewById(R.id.buttonSave);
            buttonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (qtdProduzida.getText().toString().trim().equals("")) {
                        showMessage("Não é permitido campo em branco.");
                    } else if (Double.parseDouble(qtdProduzida.getText().toString()) < 1) {
                        showMessage("Não é permitido valor nulo ou zero.");
                    } else {
                        insumo.setIsAddList(true);
                        listTemp.add(insumo);
                        listRemove.remove(insumo);
                        List<String> list= new LinkedList<>();
                        if(rbGrama.isChecked()){
                            list.add("Gramas");
                        }else if (rbQuilo.isChecked()) {
                            list.add("Quilos");
                        }else{
                            list.add("Unidade");
                        }

                        list.add(qtdProduzida.getText().toString());
                        relacao.put(insumo.getId(),list);
                        showMessage("Adicionou " + insumo.getNome().trim() + " ao produto.");
                        d.dismiss();
                        buildListInsumos();
                    }
                }
            });
            d.show();

        }else{
            insumo.setIsAddList(false);
            listRemove.add(insumo);
            listTemp.remove(insumo);
            relacao.remove(insumo.getId());
            showMessage("Removeu " + insumo.getNome().trim() + " do produto.");

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