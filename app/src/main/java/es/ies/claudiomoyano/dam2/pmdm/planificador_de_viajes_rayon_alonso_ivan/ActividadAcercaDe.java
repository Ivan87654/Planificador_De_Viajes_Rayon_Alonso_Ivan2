package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActividadAcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Carga el layout de la actividad
        setContentView(R.layout.activity_actividad_acerca_de);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Obtener referencia al TextView del layout
        TextView txtInfo = findViewById(R.id.txtInfoAcerca);

        // Texto informativo
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

        // Mostrar el texto en pantalla
        txtInfo.setText(texto);
    }
}