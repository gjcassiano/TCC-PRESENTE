package br.inatel.aluno.projecttcc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.inatel.aluno.projecttcc.adapter.PresentesAdapter;
import br.inatel.aluno.projecttcc.listener.RecyclerTouchListener;
import br.inatel.aluno.projecttcc.model.Aluno;
import br.inatel.aluno.projecttcc.model.Aula;
import br.inatel.aluno.projecttcc.model.Materia;


public class PresentesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private List<Aluno> alunoList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PresentesAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mPercenteText;

    private Materia mMateria;
    private Aula mAula;

    public PresentesFragment getInstance(Materia materia, Aula aula){
        mMateria = materia;
        mAula = aula;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.presentes_content_layout, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_present);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_id);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mPercenteText = (TextView) v.findViewById(R.id.percent_text);

        mAdapter = new PresentesAdapter(alunoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Aluno aluno = alunoList.get(position);
                aluno.setPresente(!aluno.isPresente());
                mAdapter.notifyDataSetChanged();
                updatePercentText();
//                Toast.makeText(getContext(),materia.getId() + " is selected!", Toast.LENGTH_SHORT).show();
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
        alunoList.add(new Aluno(1,"Giovani Cassiano", "828",true));
        alunoList.add(new Aluno(2,"Gustavo Cividatti", "828",true));
        alunoList.add(new Aluno(3,"Thiago Rocha", "828",true));
        alunoList.add(new Aluno(4,"Gabriela Marques", "828",false));
        mAdapter.notifyDataSetChanged();
    }
    void updatePercentText(){
        int total = alunoList.size();
        int presentes = 0;
        for (Aluno a : alunoList) {
            if (a.isPresente()) presentes++;
        }
        mPercenteText.setText(presentes + " / " + total);
        mPercenteText.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_in));
        if (total != presentes) {
            mPercenteText.setTextColor(Color.GRAY);
        } else {
            mPercenteText.setTextColor(Color.GREEN);
        }
        if (presentes == 0){
            mPercenteText.setTextColor(Color.RED);
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
//        alunoList.add(new Aluno(4,"Gabriela Marques", "828",new Random().nextBoolean()));
        mAdapter.notifyDataSetChanged();
    }
}
