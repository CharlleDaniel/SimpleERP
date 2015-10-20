package com.simpleerp.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleerp.R;
import com.simpleerp.entidades.Insumo;

import java.util.List;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class InsumoAdapter extends BaseAdapter {
    private List<Insumo> lInsumo;
    private Context context;

    public InsumoAdapter(Context context, List<Insumo> lInsumo){
        this.context = context;
        this.lInsumo = lInsumo;
    }

    @Override
    public int getCount() {
        return lInsumo.size();
    }

    @Override
    public Insumo getItem(int position) {
        return this.lInsumo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lInsumo.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Insumo insumo= lInsumo.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View layout =inflater.inflate(R.layout.adapter_insumo_producao, null);

        TextView tvNome = (TextView) layout.findViewById(R.id.t3);
        tvNome.setText(insumo.getNome());


        TextView tvId = (TextView) layout.findViewById(R.id.t4);
        tvId.setText("Preço: "+insumo.getCustoUnidade()+" Quantidade: "+insumo.getQuantidade()+" "+insumo.getTipo());
        ImageView checkAdd = (ImageView)layout.findViewById(R.id.marcaAdd);

        if(insumo.isAddList()==false){
            checkAdd.setBackgroundResource(android.R.drawable.checkbox_off_background);
        }else{

            checkAdd.setBackgroundResource(android.R.drawable.checkbox_on_background);

        }

        return layout;
    }
}
