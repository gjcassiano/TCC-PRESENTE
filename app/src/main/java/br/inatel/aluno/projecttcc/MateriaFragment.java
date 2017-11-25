package br.inatel.aluno.projecttcc;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.inatel.aluno.projecttcc.adapter.MateriaAdapter;
import br.inatel.aluno.projecttcc.listener.RecyclerTouchListener;
import br.inatel.aluno.projecttcc.model.Materia;
import br.inatel.aluno.projecttcc.service.MateriaService;

public class MateriaFragment extends Fragment{
    private static final String TAG = MateriaFragment.class.getSimpleName();
    private List<Materia> materiaList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MateriaAdapter mAdapter;
    ProgressDialogPresente progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.materia_content_layout, container, false);
//        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_materia);
//        swipeRefreshLayout.setOnRefreshListener(this);
        progressDialog = new ProgressDialogPresente(getContext());
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

        final MateriaService materiaService = new MateriaService();
        materiaService.execute("list");
        progressDialog.showProgressDialog("Buscando materias...");
        updateList(materiaService,500);
        updateList(materiaService,1000);
        updateList(materiaService,1500);
        updateList(materiaService,2000);
        updateList(materiaService,3000);
        updateList(materiaService,5000);
        updateList(materiaService,10000);
        Log.i(TAG, Build.SERIAL);
        return v;
    }
    private void updateList(final MateriaService materiaService, int time){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (materiaService.getMaterias().size()>0){
                    materiaList.clear();
                    materiaList.addAll(materiaService.getMaterias());
                    mAdapter.notifyDataSetChanged();
                    progressDialog.hideProgressDialog();
                }
            }
        },time);
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
