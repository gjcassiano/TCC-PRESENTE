package br.inatel.aluno.projecttcc.service;

import android.util.Log;

import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.inatel.aluno.projecttcc.model.Materia;

public class MateriaService extends RequestService{

    private static final String TAG = MateriaService.class.getSimpleName();
    private List<Materia> materias = new ArrayList<>();

    public MateriaService() {
        super("materias/");
    }

    @Override
    public void onResponse(JSONObject response) {
        super.onResponse(response);
        try {

            JSONArray items = (JSONArray) response.get("items");
            for(int n = 0; n < items.length(); n++)
            {
                JSONObject object = items.getJSONObject(n);
                Log.i(TAG, items.toString() + "" );
                materias.add(new Materia(
                        object.getLong("id"),
                        object.getString("nome"),
                        object.getString("sigla"),
                        ""
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Materia> getMaterias(){
        return materias;
    }
}
