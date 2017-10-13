package br.inatel.aluno.projecttcc.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.inatel.aluno.projecttcc.R;
import br.inatel.aluno.projecttcc.model.Materia;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MyViewHolder> {

    private List<Materia> materiasList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, sigla, turma;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.materia_nome);
            sigla = (TextView) view.findViewById(R.id.materia_sigla);
            turma = (TextView) view.findViewById(R.id.materia_turma);
        }
    }



    public MateriaAdapter(List<Materia> materias) {
        this.materiasList = materias;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.materia_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Materia materia = materiasList.get(position);
        holder.nome.setText(materia.getNome());
        holder.sigla.setText(materia.getSigla());
        holder.turma.setText(materia.getTurma());
    }

    @Override
    public int getItemCount() {
        return materiasList.size();
    }
}
