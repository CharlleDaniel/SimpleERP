package com.simpleerp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.simpleerp.com.dominio.RepositorioInsumo;
import com.simpleerp.database.Database;
import com.simpleerp.entidades.Insumo;

/**
 * Created by Thainan on 28/06/15.
 */
public class InsumoActivity extends Activity implements View.OnClickListener {

    private Button btnNovoInsumo;
    private ListView lstInsumos;
    private ArrayAdapter<Insumo> adpInsumos;

    private Database database;
    private SQLiteDatabase conn;
    private RepositorioInsumo repositorio;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insumo);

        this.btnNovoInsumo = (Button) findViewById(R.id.btnNovoInsumo);
        this.btnNovoInsumo.setOnClickListener(this);

        this.lstInsumos = (ListView) findViewById(R.id.lstInsumos);

        try{
            this.database = new Database(this);
            this.conn = database.getWritableDatabase();

            this.repositorio = new RepositorioInsumo(conn);
            this.adpInsumos = repositorio.buscarInsumos(this);
            this.lstInsumos.setAdapter(this.adpInsumos);

        }catch(SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }
    }

    @Override
    public void onClick(View view) {
        Intent it = new Intent(this, CadInsumo.class);
        startActivityForResult(it, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.adpInsumos = repositorio.buscarInsumos(this);
        this.lstInsumos.setAdapter(this.adpInsumos);
    }
}
