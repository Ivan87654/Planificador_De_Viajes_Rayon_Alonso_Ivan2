package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity que muestra el panel de configuración mediante FragmentPreferencias.
 */
public class ActividadPreferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        // Cargar el fragment de preferencias en el contenedor
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorPreferencias, new FragmentPreferencias())
                .commit();
    }
}