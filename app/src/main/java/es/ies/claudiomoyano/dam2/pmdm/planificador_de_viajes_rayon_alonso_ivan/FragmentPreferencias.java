package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

/**
 * Fragment que gestiona el panel de preferencias de la aplicación.

 */
public class FragmentPreferencias extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // Cargar las preferencias desde el archivo XML
        setPreferencesFromResource(R.xml.preferencias, rootKey);
    }
}