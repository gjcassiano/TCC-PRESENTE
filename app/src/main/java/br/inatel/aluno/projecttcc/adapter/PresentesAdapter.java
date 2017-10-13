package br.inatel.aluno.projecttcc.adapter;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.inatel.aluno.projecttcc.R;
import br.inatel.aluno.projecttcc.model.Aluno;

public class PresentesAdapter extends RecyclerView.Adapter<PresentesAdapter.MyViewHolder> {

    private List<Aluno> alunosList;
    private Drawable mCheckDrawable;
    private Drawable mCloseDrawable;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, matricula;
        public ImageButton presente;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.presentes_nome);
            matricula = (TextView) view.findViewById(R.id.presentes_matricula);
            presente = (ImageButton) view.findViewById(R.id.presentes_presente);
        }

    }



    public PresentesAdapter(List<Aluno> alunos) {
        this.alunosList = alunos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.presentes_layout, parent, false);

        mCheckDrawable = parent.getContext().getResources().getDrawable(R.drawable.ic_check_black_24dp);
        mCheckDrawable.setTint(Color.GREEN);
        mCloseDrawable = parent.getContext().getResources().getDrawable(R.drawable.ic_close_black_24dp);
        mCloseDrawable.setTint(Color.RED);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Aluno aluno= alunosList.get(position);
        holder.nome.setText(aluno.getNome());
        holder.matricula.setText(aluno.getMatricula());

        if (aluno.isPresente()){
            holder.presente.setBackground(mCheckDrawable);
        } else {
            holder.presente.setBackground(mCloseDrawable);
        }
    }

    @Override
    public int getItemCount() {
        return alunosList.size();
    }
}
