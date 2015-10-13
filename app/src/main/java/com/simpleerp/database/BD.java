package com.simpleerp.database;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Usuario;

public class BD {
    private SQLiteDatabase bd;

    public BD(Context context){
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserirUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("islog", usuario.isLog());

        bd.insert("usuario", null, valores);
    }


    public void atualizarUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("islog", usuario.isLog());

        bd.update("usuario", valores, "_id = ?", new String[]{""+usuario.getId()});
    }


    public void deletarUsuario(Usuario usuario){
        bd.delete("usuario", "_id = "+usuario.getId(), null);
    }


    public List<Usuario> buscarUsuario(){
        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[]{"_id", "nome", "islog" };

        Cursor cursor = bd.query("usuario", colunas, null, null, null, null, "nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                Usuario u = new Usuario();
                u.setId(cursor.getLong(0));
                u.setNome(cursor.getString(1));
                u.setIsLog(cursor.getString(2));
                list.add(u);

            }while(cursor.moveToNext());
        }

        return(list);
    }

    public void inserirInsumo(Insumo insumo){
        ContentValues valores = new ContentValues();
        valores.put("nome", insumo.getNome());
        valores.put("CustoUnidade", insumo.getCustoUnidade());
        valores.put("Quantidade", insumo.getQuantidade());

        bd.insert("insumo", null, valores);
    }


    public void atualizarInsumo(Insumo insumo){
        ContentValues valores = new ContentValues();
        valores.put("nome", insumo.getNome());
        valores.put("CustoUnidade", insumo.getCustoUnidade());
        valores.put("Quantidade", insumo.getQuantidade());

        bd.update("insumo", valores, "_id = ?", new String[]{""+insumo.getId()});
    }


    public void deletarInsumo(Insumo insumo){
        bd.delete("insumo", "_id = "+insumo.getId(), null);
    }


    public List<Insumo> buscarInsumo(){
        List<Insumo> list = new ArrayList<Insumo>();
        String[] colunas = new String[]{"_id", "nome", "CustoUnidade", "Quantidade" };

        Cursor cursor = bd.query("insumo", colunas, null, null, null, null, "nome ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                Insumo i = new Insumo();
                i.setId(cursor.getLong(0));
                i.setNome(cursor.getString(1));
                i.setCustoUnidade(cursor.getLong(2));
                i.setQuantidade(cursor.getLong(3));
                list.add(i);

            }while(cursor.moveToNext());
        }

        return(list);
    }
}
