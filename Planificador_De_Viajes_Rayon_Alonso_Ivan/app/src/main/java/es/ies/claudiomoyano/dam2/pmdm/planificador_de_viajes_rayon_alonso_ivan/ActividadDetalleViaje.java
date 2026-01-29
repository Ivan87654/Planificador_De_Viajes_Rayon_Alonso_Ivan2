package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActividadDetalleViaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_detalle_viaje);

        // OCULTAR LA BARRA SUPERIOR EN ESTA PANTALLA
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView imgPortada = findViewById(R.id.img_portada_detalle);
        TextView txtTitulo = findViewById(R.id.txt_titulo_detalle);
        TextView txtFecha = findViewById(R.id.txt_fecha_detalle);
        TextView txtDescripcion = findViewById(R.id.txt_descripcion_detalle);

        String titulo = getIntent().getStringExtra("titulo");
        String fecha = getIntent().getStringExtra("fecha");
        String descripcion = getIntent().getStringExtra("descripcion");
        int idImagen = getIntent().getIntExtra("imagen", 0);

        txtTitulo.setText(titulo);
        txtFecha.setText(fecha);
        txtDescripcion.setText(descripcion);

        if (idImagen != 0) {
            imgPortada.setImageResource(idImagen);
        }
    }
}