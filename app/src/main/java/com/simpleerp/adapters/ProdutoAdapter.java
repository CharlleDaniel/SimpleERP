package com.simpleerp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simpleerp.R;
import com.simpleerp.entidades.Produto;

import java.util.List;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class ProdutoAdapter extends BaseAdapter {
    private List<Produto> lProduto;
    private Context context;

    public ProdutoAdapter(Context context, List<Produto> lProduto){
        this.context = context;
       this.lProduto=lProduto;
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



        return layout;
    }


}
