package br.inatel.aluno.projecttcc;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import br.inatel.aluno.projecttcc.model.Aula;

import static android.content.Context.LOCATION_SERVICE;
import static br.inatel.aluno.projecttcc.service.RequestService.urlRoot;

public class AlunoFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    private static final String TAG = AlunoFragment.class.getSimpleName();

    ProgressDialogPresente progressDialog;
    FrameLayout frameLayout;
    Button textViewNenhumaChamada;
    TextView textViewInformations;
    SwipeRefreshLayout swipeRefreshLayout;
    Button buttonPresente;
    Long alunoId;

    private MapView mapView;
    private LocationManager mLocationManager;

    private GoogleMap mGoogleMap;
    private SeekBar mSeekBar;
    private Circle mapCircle;
    private CircleOptions mCircleOptions;

    private Marker mapMarker;




    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 1;


    public AlunoFragment setUserId(Long aluno) {
        this.alunoId = aluno;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aluno_content_layout, container, false);
        frameLayout = (FrameLayout) v.findViewById(R.id.frameLayout_aluno_id);
        textViewNenhumaChamada = (Button) v.findViewById(R.id.nenhuma_chamada_id);
        textViewInformations = (TextView) v.findViewById(R.id.txt_information);
        buttonPresente = (Button) v.findViewById(R.id.button_aluno_id);
        mapView = (MapView) v.findViewById(R.id.mapview_aluno);
        textViewInformations.setText("");
        progressDialog = new ProgressDialogPresente(getContext());
        mCircleOptions = new CircleOptions();
        textViewNenhumaChamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.showProgressDialog("Buscando chamada em andamento...");
                getAula();
            }
        });
        buttonPresente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mAula.getId();
                //alunoId
                progressDialog.showProgressDialog("Validando presença...");
                AndroidNetworking.post(urlRoot + "aulas/user/presente")
                        .setTag("TCC")
                        .addQueryParameter("aluno", String.valueOf(alunoId))
                        .addQueryParameter("aula", String.valueOf(mAula.getId()))
                        .addQueryParameter("presente", "true")
                        .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hideProgressDialog();
                        progressDialog.showProgressDialog("Buscando chamada em andamento...");
                        mAula= new Aula();
                        hideChamada();
                        getAula();
                    }

                    @Override
                    public void onError(ANError e) {
                        e.printStackTrace();
                        progressDialog.hideProgressDialog();
                    }
                });

                Toast.makeText(getContext(), "Info: " + mAula.getInfo() , Toast.LENGTH_SHORT).show();
            }
        });

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        progressDialog.showProgressDialog("Buscando chamada em andamento...");
        getAula();

        return v;
    }

    void showChamada() {
        frameLayout.setVisibility(View.VISIBLE);
        textViewNenhumaChamada.setVisibility(View.INVISIBLE);
    }

    void hideChamada() {
        frameLayout.setVisibility(View.INVISIBLE);
        textViewNenhumaChamada.setVisibility(View.VISIBLE);
    }
    void canShowButton(Location myloc){
        float[] results = new float[1];
        Location.distanceBetween(mAula.getLatitude(), mAula.getLongitude(), myloc.getLatitude(), myloc.getLongitude(), results);
        float distanceInMeters = results[0];
        if (distanceInMeters < mAula.getRaio()){
            buttonPresente.setVisibility(View.VISIBLE);
        } else {
            buttonPresente.setVisibility(View.INVISIBLE);
        }

        Log.i(TAG,distanceInMeters + " my raio is " + mAula.getRaio() );

    }

    Aula mAula = new Aula();
    void getAula() {
        AndroidNetworking.get(urlRoot + "users/materias/alunos/abertas")
                .setTag("TCC")
                .addQueryParameter("aluno", String.valueOf(alunoId))
                .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONArray items = (JSONArray) response.get("items");
                    Log.i(TAG, items.toString());

                    for (int n = 0; n < items.length(); n++) {
                        JSONObject object = items.getJSONObject(n);
                        mAula.setId(object.getLong("id"));
                        mAula.setStatus(object.getLong("status"));
                        mAula.setMateria(object.getLong("materia"));
                        mAula.setInfo(object.getString("info"));
                        mAula.setRaio(object.getLong("raio"));
                        mAula.setLatitude(object.getDouble("latitude"));
                        mAula.setHoraStart(object.getString("horaStart"));
                        mAula.setLongitude(object.getDouble("longitude"));
                    }
                    if (mAula.getId() == null){
                        hideChamada();
                    } else {
                        showChamada();
                    }

                    CameraUpdate cameraUpdate = CameraUpdateFactory
                            .newLatLngZoom(new LatLng(mAula.getLatitude(), mAula.getLongitude()), 18);

                    textViewInformations.setText("Info: " + mAula.getInfo() + "\n" +
                            "Horario: " + mAula.getHoraStart() + "\n" +
                            "Loc: " + mAula.getLatitude() + ":" + mAula.getLongitude());

                    mGoogleMap.animateCamera(cameraUpdate);
                    mCircleOptions.center(new LatLng(mAula.getLatitude(), mAula.getLongitude())).radius(mAula.getRaio()).
                            strokeWidth(2).fillColor(R.color.mapColor);
                    mapCircle = mGoogleMap.addCircle(mCircleOptions);
                    canShowButton(lastLocation);
                    progressDialog.hideProgressDialog();
                } catch (JSONException e) {
                    hideChamada();
                    progressDialog.hideProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError e) {
                progressDialog.hideProgressDialog();
                hideChamada();
                Log.i(TAG, e.getMessage());
                Log.i(TAG, e.getLocalizedMessage());
                Log.i(TAG, e.getCause() + "");
                Log.i(TAG, e.getStackTrace().toString());
                e.printStackTrace();
                Log.i(TAG, "Erro:" + e.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private Location lastLocation;
    @Override
    public void onLocationChanged(Location location) {
        if (location == null) return;
        lastLocation = location;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (mapMarker == null) {
            mapMarker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Você"));

        } else {
            mapMarker.setPosition(latLng);
        }
        if (mAula.getId() != null){
            canShowButton(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MapsInitializer.initialize(this.getContext());
        }
        //mapCircle = mGoogleMap.addCircle(mCircleOptions);

        onLocationChanged(mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
    }
}
