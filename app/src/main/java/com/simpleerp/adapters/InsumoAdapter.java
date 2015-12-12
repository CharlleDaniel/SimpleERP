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

import java.util.List;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class InsumoAdapter extends BaseAdapter {
    private List<Insumo> lInsumo;
    private Context context;
    private ImageView tipoMedida;
    private TextView tvQtd;
    private TextView tvId;
    private ImageView imgPreco;

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
        View layout =inflater.inflate(R.layout.adapter_insumo_produto, null);

        TextView tvNome = (TextView) layout.findViewById(R.id.textViewNomeProduto);
        tvNome.setText(insumo.getNome());


        tvId = (TextView) layout.findViewById(R.id.textViewPreco);
        tvQtd = (TextView) layout.findViewById(R.id.textViewQtd);

        tipoMedida = (ImageView)layout.findViewById(R.id.imageViewQtdTipo);
        imgPreco = (ImageView)layout.findViewById(R.id.ivPrecos);
        ImageView addMarca = (ImageView)layout.findViewById(R.id.addMarca);

        if(context.getClass().getSimpleName().equalsIgnoreCase("MenuPrincipal")){
            alterVisibility(View.VISIBLE);
            tvId.setText(" R$ "+ insumo.getCustoUnidade());
            if(!insumo.getTipo().equalsIgnoreCase("Unidade")){
                if(insumo.getQuantidade()>1){
                    tvQtd.setText(""+insumo.getQuantidade()+" "+insumo.getTipo());
                }else{
                    tvQtd.setText(""+insumo.getQuantidade()+" "+insumo.getTipo().substring(0,insumo.getTipo().length()-1));
                }
            }else{
                tvQtd.setText("" + insumo.getQuantidade() + " " + insumo.getTipo());

            }

            if(insumo.getTipo().equalsIgnoreCase("unidade")){
                tipoMedida.setBackgroundResource(R.drawable.icon_unidade);
            } else {
                tipoMedida.setBackgroundResource(R.drawable.balanca);

            }
        }else{
            alterVisibility(View.GONE);
            addMarca.setVisibility(View.VISIBLE);
            if(insumo.isAddList()==true){
                addMarca.setBackgroundResource(R.drawable.ok);
            }else{
                addMarca.setBackgroundResource(android.R.drawable.ic_input_add);
            }

        }

        return layout;
    }

    private void alterVisibility(int visibility) {
        tvId.setVisibility(visibility);
        tvQtd.setVisibility(visibility);
        imgPreco.setVisibility(visibility);
        tipoMedida.setVisibility(visibility);
    }


}
