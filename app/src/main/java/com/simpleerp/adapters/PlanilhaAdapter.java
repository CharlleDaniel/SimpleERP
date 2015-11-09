package com.simpleerp.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleerp.R;
import com.simpleerp.entidades.Insumo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class PlanilhaAdapter extends BaseAdapter {
    private File[] files;
    private Context context;

    public PlanilhaAdapter(Context context, File[] files){
        this.context = context;
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public File getItem(int position) {
        return this.files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        File file= files[position];

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =inflater.inflate(R.layout.adapter_planilhas, null);

        TextView tvNome = (TextView) layout.findViewById(R.id.textViewNomeArq);
        tvNome.setText(file.getName());


        TextView tvTamanho = (TextView) layout.findViewById(R.id.textViewTamanho);
        tvTamanho.setText("Tamanho: "+file.length()/1024 +" Kb");

        TextView tvData= (TextView) layout.findViewById(R.id.textViewData);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

        tvData.setText("Modificado em: "+sdf.format(file.lastModified())+" as "+sdfTime.format(file.lastModified())+"hs.");




        return layout;
    }


}
