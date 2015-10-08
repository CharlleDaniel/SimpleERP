package com.simpleerp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.simpleerp.entidades.Insumo;

/**
 * Created by Thainan on 28/06/15.
 */
public class CadInsumo extends Activity implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtQuantidade;
    private EditText edtCustoUnidade;
    private Button btnCadastrar;
    private Insumo insumo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cad_insumo);

        this.edtNome = (EditText) findViewById(R.id.edtNome);
        this.edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
        this.edtCustoUnidade = (EditText) findViewById(R.id.edtCustoUnidade);

        this.btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        this.btnCadastrar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        this.insumo.setNome(this.edtNome.getText().toString());
        this.insumo.setQuantidade(Float.parseFloat(this.edtQuantidade.getText().toString()));
        this.insumo.setCustoUnidade(Float.parseFloat(this.edtCustoUnidade.getText().toString()));

    }
}
