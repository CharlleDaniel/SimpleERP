package com.simpleerp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Thainan on 27/06/15.
 */
public class Database extends SQLiteOpenHelper {
    private ScriptSQL scriptSQL;

    public Database(Context context){
        super(context, "SimpleERP", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.scriptSQL = new ScriptSQL();

        sqLiteDatabase.execSQL(this.scriptSQL.getCreateInsumo());
        sqLiteDatabase.execSQL(this.scriptSQL.getCreateProducao());
        sqLiteDatabase.execSQL(this.scriptSQL.getCreateProducaoProduto());
        sqLiteDatabase.execSQL(this.scriptSQL.getCreateProduto());
        sqLiteDatabase.execSQL(this.scriptSQL.getCreateProdutoInsumo());
        sqLiteDatabase.execSQL(this.scriptSQL.getCreateUsuario());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
