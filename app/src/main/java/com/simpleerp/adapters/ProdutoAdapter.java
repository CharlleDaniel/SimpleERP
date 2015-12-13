package com.simpleerp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleerp.Control.SimpleControl;
import com.simpleerp.MenuPrincipal;
import com.simpleerp.R;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Produto;

import java.util.List;
import java.util.Map;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class ProdutoAdapter extends BaseAdapter {
    private List<Produto> lProduto;
    private Context context;
    private SimpleControl sistema;
    private TextView tvQtd;
    private ImageView imgCusto;
    private ImageView imgInsumos;
    private TextView tvCust;
    private TextView tvPreco;
    private ImageView imgPreco;

    public ProdutoAdapter(Context context, List<Produto> lProduto){
        this.context = context;
       this.lProduto=lProduto;
        sistema= MenuPrincipal.sistema;
    }

    @Override
    public int getCount() {
        return lProduto.size();
    }

    @Override
    public Produto getItem(int position) {
        return this.lProduto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lProduto.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto= lProduto.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =inflater.inflate(R.layout.adapter_produto_producao, null);

        TextView tvNome = (TextView) layout.findViewById(R.id.textViewNomeProduto);
        tvNome.setText(produto.getNome());
        ImageView addMarca = (ImageView)layout.findViewById(R.id.addMarca);

        tvPreco = (TextView) layout.findViewById(R.id.textViewPreco);
        imgCusto = (ImageView)layout.findViewById(R.id.imageView14);
        imgInsumos = (ImageView)layout.findViewById(R.id.imageView15);
        imgPreco = (ImageView)layout.findViewById(R.id.ivPreco);



        tvQtd = (TextView) layout.findViewById(R.id.textViewQtdInsumo);
        tvCust = (TextView) layout.findViewById(R.id.textViewCusto);

        if(context.getClass().getSimpleName().equalsIgnoreCase("MenuPrincipal")){
            alterVisibility(View.VISIBLE);
            addMarca.setVisibility(View.GONE);
            tvPreco.setText(" R$ " + produto.getPreco());
            try {
                List<Insumo> list=sistema.getInsumosProduto(produto);
                Map<Long,List<String>>relacaoTemp=sistema.getRelacaoTemp(produto);
                if(list.size()>0){
                    tvQtd.setText(""+list.size());
                    float custo=0;
                    for(Insumo i : list){
                        float x=Float.parseFloat(relacaoTemp.get(i.getId()).get(1));
                        if(i.getTipo().equalsIgnoreCase(relacaoTemp.get(i.getId()).get(0))){
                            if(x>i.getQuantidade()){
                                custo=custo+((x/i.getQuantidade())*i.getCustoUnidade());
                            }else if(x==i.getCustoUnidade()) {
                                custo=custo+i.getCustoUnidade();
                            }else{
                                float temp = (i.getCustoUnidade()/i.getQuantidade());
                                custo=custo+(temp*x);
                            }
                        }else{
                            if(i.getTipo().equalsIgnoreCase("Quilos")){
                                float tempQuant=i.getQuantidade()*1000;
                                float temp = (i.getCustoUnidade()/tempQuant);
                                custo=custo+(temp*x);
                            }else{
                                float tempQuant=i.getQuantidade()/1000;
                                custo=custo+((x/tempQuant)*i.getCustoUnidade());
                            }

                        }
                    }
                    tvCust.setText(""+custo);

                }else{
                    tvCust.setText("0");
                    tvQtd.setText("0");
                }

            } catch (Exception e){

            }

        }else{
            alterVisibility(View.GONE);
            addMarca.setVisibility(View.VISIBLE);
            if(produto.isAddList()==true){
                addMarca.setBackgroundResource(R.drawable.ok);
            }else{
                addMarca.setBackgroundResource(android.R.drawable.ic_input_add);
            }

        }

        return layout;
    }
    private void alterVisibility(int visibility){
        tvPreco.setVisibility(visibility);
        tvCust.setVisibility(visibility);
        tvQtd.setVisibility(visibility);
        imgCusto.setVisibility(visibility);
        imgInsumos.setVisibility(visibility);
        imgPreco.setVisibility(visibility);
    }

}
