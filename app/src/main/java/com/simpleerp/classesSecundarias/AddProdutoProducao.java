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
import android.widget.TextView;
import android.widget.Toast;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.adapters.ProdutoAdapter;
import com.simpleerp.entidades.Producao;
import com.simpleerp.entidades.Produto;
import com.simpleerp.fragments.FragProducao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by CharlleNot on 24/10/2015.
 */

public class AddProdutoProducao extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar bar;
    private ListView listProduto;
    private ProdutoAdapter adapter;
    private static Produto produto;
    private SimpleControl sistema;
    private List<Produto>listTemp;
    private List<Produto>listRemove;
    private String tipoProducao;
    private Map<Long, Float> relacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        tipoProducao=getIntent().getExtras().getString("check");

        setContentView(R.layout.add_produto_producao);
        acessViews();
        sistema = MenuPrincipal.sistema;
        Producao p = FragProducao.producao;
        listTemp=new LinkedList<>();
        listRemove=new LinkedList<>();
        relacao= new HashMap<>();
        if(p!=null){
            relacao=sistema.getRelacaoTemp(p);
        }

        bar.setTitle("Adicionar Produtos");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildListProdutos();
    }

    private void acessViews(){
        bar= (Toolbar)findViewById(R.id.bar);

    }

    public void buildListProdutos() {
        listProduto = (ListView) findViewById(R.id.lvProdutos);
        listProduto.setOnItemClickListener(this);
        registerForContextMenu(listProduto);
        adapter = new ProdutoAdapter(this, sistema.getProdutoList());
        listProduto.setAdapter(adapter);

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
            sistema.addProdutoProducaoTemp(listTemp);
            sistema.addRelacaoProdutoTemp(relacao);
            sistema.removeProdutoProducaoTemp(listRemove);
            finish();
        }
        if(id==android.R.id.home){
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

        produto = adapter.getItem(aInfo.position);

        menu.setHeaderTitle("Opções de " + produto.getNome());
       if(produto.isAddList()) {
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
        produto = adapter.getItem(position);

        if(produto.isAddList()==false){
            final Dialog d = new Dialog(this);
            d.setContentView(R.layout.dialog_add_text);

            d.setTitle(produto.getNome());
            d.setCancelable(false);

            final TextView tvQtdProduzida= (TextView)d.findViewById(R.id.tvQtdProduzida);
            tvQtdProduzida.setText("Quantidade Produzida Por "+tipoProducao + ": " );
            final EditText qtdProduzida = (EditText) d.findViewById(R.id.qtdProduzida);
            qtdProduzida.setHint("Quantidade produzida por "+tipoProducao.toLowerCase()+ ": " );

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
                    }

                    else if (Double.parseDouble(qtdProduzida.getText().toString()) <1) {
                        showMessage("Não é permitido valor nulo ou zero.");
                    }
                    else {
                        produto.setIsAddList(true);
                        relacao.put(produto.getId(),Float.parseFloat(qtdProduzida.getText().toString()));
                        listTemp.add(produto);
                        listRemove.remove(produto);
                        showMessage("Adicionou " + produto.getNome().trim() + " a produção.");
                        d.dismiss();
                        buildListProdutos();
                    }
                }
            });
            d.show();

        }else{
            produto.setIsAddList(false);
            relacao.remove(produto.getId());
            listRemove.add(produto);
            listTemp.remove(produto);
            showMessage("Removeu " + produto.getNome().trim() + " da produção.");
            buildListProdutos();
        }

    }

    public void verificaAlteracoes(){
        if(listTemp.size() > 0){
            sistema.setAllProdutos(false, listTemp);
        }
        if(listRemove.size() > 0){
            sistema.setAllProdutos(true, listRemove);
        }
    }

    @Override
    public void onBackPressed() {
        verificaAlteracoes();
        super.onBackPressed();
    }
}