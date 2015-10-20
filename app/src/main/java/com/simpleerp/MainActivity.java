package com.simpleerp;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.entidades.Usuario;
import java.io.File;

public class MainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int SIGN_IN_CODE = 56465;
    private GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;
    private boolean isConsentScreenOpened;
    private boolean isSignInButtonClicked;

    // VIEWS
    private LinearLayout llContainerAll;
    private ProgressBar pbContainer;
    private RelativeLayout llConnected;
    private TextView tvLogin;
    private Button btNext;
    private EditText edNomeEmpresa;


    //Control
    protected static SimpleControl sistema;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sistema = new SimpleControl(getBaseContext());

        accessViews();

        googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();
        login();
        criaDiretorios();


        if(sistema.getUsuarioLogado()!=null){
            Intent it = new Intent(this,MenuPrincipal.class);
            startActivity(it);
            super.finish();
        }


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

    @Override
    public void onStart(){
        super.onStart();

        if(googleApiClient != null){
            googleApiClient.connect();
        }
    }

    @Override
    public void onStop(){
        super.onStop();

        if(googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == SIGN_IN_CODE){
            isConsentScreenOpened = false;

            if(resultCode != RESULT_OK){
                isSignInButtonClicked = false;
            }

            if(!googleApiClient.isConnecting()){
                googleApiClient.connect();
            }
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

    public void resolveSignIn(){
        if(connectionResult != null && connectionResult.hasResolution()){
            try {
                isConsentScreenOpened = true;
                connectionResult.startResolutionForResult(MainActivity.this, SIGN_IN_CODE);
            }
            catch(SendIntentException e) {
                isConsentScreenOpened = false;
                googleApiClient.connect();
            }
        }
    }

    public void getDataProfile(){
       String p= "pass";

        if(!p.equals( "null")){



        }
        else{
            Toast.makeText(MainActivity.this, "Dados n�o liberados", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(){
        if(!googleApiClient.isConnecting()){
            isSignInButtonClicked = true;
            showUi(false, true);
            resolveSignIn();
        }
    }

    // LISTENERS
    @Override
    public void onClick(View v) {

        if(v.getId() == btNext.getId()){
            if(googleApiClient.isConnected()){
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

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInButtonClicked = false;
        showUi(true, false);
        getDataProfile();
        showMessage("Conectado com drive");
    }


    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
        showUi(false, false);
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if(!result.hasResolution()){
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), MainActivity.this, 0).show();
            return;
        }

        if(!isConsentScreenOpened){
            connectionResult = result;

            if(isSignInButtonClicked){
                resolveSignIn();
            }
        }
    }
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*
     Getter for the {@code GoogleApiClient}.
     */
    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

}