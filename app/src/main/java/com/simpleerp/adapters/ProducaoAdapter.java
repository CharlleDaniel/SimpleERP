package com.simpleerp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleerp.R;
import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Producao;

import java.util.List;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class ProducaoAdapter extends BaseAdapter {
    private List<Producao> lProducao;
    private Context context;

    public ProducaoAdapter(Context context, List<Producao> lProducao){
        this.context = context;
        this.lProducao = lProducao;
    }

    @Override
    public int getCount() {
        return lProducao.size();
    }

    @Override
    public Producao getItem(int position) {
        return this.lProducao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lProducao.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Producao producao= lProducao.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =inflater.inflate(R.layout.adapter_producao, null);

        TextView tvNome = (TextView) layout.findViewById(R.id.textViewNomeProducao);
        tvNome.setText(producao.getNome());



        return layout;
    }


}
