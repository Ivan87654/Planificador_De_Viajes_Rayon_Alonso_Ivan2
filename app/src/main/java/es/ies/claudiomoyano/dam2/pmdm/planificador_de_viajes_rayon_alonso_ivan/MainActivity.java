package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.notificaciones.Notificaciones;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.Viaje;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.servicios.ServicioBateriaSMS;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // RecyclerView para mostrar los viajes
    private RecyclerView rvViajes;

    // Adaptador para transformar los datos en vistas
    private AdaptadorViajes adaptadorViajes;

    // Fuente de datos
    private List<Viaje> listaViajes;

    // Código usado para identificar el resultado al crear un nuevo viaje
    private static final int CODIGO_NUEVO_VIAJE = 1;

    private android.media.MediaPlayer mediaPlayer;

    private String rol = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recibir rol desde LoginActivity
        Intent i = getIntent();
        if (i != null && i.hasExtra("ROL")) {
            rol = i.getStringExtra("ROL");
        }

        // Cargar FragmentCabecera y pasarle el rol
        FragmentCabecera cabecera = new FragmentCabecera();

        Bundle bundle = new Bundle();
        bundle.putString("ROL", rol);
        cabecera.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorCabecera, cabecera)
                .commit();

        // Enlazar RecyclerView con su vista del layout
        rvViajes = findViewById(R.id.rv_viajes);

        // Inicializar lista y cargar datos de ejemplo
        listaViajes = new ArrayList<>();
        cargarViajesEjemplo();

        // Asignar un LayoutManager
        rvViajes.setLayoutManager(new LinearLayoutManager(this));

        // Crear adaptador y asignarlo al RecyclerView
        adaptadorViajes = new AdaptadorViajes(listaViajes);
        rvViajes.setAdapter(adaptadorViajes);

        // Listener para pulsación normal sobre un elemento del RecyclerView

        adaptadorViajes.setOnItemClickListener(new AdaptadorViajes.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Viaje viajeSeleccionado = listaViajes.get(position);

                // Crear Intent explícito para abrir la actividad de detalles

                Intent intent = new Intent(MainActivity.this, ActividadDetalleViaje.class);

                // Enviar datos mediante putExtra
                intent.putExtra("titulo", viajeSeleccionado.getTitulo());
                intent.putExtra("fecha", viajeSeleccionado.getFechaSalida());
                intent.putExtra("imagen", viajeSeleccionado.getIdRecursoImagen());
                intent.putExtra("descripcion", viajeSeleccionado.getDescripcion());

                startActivity(intent);
            }
        });

        // Listener para eliminar un viaje desde el menú contextual
        adaptadorViajes.setOnItemMenuListener(new AdaptadorViajes.OnItemMenuListener() {
            @Override
            public void onEliminar(int position) {

                String titulo = listaViajes.get(position).getTitulo();

                listaViajes.remove(position);
                adaptadorViajes.notifyItemRemoved(position);

                Notificaciones.notificarBorrado(MainActivity.this, titulo);
            }
        });
    }




     //Metodo que carga datos de ejemplo a la lista
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




     //Carga el menú de opciones en la Activity.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if ("admin".equals(rol)) {
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_usuario, menu);
        }
        return true;
    }


     //Gestiona la pulsación sobre las opciones del menú.

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



        } else if (id == R.id.opcion_contactos) {

            Intent intent = new Intent(MainActivity.this, ActividadContactos.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.opcion_ajustes) {

            Intent intent = new Intent(MainActivity.this, ActividadPreferencias.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.opcion_mapa) {

            Intent intent = new Intent(MainActivity.this, ActividadMapa.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.opcion_video) {
            Intent intent = new Intent(MainActivity.this, ActividadVideo.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.opcion_audio) {

            // Reproducir audio explicativo
            if (mediaPlayer == null) {
                mediaPlayer = android.media.MediaPlayer.create(this, R.raw.audio_guia);
            }

            if (mediaPlayer != null) {
                mediaPlayer.start();
                Toast.makeText(this, "Reproduciendo audio...", Toast.LENGTH_SHORT).show();
            }
        }

            return super.onOptionsItemSelected(item);
    }


     //Metodo que recibe el resultado enviado desde ActividadNuevoViaje

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Confirmar que se trata del resultado correcto
        if (requestCode == CODIGO_NUEVO_VIAJE && resultCode == RESULT_OK && data != null) {

            // Recuperar datos enviados por la actividad
            String titulo = data.getStringExtra("titulo");
            String fecha = data.getStringExtra("fecha");
            String descripcion = data.getStringExtra("descripcion");

            // Imagen por defecto
            int imagen = R.drawable.viaje;

            // Añadir nuevo viaje a la lista
            Viaje nuevoViaje = new Viaje(titulo, fecha, imagen, descripcion);
            listaViajes.add(nuevoViaje);

            // Notificar al RecyclerView que se ha insertado un nuevo elemento
            adaptadorViajes.notifyItemInserted(listaViajes.size() - 1);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
