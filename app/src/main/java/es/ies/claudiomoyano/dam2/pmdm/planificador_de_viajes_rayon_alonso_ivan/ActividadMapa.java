package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Actividad que muestra un mapa de Google Maps con un marcador.
 */
public class ActividadMapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtener el fragmento de Google Maps desde el layout
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        // Cargar el mapa
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Coordenadas de Roma
        LatLng roma = new LatLng(41.8902, 12.4922);

        // Añadir un marcador en el mapa
        mMap.addMarker(
                new MarkerOptions()
                        .position(roma)
                        .title("Roma - Coliseo")
        );

        // Mover la cámara
        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(roma, 12f)
        );
    }
}