package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActividadAcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_acerca_de);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView txtInfo = findViewById(R.id.txtInfoAcerca);

        String texto =
                "Aplicación: Planificador de Viajes\n" +
                        "Alumno: Iván Ratón Alonso\n" +
                        "Curso: 2º DAM – Programación Multimedia\n" +
                        "Descripción:\n" +
                        "Esta aplicación permite planificar viajes, visualizar destinos " +
                        "con sus fotografías, ver la información de cada viaje, añadir nuevos destinos, " +
                        "seleccionar actividades, gestionar recordatorios, valorar la experiencia " +
                        "y ajustar un presupuesto aproximado.\n\n" +
                        "https://github.com/Ivan87654/Planificador_De_Viajes_Rayon_Alonso_Ivan\n";

        txtInfo.setText(texto);
    }
}