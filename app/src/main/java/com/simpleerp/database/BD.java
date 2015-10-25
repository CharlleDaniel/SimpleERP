package com.simpleerp.database;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpleerp.entidades.Insumo;
import com.simpleerp.entidades.Produto;
import com.simpleerp.entidades.Usuario;

public class BD {
    private SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserirUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("islog", usuario.isLog());

        bd.insert("usuario", null, valores);
    }


    public void atualizarUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("islog", usuario.isLog());

        bd.update("usuario", valores, "_id = ?", new String[]{"" + usuario.getId()});
    }


    public void deletarUsuario(Usuario usuario) {
        bd.delete("usuario", "_id = " + usuario.getId(), null);
    }


    public List<Usuario> buscarUsuario() {
        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[]{"_id", "nome", "islog"};

        Cursor cursor = bd.query("usuario", colunas, null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Usuario u = new Usuario();
                u.setId(cursor.getInt(0));
                u.setNome(cursor.getString(1));
                u.setIsLog(cursor.getString(2));
                list.add(u);

            } while (cursor.moveToNext());
        }

        return (list);
    }

    public void inserirInsumo(Insumo insumo) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", insumo.getNome());
        valores.put("CustoUnidade", insumo.getCustoUnidade());
        valores.put("Quantidade", insumo.getQuantidade());
        valores.put("Tipo", insumo.getTipo());

        bd.insert("insumo", null, valores);
    }


    public void atualizarInsumo(Insumo insumo) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", insumo.getNome());
        valores.put("CustoUnidade", insumo.getCustoUnidade());
        valores.put("Quantidade", insumo.getQuantidade());
        valores.put("Tipo", insumo.getTipo());

        bd.update("insumo", valores, "_id = ?", new String[]{"" + insumo.getId()});
    }


    public void deletarInsumo(Insumo insumo) {
        bd.delete("insumo", "_id = " + insumo.getId(), null);
    }


    public List<Insumo> buscarInsumos() {
        List<Insumo> list = new ArrayList<Insumo>();
        String[] colunas = new String[]{"_id", "Nome", "CustoUnidade", "Quantidade", "Tipo"};

        Cursor cursor = bd.query("insumo", colunas, null, null, null, null, "Nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Insumo i = new Insumo();
                i.setId(cursor.getInt(0));
                i.setNome(cursor.getString(1));
                i.setCustoUnidade(cursor.getFloat(2));
                i.setQuantidade(cursor.getFloat(3));
                i.setTipo(cursor.getString(4));
                list.add(i);

            } while (cursor.moveToNext());
        }

        return (list);
    }

    public void inserirProduto(Produto produto) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", produto.getNome());
        valores.put("PRECO", produto.getPreco());
        bd.insert("PRODUTO", null, valores);
    }

    public void atualizarProduto(Produto produto) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", produto.getNome());
        valores.put("PRECO", produto.getPreco());

        bd.update("PRODUTO", valores, "_id = ?", new String[]{"" + produto.getId()});
    }

    public void deletarInsumo(Produto produto) {
        bd.delete("PRODUTO", "_id = " + produto.getId(), null);
    }

    public List<Produto> buscarProdutos() {
        List<Produto> list = new ArrayList<Produto>();
        String[] colunas = new String[]{"_id", "NOME", "PRECO"};

        Cursor cursor = bd.query("PRODUTO", colunas, null, null, null, null, "NOME ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Produto p = new Produto();
                p.setId(cursor.getInt(0));
                p.setNome(cursor.getString(1));
                p.setPreco(cursor.getFloat(2));

                list.add(p);

            } while (cursor.moveToNext());
        }

        return (list);
    }
    public void inserirInsumoProduto(Produto produto , Insumo i){
        ContentValues valores = new ContentValues();
        valores.put("idProduto", produto.getId());
        valores.put("idInsumo", i.getId());

        bd.insert("PRODUTO_INSUMO", null, valores);
    }
    public void deletarInsumoProduto(Produto produto,Insumo insumo) {
        bd.delete("PRODUTO_INSUMO", "idProduto = " + produto.getId() + " and " + "idInsumo = " + insumo.getId(), null);
    }

    public List<Insumo> buscarInsumoProduto(Produto produto) {
        List<Insumo> list = new ArrayList<Insumo>();
        String[] colunas = new String[]{"_id", "Nome", "CustoUnidade", "Quantidade", "Tipo"};
        Cursor cursor = bd.query("insumo i , PRODUTO_INSUMO pi",colunas, "pi.idProduto = "+produto.getId()+" and pi.idInsumo= i._id", null, null, null, "Nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Insumo i = new Insumo();
                i.setId(cursor.getInt(0));
                i.setNome(cursor.getString(1));
                i.setCustoUnidade(cursor.getFloat(2));
                i.setQuantidade(cursor.getFloat(3));
                i.setTipo(cursor.getString(4));
                list.add(i);

            } while (cursor.moveToNext());
        }

        return (list);
    }
}