package com.simpleerp;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int SIGN_IN_CODE = 56465;
    private GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;

    private boolean isConsentScreenOpened;
    private boolean isSignInButtonClicked;

    // VIEWS
    private LinearLayout llContainerAll;
    private ProgressBar pbContainer;


    private LinearLayout llConnected;
    private ImageView ivProfile;
    private ProgressBar pbProfile;
    private TextView tvId;
    private TextView tvLanguage;
    private TextView tvName;
    private TextView tvUrlProfile;
    private TextView tvEmail;
    private Button btSignOut;
    private Button btRevokeAccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessViews();

        googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();
        login();
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
        llConnected = (LinearLayout) findViewById(R.id.llConnected);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvId = (TextView) findViewById(R.id.tvId);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvName = (TextView) findViewById(R.id.tvName);
        tvUrlProfile = (TextView) findViewById(R.id.tvUrlProfile);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        btSignOut = (Button) findViewById(R.id.btSignOut);


        // LISTENER

        btSignOut.setOnClickListener(MainActivity.this);

    }

    public void showUi(boolean status, boolean statusProgressBar){
        if(!statusProgressBar){
            llContainerAll.setVisibility(View.VISIBLE);
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

        if(p != null){
            String id = "123";
            String name ="Charlle";
            String language = "PT_BR";
            String profileUrl = "www.google.com";

            String email = "charlle.daniel@gmail.com";

            tvId.setText(id);
            tvLanguage.setText(language);
            tvName.setText(name);
            tvEmail.setText(email);

            tvUrlProfile.setText(profileUrl);
            Linkify.addLinks(tvUrlProfile, Linkify.WEB_URLS);


        }
        else{
            Toast.makeText(MainActivity.this, "Dados não liberados", Toast.LENGTH_SHORT).show();
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

        if(v.getId() == R.id.btSignOut){
            if(googleApiClient.isConnected()){
               Intent it = new Intent(this,ListFilesActivity.class);
               startActivity(it);
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Getter for the {@code GoogleApiClient}.
     */
    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
}