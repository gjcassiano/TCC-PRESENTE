package br.inatel.aluno.projecttcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.inatel.aluno.projecttcc.adapter.MateriaAdapter;
import br.inatel.aluno.projecttcc.listener.RecyclerTouchListener;
import br.inatel.aluno.projecttcc.model.Materia;

public class MateriaFragment extends Fragment {
    private List<Materia> materiaList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MateriaAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.materia_content_layout, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        mAdapter = new MateriaAdapter(materiaList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Materia materia = materiaList.get(position);
                changeFragment(materia);
//                Toast.makeText(getContext(),materia.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
        return v;

    }

    private void prepareMovieData() {
        materiaList.add(new Materia(1,"Calculo 1", "NB001","A"));
        materiaList.add(new Materia(2,"Calculo 1", "NB001","B"));
        materiaList.add(new Materia(3,"Calculo 1", "NB001","C"));
        materiaList.add(new Materia(4,"Calculo 2", "NB001","A"));
        materiaList.add(new Materia(5,"Calculo 2", "NB001","B"));
        materiaList.add(new Materia(6,"Calculo 2", "NB001","C"));
        materiaList.add(new Materia(7,"Calculo 3", "NB001","A"));
        materiaList.add(new Materia(8,"Calculo 3", "NB001","B"));
        materiaList.add(new Materia(9,"Calculo 3", "NB001","C"));
        materiaList.add(new Materia(10,"Fisica 1", "NB001","A"));
        materiaList.add(new Materia(11,"Fisica 1", "NB001","B"));
        materiaList.add(new Materia(12,"Fisica 1", "NB001","C"));
        materiaList.add(new Materia(13,"Fisica 2", "NB001","A"));
        materiaList.add(new Materia(14,"Fisica 2", "NB001","B"));
        materiaList.add(new Materia(15,"Fisica 2", "NB001","C"));
        materiaList.add(new Materia(16,"Fisica 3", "NB001","A"));
        materiaList.add(new Materia(17,"Fisica 3", "NB001","B"));
        materiaList.add(new Materia(18,"Fisica 3", "NB001","C"));
        materiaList.add(new Materia(19,"Atividade Complementar 1", "NB001","A"));
        materiaList.add(new Materia(20,"Atividade Complementar 1", "NB001","B"));
        materiaList.add(new Materia(21,"Atividade Complementar 1", "NB001","C"));
        mAdapter.notifyDataSetChanged();
    }

    void changeFragment(Materia materia){

        Fragment newFragment = new ChamandaFragment().getInstance(materia);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

        transaction.replace(R.id.frame_container, newFragment);
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
