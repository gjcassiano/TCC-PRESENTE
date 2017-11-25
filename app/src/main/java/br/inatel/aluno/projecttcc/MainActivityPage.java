package br.inatel.aluno.projecttcc;

import android.Manifest;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.inatel.aluno.projecttcc.service.UserService;

public class MainActivityPage extends AppCompatActivity {

    private static final String TAG = MainActivityPage.class.getSimpleName();
    private TextView carregandoText;
    SharedPreferences sharedpreferences;
    public static final String MY_PREFERENCES = "PRESENTE" ;
    public static final String MATRICULA = "matricula";

    ProgressDialogPresente progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialogPresente(MainActivityPage.this);
        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        while (!checkAndRequestPermissions()){}

    }

    @Override
    protected void onResume() {
        super.onResume();
        String matricula = sharedpreferences.getString(MATRICULA, null);

        if(matricula == null){
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
        } else {
            updateUser();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        changedPage = false;
    }

    public void updateUser(){
        progressDialog.showProgressDialog("Buscando usuario...");
        setContentView(R.layout.activity_content);
        UserService userService = new UserService();
        userService.getUserExecute(sharedpreferences.getString(MATRICULA, null),"UNKNOW");
        carregandoText = (TextView)findViewById(R.id.carregando_id);

        updateList(userService,500);
        updateList(userService,1000);
        updateList(userService,1500);
        updateList(userService,2000);
        updateList(userService,3000);
        updateList(userService,5000);
        updateList(userService,10000);
    }

    Handler handler = new Handler();
    Boolean changedPage = false;
    private void updateList(final UserService userService, int time){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"Carregando user: " + userService.getUser());
            if (userService.getUser() != null && !changedPage){
                changedPage = true;
                carregandoText.setVisibility(View.INVISIBLE);
                //check if aluno or prof
                if(userService.getUser().getUserType() == 1) {
                    //prof
                    Log.i(TAG,"Professor logado.");
                    Fragment newFragment = new MateriaFragment();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, newFragment);
                    transaction.commit();
                } else {
                    Log.i(TAG,"Aluno logado.");
                    //aluno
                    Fragment newFragment = new AlunoFragment().setUserId(userService.getUser().getId());
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, newFragment);
                    transaction.commit();
                }

                progressDialog.hideProgressDialog();
            } else {
                carregandoText.setText(carregandoText.getText() + ".");
            }
            }
        },time);
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private  boolean checkAndRequestPermissions() {


        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int getaccount = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (getaccount != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.GET_ACCOUNTS);
        }
        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

    }

}
