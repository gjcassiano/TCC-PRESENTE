//package br.inatel.aluno.projecttcc.service;
//
//import android.os.Build;
//import android.util.Log;
//
//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.common.Priority;
//import com.androidnetworking.error.ANError;
//import com.androidnetworking.interfaces.JSONObjectRequestListener;
//import com.example.unknown.myapplication.backend.presente.model.User;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class UserService  extends RequestService {
//
////    private static final String TAG = UserService.class.getSimpleName();
////    private User user;
////
////    public UserService() {
////        super("users/");
////    }
////
////    public void getUserExecute(String matricula, String name){
////        AndroidNetworking.get(this.urlRoot + "users/get")
////                .setTag("TCC")
////                .addQueryParameter("deviceSerial", Build.SERIAL )
////                .addQueryParameter("matricula",matricula)
////                .addQueryParameter("name", name)
////                .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
////                @Override
////                public void onResponse(JSONObject response) {
////                    try {
////                        user = new User();
////                        JSONArray items = (JSONArray) response.get("items");
////                        Log.i(TAG,items.toString());
////                        for(int n = 0; n < items.length(); n++)
////                        {
////                            JSONObject object = items.getJSONObject(n);
////                            user.setId(object.getLong("id"));
////                            user.setName(object.getString("name"));
////                            user.setSerialNumber(object.getString("serialNumber"));
////                            user.setMatricula(object.getLong("matricula"));
////                            user.setUserType(object.getLong("userType"));
////                        }
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////
////                @Override
////                public void onError(ANError anError) {
////                    Log.i(TAG, "Erro:" + anError.getMessage());
////                }
////        });
////    }
////    public User getUser(){
////        return this.user;
////    }
//
//}
