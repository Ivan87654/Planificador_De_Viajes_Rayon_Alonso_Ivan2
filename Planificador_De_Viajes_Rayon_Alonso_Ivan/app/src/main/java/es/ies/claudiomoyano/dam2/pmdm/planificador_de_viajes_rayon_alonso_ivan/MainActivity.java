package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvViajes;
    private AdaptadorViajes adaptadorViajes;
    private List<Viaje> listaViajes;

    // Código de petición para startActivityForResult
    private static final int CODIGO_NUEVO_VIAJE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvViajes = findViewById(R.id.rv_viajes);

        listaViajes = new ArrayList<>();
        cargarViajesEjemplo();

        rvViajes.setLayoutManager(new LinearLayoutManager(this));

        adaptadorViajes = new AdaptadorViajes(listaViajes);
        rvViajes.setAdapter(adaptadorViajes);


        adaptadorViajes.setOnItemClickListener(new AdaptadorViajes.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Viaje viajeSeleccionado = listaViajes.get(position);

                Intent intent = new Intent(MainActivity.this, ActividadDetalleViaje.class);
                intent.putExtra("titulo", viajeSeleccionado.getTitulo());
                intent.putExtra("fecha", viajeSeleccionado.getFechaSalida());
                intent.putExtra("imagen", viajeSeleccionado.getIdRecursoImagen());
                intent.putExtra("descripcion", viajeSeleccionado.getDescripcion());

                startActivity(intent);
            }
        });

        // Menú contextual
        adaptadorViajes.setOnItemMenuListener(new AdaptadorViajes.OnItemMenuListener() {
            @Override
            public void onEditar(int position) {
                Viaje viajeSeleccionado = listaViajes.get(position);

                Intent intent = new Intent(MainActivity.this, ActividadDetalleViaje.class);
                intent.putExtra("titulo", viajeSeleccionado.getTitulo());
                intent.putExtra("fecha", viajeSeleccionado.getFechaSalida());
                intent.putExtra("imagen", viajeSeleccionado.getIdRecursoImagen());
                intent.putExtra("descripcion", viajeSeleccionado.getDescripcion());

                startActivity(intent);
            }

            @Override
            public void onEliminar(int position) {
                listaViajes.remove(position);
                adaptadorViajes.notifyItemRemoved(position);
            }
        });
    }


    private void cargarViajesEjemplo() {

        listaViajes.add(new Viaje(
                "Roma - El Coliseum",
                "12/04/2025",
                R.drawable.roma,
                "Visita una de las Siete Maravillas del Mundo Moderno. El Coliseo es el mayor anfiteatro del Imperio Romano y un símbolo eterno de la ciudad. Descubre su historia, los gladiadores y el corazón del antiguo deporte romano."
        ));

        listaViajes.add(new Viaje(
                "París - La Torre Eiffel",
                "20/07/2025",
                R.drawable.paris,
                "La Torre Eiffel es el monumento más famoso de Francia. Disfruta de París desde sus miradores, pasea por los Campos de Marte y explora la romántica atmósfera de la Ciudad de la Luz."
        ));

        listaViajes.add(new Viaje(
                "Londres - El Big Ben",
                "05/10/2025",
                R.drawable.londres,
                "El Big Ben y el Palacio de Westminster forman uno de los iconos más reconocibles del Reino Unido. Recorre la zona del Támesis, descubre la historia del parlamento británico y vive el ambiente cosmopolita de Londres."
        ));

        listaViajes.add(new Viaje(
                "Nueva York – Manhattan",
                "15/03/2026",
                R.drawable.nuevayork,
                "Manhattan es el corazón de Nueva York. Recorre Times Square, Central Park, Broadway y los rascacielos más famosos del mundo en la ciudad que nunca duerme."
        ));

        listaViajes.add(new Viaje(
                "Berlín – Ruta histórica",
                "09/09/2025",
                R.drawable.berlin,
                "En Berlín podrás visitar el Muro, el Reichstag, la Puerta de Brandeburgo y museos llenos de historia. Una ciudad moderna que mezcla memoria, innovación y cultura europea."
        ));

        listaViajes.add(new Viaje(
                "Egipto – Pirámides de Giza",
                "02/02/2026",
                R.drawable.egipto,
                "Explora la necrópolis más famosa del planeta. Las Pirámides de Giza y la Gran Esfinge son vestigios milenarios del Antiguo Egipto, ideales para amantes de la historia y la arqueología."
        ));
    }

    // ---------------- MENÚ SUPERIOR ----------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.opcion_nuevo_viaje) {

            Intent intent = new Intent(MainActivity.this, ActividadNuevoViaje.class);
            startActivityForResult(intent, CODIGO_NUEVO_VIAJE);
            return true;

        } else if (id == R.id.opcion_acerca_de) {

            Intent intent = new Intent(MainActivity.this, ActividadAcercaDe.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // -------- RECIBIR EL VIAJE NUEVO DESDE ActividadNuevoViaje --------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_NUEVO_VIAJE && resultCode == RESULT_OK && data != null) {

            String titulo = data.getStringExtra("titulo");
            String fecha = data.getStringExtra("fecha");
            String descripcion = data.getStringExtra("descripcion");

            // Imagen por defecto para los viajes nuevos
            int imagen = R.drawable.viaje; // puedes cambiarla por otra

            Viaje nuevoViaje = new Viaje(titulo, fecha, imagen, descripcion);
            listaViajes.add(nuevoViaje);

            adaptadorViajes.notifyItemInserted(listaViajes.size() - 1);
        }
    }
}