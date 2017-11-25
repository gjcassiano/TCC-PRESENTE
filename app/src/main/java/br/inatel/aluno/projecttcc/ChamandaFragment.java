package br.inatel.aluno.projecttcc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.unknown.myapplication.backend.presente.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import br.inatel.aluno.projecttcc.model.Aula;
import br.inatel.aluno.projecttcc.model.Materia;

import static android.content.ContentValues.TAG;
import static br.inatel.aluno.projecttcc.service.RequestService.urlRoot;

public class ChamandaFragment extends Fragment {

    private TextView mObserv;
    private TextView mInfo;
    private static TextView mHora;
    private static TextView mData;
    private TextView mNome;
    private TextView mSigla;
    private TextView mTurma;
    private Button mLocal;


    private Materia mMateria;
    public ChamandaFragment getInstance(Materia materia){
        mMateria = materia;
        return this;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_chamada, container, false);
        mObserv = (TextView) v.findViewById(R.id.chamada_observa);
        mInfo = (TextView) v.findViewById(R.id.chamada_info);
        mHora = (TextView) v.findViewById(R.id.txt_horario);
        mData = (TextView) v.findViewById(R.id.txt_data);
        mLocal = (Button) v.findViewById(R.id.btn_local);

        mNome = (TextView) v.findViewById(R.id.materia_nome);
        mSigla = (TextView) v.findViewById(R.id.materia_sigla);
        mTurma = (TextView) v.findViewById(R.id.materia_turma);
        mNome.setText(mMateria.getNome());
        mSigla.setText(mMateria.getSigla());
        mTurma.setText(mMateria.getTurma());

        mObserv.setText("Observações");

        mHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker mTimePicker = new TimePicker();
                mTimePicker.show(getFragmentManager(), "Selecione o horario");
            }
        });

        mData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment mDatePicker = new DatePickerFragment();
                mDatePicker.show(getFragmentManager(), "Selecione a data");
            }
        });

        mLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });

        return v;
    }

    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            mHora.setText(String.valueOf(hourOfDay) + ":" + (minute<10 ? "0" + String.valueOf(minute):String.valueOf(minute)));
        }
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            mData.setText((day<10 ? "0" + String.valueOf(day):String.valueOf(day))+ "/" +
                    (month<10 ? "0" + String.valueOf(month):String.valueOf(month))+ "/" +
                    String.valueOf(year));
        }
    }

    Aula mAula = new Aula();
    void changeFragment(){

        mAula.setInfo(mInfo.getText().toString());
        mAula.setHoraStart(mHora.getText().toString());
        mAula.setDataAula(new Date(mData.getText().toString()));
        changePage();
    }



    private void changePage(){
        Fragment newFragment = new MapViewFragment().getInstance(mMateria, mAula);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.frame_container, newFragment);
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
