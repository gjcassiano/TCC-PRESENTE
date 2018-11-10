package br.inatel.aluno.projecttcc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.unknown.myapplication.backend.presente.model.User;
import com.google.api.client.util.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.inatel.aluno.projecttcc.adapter.PresentesAdapter;
import br.inatel.aluno.projecttcc.listener.RecyclerTouchListener;
import br.inatel.aluno.projecttcc.model.Aluno;
import br.inatel.aluno.projecttcc.model.Aula;
import br.inatel.aluno.projecttcc.model.Materia;

import static br.inatel.aluno.projecttcc.service.RequestService.urlRoot;


public class PresentesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = PresentesFragment.class.getSimpleName();

    private List<User> alunoList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private PresentesAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mPercenteText;
    private Button mEndAula;

    private Materia mMateria;
    private Aula mAula;

    public PresentesFragment getInstance(Materia materia, Aula aula) {
        mMateria = materia;
        mAula = aula;
        return this;
    }

    ProgressDialogPresente progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.presentes_content_layout, container, false);

        progressDialog = new ProgressDialogPresente(getContext());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_present);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_id);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mPercenteText = (TextView) v.findViewById(R.id.percent_text);
        mEndAula =(Button) v.findViewById(R.id.btn_end_aula);
        mEndAula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.showProgressDialog("Finalizando aula...");
                AndroidNetworking.post(urlRoot + "aulas/finalizar")
                        .setTag("TCC")
                        .addQueryParameter("aula", String.valueOf(mAula.getId()))
                        .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hideProgressDialog();
                        getFragmentManager().popBackStack();
                        getFragmentManager().popBackStack();
                        getFragmentManager().popBackStack();
                    }

                    @Override
                    public void onError(ANError e) {
                        e.printStackTrace();
                        progressDialog.hideProgressDialog();
                    }
                });
            }
        });

        mAdapter = new PresentesAdapter(alunoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                User aluno = alunoList.get(position);


                progressDialog.showProgressDialog("Validando presença...");
                AndroidNetworking.post(urlRoot + "aulas/user/presente")
                        .setTag("TCC")
                        .addQueryParameter("aluno", String.valueOf(aluno.getId()))
                        .addQueryParameter("aula", String.valueOf(mAula.getId()))
                        .addQueryParameter("presente", aluno.getPresente() != true ? "true" : "false" )
                        .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hideProgressDialog();
                    }

                    @Override
                    public void onError(ANError e) {
                        e.printStackTrace();
                        progressDialog.hideProgressDialog();
                    }
                });

                //aluno.setPresente(!aluno.getPresente());
                prepareAlunos();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        mRecyclerView.setAdapter(mAdapter);

        prepareAlunos();
        updatePercentText();
        return v;

    }

    private void prepareAlunos() {
        AndroidNetworking.get(urlRoot + "aulas/user/all")
                .setTag("TCC")
                .addQueryParameter("aula", String.valueOf(mAula.getId()))
                .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    alunoList.clear();
                    JSONArray items = (JSONArray) response.get("items");
                    Log.i(TAG, items.toString());
                    for (int n = 0; n < items.length(); n++) {
                        JSONObject object = items.getJSONObject(n);
                        User user = new User();
                        user.setId(object.getLong("id"));
                        user.setName(object.getString("name"));
                        user.setSerialNumber(object.getString("serialNumber"));
                        user.setMatricula(object.getLong("matricula"));
                        user.setUserType(object.getLong("userType"));
                        user.setPresente(object.getBoolean("presente"));
                        alunoList.add(user);
                    }
                    mAdapter.notifyDataSetChanged();
                    updatePercentText();
//                    changeFragment(mMateria, mAula);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                anError.printStackTrace();
            }
        });
//        alunoList.add(new Aluno(1,"Giovani Cassiano", "828",true));
//        alunoList.add(new Aluno(2,"Gustavo Cividatti", "828",true));
//        alunoList.add(new Aluno(3,"Thiago Rocha", "828",true));
//        alunoList.add(new Aluno(4,"Gabriela Marques", "828",false));
//

        mAdapter.notifyDataSetChanged();

    }

    void updatePercentText() {
        int total = alunoList.size();
        int presentes = 0;
        for (User a : alunoList) {
            if (a.getPresente()) presentes++;
        }
        mPercenteText.setText(presentes + " / " + total);
        mPercenteText.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_in));
        if (total != presentes) {
            mPercenteText.setTextColor(Color.GRAY);
        } else {
            mPercenteText.setTextColor(Color.GREEN);
        }
        if (presentes == 0) {
            mPercenteText.setTextColor(Color.RED);
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
//        alunoList.add(new Aluno(4,"Gabriela Marques", "828",new Random().nextBoolean()));
        prepareAlunos();
        mAdapter.notifyDataSetChanged();
    }
}
