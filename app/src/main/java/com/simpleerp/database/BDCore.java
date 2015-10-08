package com.simpleerp.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {
    private ScriptSQL scriptSQL;
    private static final String NOME_BD = "BDSimpleERP";
    private static final int VERSAO_BD = 1;


    public BDCore(Context ctx){
        super(ctx, NOME_BD, null, VERSAO_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        scriptSQL = new ScriptSQL();
        bd.execSQL(scriptSQL.getCreateUsuario());
        bd.execSQL(scriptSQL.getCreateInsumo());
        bd.execSQL(scriptSQL.getCreateProducao());
        bd.execSQL(scriptSQL.getCreateProducaoProduto());
        bd.execSQL(scriptSQL.getCreateProduto());
        bd.execSQL(scriptSQL.getCreateProdutoInsumo());
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table usuario;");
        onCreate(bd);
    }

}