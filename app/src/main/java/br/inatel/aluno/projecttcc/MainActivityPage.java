package br.inatel.aluno.projecttcc;

import android.Manifest;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.unknown.myapplication.backend.presente.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import static br.inatel.aluno.projecttcc.service.RequestService.urlRoot;

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
    boolean openedLogin = false;
    @Override
    protected void onResume() {
        super.onResume();
        if (openedLogin){
            updateUser();
            return;
        }
        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String matricula = sharedpreferences.getString(MATRICULA, null);
        if(matricula == null){
            openedLogin = true;
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
        } else {
            openedLogin = false;
            updateUser();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        changedPage = false;
    }
    User user = new User();
    public void updateUser(){
        progressDialog.showProgressDialog("Buscando usuario...");
        setContentView(R.layout.activity_content);


        AndroidNetworking.get(urlRoot + "users/get")
                .setTag("TCC")
                .addQueryParameter("deviceSerial", Build.SERIAL )
                .addQueryParameter("matricula",sharedpreferences.getString(MATRICULA, null))
                .addQueryParameter("name", "UNKNOW")
                .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray items = (JSONArray) response.get("items");
                    Log.i(TAG,items.toString());
                    for(int n = 0; n < items.length(); n++)
                    {
                        JSONObject object = items.getJSONObject(n);
                        user.setId(object.getLong("id"));
                        user.setName(object.getString("name"));
                        user.setSerialNumber(object.getString("serialNumber"));
                        user.setMatricula(object.getLong("matricula"));
                        user.setUserType(object.getLong("userType"));
                        updateList();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.i(TAG, "Erro:" + anError.getMessage());
            }
        });

        carregandoText = (TextView)findViewById(R.id.carregando_id);

    }

    Handler handler = new Handler();
    Boolean changedPage = false;

    private void updateList(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"Carregando user: " + user.getName() + " id:" + user.getId());
            if (user != null && !changedPage){
                changedPage = true;
                carregandoText.setVisibility(View.INVISIBLE);
                //check if aluno or prof
                if(user.getUserType() == 1) {
                    //prof
                    Log.i(TAG,"Professor logado.");
                    Fragment newFragment = new MateriaFragment();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, newFragment);
                    transaction.commit();
                } else {
                    Log.i(TAG,"Aluno logado.");
                    //aluno
                    Fragment newFragment = new AlunoFragment().setUserId(user.getId());
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, newFragment);
                    transaction.commit();
                }

                progressDialog.hideProgressDialog();
            } else {
                carregandoText.setText(carregandoText.getText() + ".");
            }
            }
        });
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
