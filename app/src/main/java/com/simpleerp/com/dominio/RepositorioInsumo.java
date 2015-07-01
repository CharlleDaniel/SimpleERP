package com.simpleerp.com.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.simpleerp.entidades.Insumo;

/**
 * Created by Thainan on 28/06/15.
 */
public class RepositorioInsumo {
    private SQLiteDatabase conn;

    public RepositorioInsumo(SQLiteDatabase conn){
        this.conn = conn;
    }

    public void inserir(Insumo insumo){
        ContentValues values = new ContentValues();

        values.put("Nome", insumo.getNome());
        values.put("Quantidade", insumo.getQuantidade());
        values.put("CustoUnidade", insumo.getCustoUnidade());

        this.conn.insertOrThrow("SimpleERP", null, values);
    }

    public ArrayAdapter<Insumo> buscarInsumos(Context context){
        ArrayAdapter<Insumo> adpInsumos = new ArrayAdapter<Insumo>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = this.conn.query("SimpleERP", null, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Insumo insumo = new Insumo();

                insumo.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
                insumo.setQuantidade(Float.parseFloat(cursor.getString(cursor.getColumnIndex("Quantidade"))));
                insumo.setCustoUnidade(Float.parseFloat(cursor.getString(cursor.getColumnIndex("CustoUnidade"))));

                adpInsumos.add(insumo);

            }while(cursor.moveToNext());

        }
        return adpInsumos;
    }
}
