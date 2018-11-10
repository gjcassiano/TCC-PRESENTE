package br.inatel.aluno.projecttcc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationListener;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
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
import com.google.api.client.util.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.inatel.aluno.projecttcc.model.Aula;
import br.inatel.aluno.projecttcc.model.Materia;

import static android.content.Context.LOCATION_SERVICE;
import static br.inatel.aluno.projecttcc.service.RequestService.urlRoot;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private static final String TAG = MapViewFragment.class.getSimpleName();

    private static final LatLng INATEL_POSITION = new LatLng(-22.257027, -45.695759);

    private MapView mapView;
    private LocationManager mLocationManager;
    private Context mContext;
    private TextView mLocationTxt;

    private GoogleMap mGoogleMap;
    private SeekBar mSeekBar;
    private Circle mapCircle;
    private CircleOptions mCircleOptions;

    private Marker mapMarker;

    private int mRadioMinimo = 10;


    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 1;

    private Materia mMateria;
    private Aula mAula;

    public MapViewFragment getInstance(Materia materia, Aula aula) {
        mMateria = materia;
        mAula = aula;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.map_layout, container, false);
        mapView = (MapView) v.findViewById(R.id.mapview);
        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);

        mSeekBar = (SeekBar) v.findViewById(R.id.seekBar_id);
        mCircleOptions = new CircleOptions();
        mCircleOptions.center(INATEL_POSITION).radius(mRadioMinimo + 25).
                strokeWidth(2).fillColor(R.color.mapColor);
        mLocationTxt = (TextView) v.findViewById(R.id.txt_location);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mapCircle.setRadius(mRadioMinimo + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAula.setRaio((long) (mRadioMinimo + seekBar.getProgress()));
                Toast.makeText(mContext, String.format("Raio ajustado para %d metros.", mRadioMinimo + seekBar.getProgress()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button button = (Button) v.findViewById(R.id.btn_inicar_chamada);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarChamada();
            }
        });

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        return v;
    }

    private void iniciarChamada() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("Iniciar a chamada?");
        alertDialogBuilder.setPositiveButton("sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(mContext, "Iniciando chamada.",
                                Toast.LENGTH_SHORT).show();
                        executeChamada();
//                        changeFragment(mMateria);
                    }
                });

        alertDialogBuilder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MapsInitializer.initialize(this.getContext());
        }
        mapCircle = mGoogleMap.addCircle(mCircleOptions);

        if (ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        onLocationChanged(mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
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
        mapCircle.setCenter(latLng);

        goCamera(latLng);
        mLocationTxt.setText("Localização:" + location.getLatitude() + ", " + location.getLongitude());
    }
    private void goCamera(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mGoogleMap.animateCamera(cameraUpdate);
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

    private void executeChamada() {
            if (mAula.getRaio() == null){
                mAula.setRaio((long) mRadioMinimo);
            }

            mAula.setLatitude(lastLocation.getLatitude());
            mAula.setLongitude(lastLocation.getLongitude());
            Log.i(TAG, String.valueOf(new DateTime( mAula.getDataAula())));
            Log.i(TAG, String.valueOf(mMateria.getId()));
            Log.i(TAG, mAula.getInfo());
            Log.i(TAG, String.valueOf(mAula.getRaio()));
            Log.i(TAG, mAula.getHoraStart());
            Log.i(TAG, String.valueOf(mAula.getLatitude()));
            Log.i(TAG, String.valueOf(mAula.getLongitude()));

            AndroidNetworking.post(urlRoot + "aulas/create")
                    .setTag("TCC")
                    .addQueryParameter("materia", String.valueOf(mMateria.getId()))
                    .addQueryParameter("dataAula",String.valueOf(new DateTime( mAula.getDataAula())))
                    .addQueryParameter("info", mAula.getInfo())
                    .addQueryParameter("raio", String.valueOf(mAula.getRaio()))
                    .addQueryParameter("horaStart", mAula.getHoraStart())
                    .addQueryParameter("latitude", String.valueOf(mAula.getLatitude()))
                    .addQueryParameter("longitude", String.valueOf(mAula.getLongitude()))
                    .addQueryParameter("status", "0")
                    .setPriority(Priority.IMMEDIATE).build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray items = (JSONArray) response.get("items");
                        Log.i(TAG,items.toString());

                        for(int n = 0; n < items.length(); n++){
                            JSONObject object = items.getJSONObject(n);
                            mAula.setId(object.getLong("id"));
                            mAula.setStatus(object.getLong("status"));
                        }

                        changeFragment(mMateria, mAula);

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError e) {
                    e.printStackTrace();
//                    Log.i(TAG, e.getMessage());
//                    Log.i(TAG, e.getLocalizedMessage());
//                    Log.i(TAG, e.getCause() + "");
//                    Log.i(TAG, e.getStackTrace().toString());
//                    e.printStackTrace();
//                    Log.i(TAG, "Erro:" + e.getMessage());
                }
            });
        }


    void changeFragment(Materia materia, Aula aula ){

        Fragment newFragment = new PresentesFragment().getInstance(materia, aula);

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