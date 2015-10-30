package com.simpleerp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.entidades.Usuario;
import java.io.File;



public class MainActivity extends AppCompatActivity implements OnClickListener {

    // VIEWS
    private LinearLayout llContainerAll;
    private ProgressBar pbContainer;
    private RelativeLayout llConnected;
    private TextView tvLogin;
    private Button btNext;
    private EditText edNomeEmpresa;
    private boolean time;

    //Control
    protected static SimpleControl sistema;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sistema = new SimpleControl(getBaseContext());

        accessViews();


        if(sistema.getUsuarioLogado()!=null){
            Intent it = new Intent(this,MenuPrincipal.class);
            startActivity(it);
            super.finish();
        }

        login();

    }

    private void login() {
        showUi(false, true);
        time=false;
        new Thread(){
            public void run() {
                while (time==false) {
                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            public void run() {
                               time=true;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        showMessage("Conectado com Drive.");
                        showUi(true,false);
                    }
                });

            }
        }.start();
    }

    public void criaDiretorios(){
        try {
            if (!new File("/sdcard/SimpleERP").exists()) { // Verifica se o diretório existe.
                (new File("/sdcard/SimpleERP")).mkdir();// Cria o diretório
                (new File("/sdcard//SimpleERP/Planilhas")).mkdir();// Cria o diretório
            }
        } catch (Exception ex) {
            showMessage("Erro");
        }
    }



    // UTIL
    public void accessViews(){
        llContainerAll = (LinearLayout) findViewById(R.id.llContainerAll);
        pbContainer = (ProgressBar) findViewById(R.id.pbContainer);

        // CONNECTED
        llConnected = (RelativeLayout) findViewById(R.id.llConnected);
        tvLogin = (TextView) findViewById(R.id.login);
        btNext = (Button) findViewById(R.id.avançar);
        edNomeEmpresa=(EditText)findViewById(R.id.nomeEmpresa);

        // LISTENER

        btNext.setOnClickListener(MainActivity.this);

    }

    public void showUi(boolean status, boolean statusProgressBar){
        if(!statusProgressBar){
            llContainerAll.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            pbContainer.setVisibility(View.GONE);


            llConnected.setVisibility(!status ? View.GONE : View.VISIBLE);
        }
        else{

            pbContainer.setVisibility(View.VISIBLE);
        }
    }



    // LISTENERS
    @Override
    public void onClick(View v) {

        if(v.getId() == btNext.getId()){

            String temp = edNomeEmpresa.getText().toString();
            temp=temp.trim();
            if(temp.equals("")){
                showMessage("Não é Permitido Campo em Branco");
            }
            else{
                Usuario u = new Usuario();
                u.setNome(temp);
                sistema.login(u);
                Intent it = new Intent(this,MenuPrincipal.class);
                startActivity(it);
                super.finish();
            }

        }

    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}