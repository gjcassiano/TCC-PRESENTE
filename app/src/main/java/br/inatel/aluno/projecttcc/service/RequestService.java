package br.inatel.aluno.projecttcc.service;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.inatel.aluno.projecttcc.model.Materia;

public class RequestService implements RequestServiceInterface, JSONObjectRequestListener {
    private static final String TAG = RequestService.class.getSimpleName();
    public static String urlRoot = "https://groovy-works-180421.appspot.com/_ah/api/presente/v1/";
    //https://groovy-works-180421.appspot.com/_ah/api/users/v1/users/list

    private ANRequest.GetRequestBuilder requestBuilder;
    private String pathFather;

    public RequestService(String pathFather){
        this.pathFather = pathFather;
    }

    @Override
    public void execute(String path){
        Log.i(TAG,"Execute :" + urlRoot + pathFather + path);
        AndroidNetworking.get(urlRoot + pathFather + path)
                .setTag("TCC")
                .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(this);
    }

    @Override
    public void onResponse(JSONObject response) {
            Log.i(TAG, response.toString());
    }

    @Override
    public void onError(ANError anError) {
       Log.d(TAG, anError.getErrorDetail());
    }
}
