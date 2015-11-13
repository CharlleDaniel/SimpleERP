package com.simpleerp;

import android.content.Intent;
import android.content.IntentSender;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.entidades.Usuario;

public class MainActivity extends AppCompatActivity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    // VIEWS
    private LinearLayout llContainerAll;
    private ProgressBar pbContainer;
    private RelativeLayout llConnected;
    private TextView tvLogin;
    private Button btNext;
    private EditText edNomeEmpresa;
    protected String mAccountName;

    //Control
    protected static SimpleControl sistema;

    //Drive Login

    private static final int SIGN_IN_CODE = 56465;
    private GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;
    protected static final String EXTRA_ACCOUNT_NAME = "account_name";


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

        googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(Plus.API)
                .addApi(com.google.android.gms.drive.Drive.API)
                .addScope(com.google.android.gms.drive.Drive.SCOPE_FILE)
                .build();

        showUi(false,true);

    }

    public void criaDiretorios(){
        try {
            if (!new java.io.File("/sdcard/SimpleERP").exists()) { // Verifica se o diretório existe.
                (new java.io.File("/sdcard/SimpleERP")).mkdir();// Cria o diretório
                (new java.io.File("/sdcard/SimpleERP/Planilhas")).mkdir();// Cria o diretório
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
                u.setEmail(mAccountName);
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

            if(resultCode != RESULT_OK){
                resolveSignIn();
            }

            if(!googleApiClient.isConnecting()){
                googleApiClient.connect();
            }
        }
    }

    public void resolveSignIn(){
        if(connectionResult != null && connectionResult.hasResolution()){
            try {
                 connectionResult.startResolutionForResult(MainActivity.this, SIGN_IN_CODE);
            }
            catch(IntentSender.SendIntentException e) {
                googleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if(googleApiClient.isConnected()){
            showUi(true, false);
            mAccountName = Plus.AccountApi.getAccountName(googleApiClient);
            showMessage("Conectado "+ mAccountName + " No Drive");

        }else{
            resolveSignIn();
        }
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
        connectionResult = result;
        resolveSignIn();
    }
    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
}